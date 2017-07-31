package cn.hopever.platform.utilsweb.web.rest;

import cn.hopever.platform.utils.moji.MojiUtils;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by Donghui Huo on 2016/12/12.
 */
@RestController
@RequestMapping(produces = "application/json")
public class UtilsWebController {

    Logger logger = LoggerFactory.getLogger(UtilsWebController.class);

    @Autowired
    @Qualifier("mojiUtils")
    private MojiUtils mojiUtils;

    @RequestMapping(value = "/upload/image", method = {RequestMethod.POST})
    public Map uploadImg(HttpServletRequest request, @RequestPart("files") MultipartFile[] files) throws Exception {
        String filePath = request.getParameter("filePath");
        return mojiUtils.uploadImg(filePath,files);
    }

    @RequestMapping(value = "/upload/doc", method = {RequestMethod.POST})
    public Map uploadDoc(HttpServletRequest request, @RequestPart("files") MultipartFile[] files) throws Exception {
        String filePath = request.getParameter("filePath");
        return mojiUtils.uploadDoc(filePath,files);
    }

    @RequestMapping(value = "/upload/file", method = {RequestMethod.POST})
    public Map uploadFile(HttpServletRequest request, @RequestPart("files") MultipartFile[] files) throws Exception {
        String filePath = request.getParameter("filePath");
        return mojiUtils.uploadFile(filePath,files);
    }

    @RequestMapping(value = "/delete/image", method = { RequestMethod.POST})
    public void deleteImg(@RequestBody Map<String, Object> body) throws Exception {
        String fileUrl = body.get("fileUrl").toString();
        logger.info("we check:"+fileUrl);
        mojiUtils.deleteImg(fileUrl);
    }

    @RequestMapping(value = "/delete/doc", method = {RequestMethod.POST})
    public void deleteDoc(@RequestBody Map<String, Object> body) throws Exception {
        String fileUrl = body.get("fileUrl").toString();
        logger.info("we check:"+fileUrl);
        mojiUtils.deleteDoc(fileUrl);
    }

    @RequestMapping(value = "/delete/file", method = {RequestMethod.POST})
    public void deleteFile(@RequestBody Map<String, Object> body) throws Exception {
        String fileUrl = body.get("fileUrl").toString();
        logger.info("we check:"+fileUrl);
        mojiUtils.deleteFile(fileUrl);
    }

    @RequestMapping(value = "/test/moji", method = {RequestMethod.GET})
    @CrossOrigin
    public void test() throws Exception {
        logger.info("we check just test:");
    }
}
