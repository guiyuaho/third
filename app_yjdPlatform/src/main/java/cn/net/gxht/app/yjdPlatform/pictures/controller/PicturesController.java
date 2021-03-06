package cn.net.gxht.app.yjdPlatform.pictures.controller;

import cn.net.gxht.app.yjdPlatform.common.entity.jsonResult.JsonResult;
import cn.net.gxht.app.yjdPlatform.pictures.entity.MatrixToImageWriter;
import cn.net.gxht.app.yjdPlatform.pictures.service.PicturesService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/28.
 */
@Controller
public class PicturesController {
    @Resource
    private PicturesService picturesService;

    @RequestMapping("/findSonPicture")
    @ResponseBody
    public JsonResult doFindSonPicture(int id) {
        String path = picturesService.findPictureByBelongId(id);
        return new JsonResult(path);
    }

    @RequestMapping("/findLunbo")
    @ResponseBody
    public JsonResult doFindLunboPictures() {
        List<Map<String, Object>> list  = picturesService.findGundongPictures();
        return new JsonResult(list);
    }

    @RequestMapping("/findHengpai")
    @ResponseBody
    public JsonResult doFindHengpaiPictures() {
        Map<String, Object> map = picturesService.findHengpaiPictures();
        return new JsonResult(map);
    }

    @RequestMapping("/findShupai")
    @ResponseBody
    public JsonResult doFindShupaiPictures() {
        Map<String, Object> map = picturesService.findShupaiPictures();
        return new JsonResult(map);
    }

    @RequestMapping("/findRegistPicture")
    @ResponseBody
    public JsonResult doFindRegistPicture() {
        return new JsonResult(picturesService.findRegistPicture());
    }

    @RequestMapping("/findPageTwoImg")
    @ResponseBody
    public JsonResult doFindPageTowImg() {
        Map<String, Object> map = picturesService.findPageTwoImg();
        return new JsonResult(map);
    }

    @RequestMapping("/findMerchant")
    @ResponseBody
    public JsonResult fuzzyQuery(HttpServletRequest request, Integer id) {
        String title = request.getParameter("title");
        try {
            title = new String(title.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("系统异常：数据转换异常");
        }
        List<Map<String, Object>> list = picturesService.fuzzyQuery(title, id);
        return new JsonResult(list);
    }
    @RequestMapping("/getQRCode")
    @ResponseBody
    public String getQRCode(HttpServletRequest request, HttpServletResponse response){
        String url=request.getSession().getServletContext().getRealPath("/");
        System.out.println("url:"+url);
        try {
            String content = "www.gxht.net.cn";
            String path = url;
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400,hints);
            File file1 = new File(path,"gxhtLTD.jpg");
            MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    return url;
    }
}
