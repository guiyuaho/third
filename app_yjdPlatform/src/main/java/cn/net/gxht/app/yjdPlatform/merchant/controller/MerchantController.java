package cn.net.gxht.app.yjdPlatform.merchant.controller;

import cn.net.gxht.app.yjdPlatform.common.entity.jsonResult.JsonResult;
import cn.net.gxht.app.yjdPlatform.common.entity.page.Page;
import cn.net.gxht.app.yjdPlatform.merchant.service.MerchantService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/11.
 */
@Controller
public class MerchantController {
    @Resource
    MerchantService merchantService;
    @RequestMapping("/findMerchants")
    @ResponseBody
    public JsonResult findMerchants(Integer hengpaiId, Integer citycode, Page page){
        Map<String,Object> map= merchantService.findMerchantService(hengpaiId,citycode,page);
        return  new JsonResult(map);
    }
}
