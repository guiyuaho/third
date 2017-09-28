package cn.net.gxht.app.yjdPlatform.user.service.impl;

import cn.net.gxht.app.yjdPlatform.common.entity.Client;
import cn.net.gxht.app.yjdPlatform.common.entity.Request;
import cn.net.gxht.app.yjdPlatform.common.entity.Response;
import cn.net.gxht.app.yjdPlatform.common.entity.constant.Constants;
import cn.net.gxht.app.yjdPlatform.common.entity.constant.HttpHeader;
import cn.net.gxht.app.yjdPlatform.common.entity.constant.HttpSchema;
import cn.net.gxht.app.yjdPlatform.common.entity.enums.Method;
import cn.net.gxht.app.yjdPlatform.user.dao.UserDao;
import cn.net.gxht.app.yjdPlatform.user.entity.AppUser;
import cn.net.gxht.app.yjdPlatform.user.entity.md5.MyMD5Util;
import cn.net.gxht.app.yjdPlatform.user.entity.md5.SecurityUtil;
import cn.net.gxht.app.yjdPlatform.user.service.UserService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by Administrator on 2017/9/18.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {
    private final static List<String> CUSTOM_HEADERS_TO_SIGN_PREFIX = new ArrayList<String>();
    //APP KEY
    private final static String APP_KEY = "24603192";
    // APP密钥
    private final static String APP_SECRET = "e8a4f79d4a6955783b8815434795b638";
    //API域名
    private final static String HOST = "sms.market.alicloudapi.com";
    //log4j
    private static Logger logger = LogManager.getLogger(UserServiceImpl.class);
    @Resource
    private UserDao userDao;

    public Map<String, Object> getCode(String phone, HttpSession session) {
        logger.entry();
        Map<String, Object> map1 = new HashedMap();
        //请求path
        String path = "/singleSendSms";
        Map<String, String> headers = new HashMap<String, String>();
        //（必填）根据期望的Response内容类型设置
        headers.put(HttpHeader.HTTP_HEADER_ACCEPT, "application/json");
        String appcode = "0ed210ef6f544e559cda2912ae1210de";
        headers.put("Authorization", "APPCODE " + appcode);
        //headers.put("b-header2", "header2Value");
        CUSTOM_HEADERS_TO_SIGN_PREFIX.clear();
        CUSTOM_HEADERS_TO_SIGN_PREFIX.add("Authorization");
        //CUSTOM_HEADERS_TO_SIGN_PREFIX.add("a-header2");
        Request request = new Request(Method.GET, HttpSchema.HTTP + HOST, path, APP_KEY, APP_SECRET, Constants.DEFAULT_TIMEOUT);
        request.setHeaders(headers);
        request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);

        //请求的query
        // {“no”:”123456”}
        Map<String, String> querys = new HashMap<String, String>();
        String code = (int) (Math.random() * 10) + "" + (int) (Math.random() * 10) + (int) (Math.random() * 10) + (int) (Math.random() * 10);
        String paramString = "{\"code\":\"" + code + "\"}";
        querys.put("ParamString", paramString);
        querys.put("RecNum", phone);
        querys.put("SignName", "国信汇通");
        querys.put("TemplateCode", "SMS_90245076");
        request.setQuerys(querys);

        //调用服务端
        Response response = null;
        try {
            response = Client.execute(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("执行请求异常");
        }
        String status = JSON.toJSONString(response);
        System.out.println(status);
        Map maps = (Map) JSON.parse(status);
        System.out.println("这个是用JSON类来解析JSON字符串!!!");
        Integer statusCode = (Integer) maps.get("statusCode");
        for (Object map : maps.entrySet()) {
            if ("body".equals((String) ((Map.Entry) map).getKey())) {
                String s = (String) ((Map.Entry) map).getValue();
                Map map2 = (Map) JSON.parse(s);
                String message = (String) map2.get("message");
                Boolean state = (Boolean) map2.get("success");
                map1.put("statusCode", statusCode);
                map1.put("message", message);
                map1.put("state", state);
                if (state == false) {
                    if ("The specified recNum is wrongly formed.".equals(message)) {
                        message = "手机号格式错误";
                    } else if ("The specified dayu status is wrongly formed.".equals(message)) {
                        message = "运营商账号未开通";
                    } else if ("Frequency limit reaches.".equals(message)) {
                        message = "获取验证码的次数收到限制";
                    }
                    throw new RuntimeException(message);
                }
                System.out.print(map1);
            }
        }
        if (map1 == null) {
            throw new RuntimeException("系统异常");
        }
        session.setAttribute("code", code);
        System.out.printf("code:"+code);
        session.setMaxInactiveInterval(360);
        logger.debug("用户:" + phone + "验证码发送成功,验证码为" + code);
        logger.exit();
        return map1;
    }

    public void insertAppUser(String password, String phone, String code,HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //CheckCode.checkCode(code,session);
        Boolean passwordStyle = NumberUtils.isDigits(password);
        if (passwordStyle == true) {
            throw new RuntimeException("密码不得是纯数字");
        }
        if (phone.length() != 11) {
            throw new RuntimeException("手机号格式错误");
        }
        /**
         * 此处要判断验证码是否相等
         */
        if ("".equals(password)) {
            throw new RuntimeException("密码不得为空");
        }
        if (password.length() > 30) {
            throw new RuntimeException("密码不得超过三十个字符");
        }
        AppUser user1 = userDao.findUserByPhone(phone);
        if (user1 != null) {
            throw new RuntimeException("手机号已被注册");
        }

        /**
         * 对用户密码进行MD5加密
         */
        String pwdInDB = MyMD5Util.getEncryptedPwd(password);
        //注册时可以看用户是否已注册
        Integer i = userDao.insertAppUser(pwdInDB, phone);
        if (i <= 0) {
            throw new RuntimeException("插入数据异常");
        }
        logger.debug("插入数据成功" + phone + "," + password);
    }

    public String login(String phone, String password,HttpSession sesssion) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Map<String,Object> tokenMap=new HashedMap();
        if ("".equals(phone)) {
            throw new RuntimeException("手机号不得为空");
        }
        if (phone.length() != 11) {
            throw new RuntimeException("手机号格式错误");
        }
        if ("".equals(password)) {
            throw new RuntimeException("密码不得为空");
        }
        if (password.length() > 30) {
            throw new RuntimeException("密码不得超过三十个字符");
        }
        /**
         * 用户根据账号查找到了对应的账户
         */
        AppUser user = userDao.findUserByPhone(phone);
        /**
         * 取出密码对密码进行解密,再验证密码是否正确
         */
        String pwdInDB = user.getPassword();
        if(user==null){
            throw new RuntimeException("未注册");
        }else if(!MyMD5Util.validPassword(password,pwdInDB)){
            throw new RuntimeException("密码错误");
        }
        /**
         * 此处需要将一些重要数据存储在session当中
         */
        tokenMap.put("phone",phone);
        tokenMap.put("password",password);
        tokenMap.put("time",new Date());
        String token= SecurityUtil.authentication(tokenMap);
        sesssion.setAttribute("tokenMap",tokenMap);
        sesssion.setAttribute("token",token);
        /**
         * 将用户token传入数据库
         */
        Integer i= userDao.insertToken(token,phone);
        System.out.println(i);
        if(i<=0){
            throw new RuntimeException("系统异常 token");
        }
        /**
         * 记日志
         */
        logger.debug(phone + "登录成功,token"+token);
        return token;
    }
}
