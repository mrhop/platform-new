package cn.hopever.platform.utils.moji;

import cn.hopever.platform.utils.properties.CommonProperties;
import fm.last.moji.MojiFile;
import fm.last.moji.spring.SpringMojiBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Donghui Huo on 2017/7/31.
 */
@Component("mojiUtils")
public class MojiUtils {
    Logger logger = LoggerFactory.getLogger(MojiUtils.class);

    @Autowired
    @Qualifier("mojiImages")
    private SpringMojiBean mojiImages;
    @Autowired
    @Qualifier("mojiFiles")
    private SpringMojiBean mojiFiles;
    @Autowired
    @Qualifier("mojiDocs")
    private SpringMojiBean mojiDocs;
    @Autowired
    private CommonProperties commonProperties;

    public Map<String, List<String>> uploadImg(String filePath, MultipartFile[] files) throws Exception {
        return upload(filePath, files, mojiImages, "image");
    }

    public Map<String, List<String>> uploadDoc(String filePath, MultipartFile[] files) throws Exception {
        return upload(filePath, files, mojiDocs, "doc");
    }

    public Map<String, List<String>> uploadFile(String filePath, MultipartFile[] files) throws Exception {
        return upload(filePath, files, mojiFiles, "file");
    }

    public void deleteImg(String fileUrl) throws Exception {
        mojiImages.getFile(fileUrl).delete();
    }

    public void deleteDoc(String fileUrl) throws Exception {
        mojiDocs.getFile(fileUrl).delete();
    }

    public void deleteFile(String fileUrl) throws Exception {
        mojiFiles.getFile(fileUrl).delete();
    }

    private Map<String, List<String>> upload(String filePath, MultipartFile[] files, SpringMojiBean mojiBean, String type) throws IOException {
        HashMap<String, List<String>> responseData = new HashMap<>();
        List<String> list = new ArrayList<>();
        for (MultipartFile file : files) {
            String[] fileName = file.getOriginalFilename().split("\\.");
            String filePrefix = fileName[0] + "-" + UUID.randomUUID();
            String fileSuffix = fileName[fileName.length - 1];
            File fileTmp = File.createTempFile(filePrefix, "." + fileSuffix);
            String fileToSavePath = filePath + filePrefix + "." + fileSuffix;
            MojiFile mojiFile = mojiBean.getFile(fileToSavePath);
            file.transferTo(fileTmp);
            mojiBean.copyToMogile(fileTmp, mojiFile);
            String pathPrev = "";
            if ("image".equals(type)) {
                pathPrev = commonProperties.getImagePathPrev();
            } else if ("file".equals(type)) {
                pathPrev = commonProperties.getFilePathPrev();
            } else if ("doc".equals(type)) {
                pathPrev = commonProperties.getDocPathPrev();
            }
            list.add(pathPrev + fileToSavePath);
        }
        responseData.put("fileKeys", list);
        return responseData;
    }
}
