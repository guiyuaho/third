package cn.net.gxht.app.yjdPlatform.common.controller;

import cn.net.gxht.app.yjdPlatform.common.entity.jsonResult.JsonResult;
import cn.net.gxht.app.yjdPlatform.common.entity.request.GetTest;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/18.
 */
@Controller
public class CommonController {
    @RequestMapping("/getLocation")
    @ResponseBody
    public JsonResult doGetLocationInfo(String latitude, String longitude, HttpSession session) throws IOException {
        Map<String,Object> map=new HashedMap();
        Map<String,Object> resultMap=new HashedMap();
        map.put("location",latitude+","+longitude);
        map.put("get_poi","1");
        map.put("key","Q7YBZ-UQ5LW-EPHRD-RVBSN-PSALJ-FLFBQ");
        StringBuilder url=new StringBuilder("http://apis.map.qq.com/ws/geocoder/v1/");
        JSONObject returnJsonObject = GetTest.sendGetReturnJsonString(map,url);
        System.out.printf(returnJsonObject.toString());
        JSONObject result =(JSONObject) returnJsonObject.get("result");
        JSONObject ad_info=(JSONObject) result.get("ad_info");
        String province=(String)ad_info.get("province");
        String city=(String)ad_info.get("city");
        String city_code=(String)ad_info.getString("city_code");
        String location=province+city;
        session.setAttribute("location",location);
        session.setAttribute("city_code",city_code);
        resultMap.put("location",location);
        resultMap.put("city_code",city_code);
        resultMap.put("city",city);
        return new JsonResult(resultMap);
    }
}
