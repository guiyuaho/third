package cn.net.gxht.app.yjdPlatform.pictures.service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/28.
 */
public interface PicturesService {
    public List<Map<String,Object>> findGundongPictures();
    public Map<String,Object> findHengpaiPictures();
    public Map<String,Object> findShupaiPictures();
    public List<Map<String,Object>> findRegistPicture();
    String findPictureByBelongId(Integer id);
    Map<String,Object> findPageTwoImg();
    List<Map<String,Object>> fuzzyQuery(String title, Integer id);
}
