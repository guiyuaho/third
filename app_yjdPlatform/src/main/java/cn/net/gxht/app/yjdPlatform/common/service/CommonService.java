package cn.net.gxht.app.yjdPlatform.common.service;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/9/18.
 */
public interface CommonService {
    String checkUserLogin(String token, HttpSession session);
}
