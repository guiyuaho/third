package cn.net.gxht.app.yjdPlatform.user.controller;

import cn.net.gxht.app.yjdPlatform.common.entity.jsonResult.JsonResult;
import cn.net.gxht.app.yjdPlatform.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/18.
 */
@Controller
public class UserController {
    @Resource
    private UserService userService;
    @RequestMapping("/getCode")
    @ResponseBody
    public JsonResult getCode(String phone, HttpSession session){
        Map<String,Object> map=userService.getCode(phone,session);
        return new JsonResult(map);
    }
    @RequestMapping("/insertUser")
    @ResponseBody
    public JsonResult insertUser(String password, String phone, String code, HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        userService.insertAppUser(password, phone, code, session);
        return new JsonResult();
    }
    @RequestMapping("/login")
    @ResponseBody
    public JsonResult login(String phone,String password,HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String token=userService.login(phone,password,session);
        return new JsonResult(token);
    }
}
