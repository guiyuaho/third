package cn.net.gxht.app.yjdPlatform.common.service.impl;

import cn.net.gxht.app.yjdPlatform.common.dao.CommonDao;
import cn.net.gxht.app.yjdPlatform.common.service.CommonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/9/18.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CommonServiceImpl implements CommonService{
    private CommonDao commonDao;
    public String checkUserLogin(String token, HttpSession session){
//        String sessionToken=(String) session.getAttribute("token");
//        if(token==null){
//
//        }
        return null;
    };
}
