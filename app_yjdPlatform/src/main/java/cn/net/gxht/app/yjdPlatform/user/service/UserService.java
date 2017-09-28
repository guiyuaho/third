package cn.net.gxht.app.yjdPlatform.user.service;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/18.
 */
public interface UserService {
    void insertAppUser(String password, String phone,String code,HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException;
    Map<String,Object> getCode(String phone, HttpSession session);
    String login(String name, String password,HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
