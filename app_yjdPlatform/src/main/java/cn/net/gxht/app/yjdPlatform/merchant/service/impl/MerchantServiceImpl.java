package cn.net.gxht.app.yjdPlatform.merchant.service.impl;

import cn.net.gxht.app.yjdPlatform.common.dao.CommonDao;
import cn.net.gxht.app.yjdPlatform.common.entity.page.Page;
import cn.net.gxht.app.yjdPlatform.merchant.dao.MerchantDao;
import cn.net.gxht.app.yjdPlatform.merchant.service.MerchantService;
import cn.net.gxht.app.yjdPlatform.pictures.dao.PicturesDao;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/11.
 */
@Service
@Transactional
public class MerchantServiceImpl implements MerchantService {
    @Resource
    private MerchantDao merchantDao;
    @Resource
    private PicturesDao picturesDao;
    @Resource
    private CommonDao commonDao;
    public Map<String,Object> findMerchantService(Integer BelongId,Integer citycode, Page page){
        System.out.printf("citycode"+citycode);
        List<Map<String,Object>> list = picturesDao.findMerchantByIdAndCityCode(citycode);
        System.out.println("list:"+list);
        List<Map<String,Object>> merchants=new ArrayList<Map<String, Object>>();
        List<Map<String,Object>> merchantsByPage=new ArrayList<Map<String, Object>>();
        for (Map<String,Object> map:list){
            Integer merchantId = (Integer) map.get("id");
            System.out.println("merchantId:"+merchantId);
            List<Map<String,Object>> list1=merchantDao.findByBelongIdAndMerchantId(BelongId,merchantId);
            System.out.println(list1);
            if(list1.size()!=0){
                merchants.add(map);
            }
        }
        /**
         * 获得总数
         */
        System.out.println(merchants.size());
        page.setAmount(merchants.size());
        /**
         * 获得总数之后就可以获得总页数
         */
        page.setAllPage();
        /**
         * 获得起始条目
         */
         page.setStartIndex(page.getStartIndex());
        /**
         * 获得结束条目
         */
        page.setEndIndex(page.getEndIndex());
        System.out.println(page);
        int index=0;
        for(Map<String,Object> map:merchants){
            if(index>=page.getStartIndex()&&index<=page.getEndIndex()){
                merchantsByPage.add(map);
            }
            index++;
        }
        Map map=new HashedMap();
        map.put("pageObject",page);
        map.put("pageInfo",merchantsByPage);
        return map;
    }
}
