package cn.net.gxht.app.yjdPlatform.pictures.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/28.
 */
public interface PicturesDao {
    List<Map<String,Object>> findPictures();
    List<Map<String,Object>> findRegistPicture();
    String findPictureByBelongId(Integer id);
    String findTileByPictureId(Integer pictureId);
    List<Map<String,Object>> findPageTwoImg();
    List<Map<String,Object>> findMerchantByIdAndCityCode(Integer citycode);
    List<Map<String,Object>> fuzzyQuery(@Param(value = "title") String title, @Param(value = "id") Integer id);
    Map<String,Object> findPictureById(Integer id);
    //根据对应的pictureId查找对应的联系人
    String findContactByPictureId(Integer pictureId);
    Integer findByPhone(String phone);
    List<Map<String,Object>> findClientByPhone(String phone);
    Integer findClientQueryTimes(Integer clientId);
    Integer updateClientQueryTimes(@Param(value = "clientId") Integer clientId, @Param(value = "times") Integer times);



    List<Map<String,Object>> findLunboPictures();
}
