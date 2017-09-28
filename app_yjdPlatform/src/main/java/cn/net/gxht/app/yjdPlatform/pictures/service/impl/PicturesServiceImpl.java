package cn.net.gxht.app.yjdPlatform.pictures.service.impl;

import cn.net.gxht.app.yjdPlatform.pictures.dao.PicturesDao;
import cn.net.gxht.app.yjdPlatform.pictures.service.PicturesService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 10604 on 2017/8/28.
 */
@Service
@Transactional
    public class PicturesServiceImpl implements PicturesService {
    @Resource
    private PicturesDao picturesDao;
    public String findPictureByBelongId(Integer id) {
        return picturesDao.findPictureByBelongId(id);
    }

    public Map<String, Object> findPageTwoImg() {
        List<Map<String,Object>> list=picturesDao.findPageTwoImg();
        List<Map<String,Object>> junData=new ArrayList<Map<String, Object>>();
        List<Map<String,Object>> headImg=new ArrayList<Map<String, Object>>();
        for(Map<String,Object> map:list){
            String type=(String) map.get("type");
            if("jiuData".equals(type)){
                junData.add(map);
            }
            if("headImg".equals(type)){
                headImg.add(map);
            }
        }
        Map<String,Object> map=new HashedMap();
        map.put("jiuData",junData);
        map.put("headImg",headImg);
        return map;
    }

    public List<Map<String,Object>> findGundongPictures() {
        List<Map<String,Object>> list=picturesDao.findLunboPictures();
        return list;
    }
    public Map<String,Object> findHengpaiPictures() {
        List<Map<String,Object>> list=picturesDao.findPictures();
        System.out.println(list);
        List<Map<String,Object>> henpai=new ArrayList<Map<String, Object>>();
        for(Map<String,Object> map:list){
            String type=(String) map.get("type");
            if("横排".equals(type)){
                henpai.add(map);
            }
        }
        Map<String,Object> map=new HashedMap();
        map.put("henpai",henpai);
        return map;
    }
    public Map<String,Object> findShupaiPictures() {
        List<Map<String,Object>> list=picturesDao.findPictures();
        List<Map<String,Object>> shupai=new ArrayList<Map<String, Object>>();
        for(Map<String,Object> map:list){
            String type=(String) map.get("type");
            if("竖排".equals(type)){
                shupai.add(map);
            }
        }
        Map<String,Object> map=new HashedMap();
        map.put("shupai",shupai);
        return map;
    }
    public List<Map<String,Object>> findRegistPicture(){
        return picturesDao.findRegistPicture();
    }

    public List<Map<String, Object>> fuzzyQuery(String title,Integer id) {

        return picturesDao.fuzzyQuery(title,id);
    }
}
