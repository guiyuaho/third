package cn.net.gxht.app.yjdPlatform.merchant.service;

import cn.net.gxht.app.yjdPlatform.common.entity.page.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface MerchantService {
    public Map<String,Object> findMerchantService(Integer BelongId, Integer citycode, Page page);
}
