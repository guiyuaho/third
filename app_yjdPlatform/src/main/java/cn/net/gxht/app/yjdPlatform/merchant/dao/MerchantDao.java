package cn.net.gxht.app.yjdPlatform.merchant.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/11.
 */
public interface MerchantDao {
    List<Map<String,Object>> findByBelongIdAndMerchantId(@Param(value = "belongId") Integer hengpaiId, @Param(value = "merchantId") Integer merchantId);
}
