package cn.hopever.platform.utils.moji;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, List<String>> uploadImg(String filePath, MultipartFile[] files) throws Exception {
        return upload(filePath, files, mojiImages);
    }

    public Map<String, List<String>> uploadDoc(String filePath, MultipartFile[] files) throws Exception {
        return upload(filePath, files, mojiDocs);
    }

    public Map<String, List<String>> uploadFile(String filePath, MultipartFile[] files) throws Exception {
        return upload(filePath, files, mojiFiles);
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

    private Map<String, List<String>> upload(String filePath, MultipartFile[] files, SpringMojiBean mojiBean) throws IOException {
        HashMap<String, List<String>> responseData = new HashMap<>();
        List<String> list = new ArrayList<>();
        for (MultipartFile file : files) {
            String[] fileName = file.getOriginalFilename().split("\\.");
            String filePrefix = fileName[0];
            String fileSuffix = fileName[fileName.length - 1];
            File fileTmp = File.createTempFile(filePrefix, "." + fileSuffix);
            MojiFile mojiFile = mojiBean.getFile(filePath + file.getOriginalFilename());
            file.transferTo(fileTmp);
            mojiBean.copyToMogile(fileTmp, mojiFile);
            list.add(filePath + file.getOriginalFilename());
        }
        responseData.put("fileKeys", list);
        return responseData;
    }
}
