package cn.hopever.platform.oauth2client.web.common;

import cn.hopever.platform.oauth2client.config.Oauth2Properties;
import cn.hopever.platform.utils.web.CommonResult;
import cn.hopever.platform.utils.web.CommonResultStatus;
import cn.hopever.platform.utils.web.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Donghui Huo on 2016/9/22.
 */
@Component
public class CommonMethods {

    Logger logger = LoggerFactory.getLogger(CommonMethods.class);

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private Oauth2Properties oauth2Properties;


    public OAuth2RestOperations getPasswordRestTemplate(String username, String password) {
        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
        resource.setClientId(oauth2Properties.getClientID());
        resource.setClientSecret(oauth2Properties.getClientSecret());
        resource.setAccessTokenUri(oauth2Properties.getAccessTokenUri());
        resource.setUsername(username);
        resource.setPassword(password);
        resource.setScope(oauth2Properties.getClientScopes());
        return new OAuth2RestTemplate(resource, new DefaultOAuth2ClientContext(new DefaultAccessTokenRequest()));
    }

    @SuppressWarnings("unchecked")
    public CommonResult getResource(HttpServletRequest request) throws Exception {
        Cookie c = CookieUtil.getCookieByName("accesstoken", request.getCookies());
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + c.getValue());
        headers.add("Content-Type", "application/json;charset=UTF-8");
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<Object> re = restTemplate.exchange(request.getAttribute("resourceUrl").toString(), HttpMethod.GET, httpEntity, Object.class);
        CommonResult cr = new CommonResult();
        HashMap<String, Object> responseData = new HashMap<>();
        responseData.put("data", re.getBody());
        cr.setStatus(CommonResultStatus.SUCCESS.toString());
        cr.setMessage("success");
        cr.setResponseData(responseData);
        return cr;
    }

    @SuppressWarnings("unchecked")
    public CommonResult postResource(Object body, HttpServletRequest request) throws Exception {
        Cookie c = CookieUtil.getCookieByName("accesstoken", request.getCookies());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + c.getValue());
        headers.add("Content-Type", "application/json;charset=UTF-8");
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        HttpEntity<Object> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<Object> re = restTemplate.exchange(request.getAttribute("resourceUrl").toString(), HttpMethod.POST, httpEntity, Object.class);
        CommonResult cr = new CommonResult();
        HashMap<String, Object> responseData = new HashMap<>();
        responseData.put("data", re.getBody());
        cr.setStatus(CommonResultStatus.SUCCESS.toString());
        cr.setMessage("success");
        cr.setResponseData(responseData);
        return cr;
    }

    @SuppressWarnings("unchecked")
    public CommonResult postFile(HttpServletRequest request, MultipartFile[] files) throws Exception {
        Cookie c = CookieUtil.getCookieByName("accesstoken", request.getCookies());
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        try {
            for (MultipartFile file : files) {
                String[] fileName = file.getOriginalFilename().split("\\.");
                String filePrefix = new Date().getTime() + "" + (int) (Math.random() * 1000);
                String fileSuffix = fileName[fileName.length - 1];
                File fileTmp = File.createTempFile(filePrefix, "." + fileSuffix);
                file.transferTo(fileTmp);
                map.add("files", new FileSystemResource(fileTmp));
            }
            map.add("filePath", request.getAttribute("filePath"));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.add("Authorization", "Bearer " + c.getValue());
            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
            ResponseEntity<Object> re = restTemplate.exchange(request.getAttribute("resourceUrl").toString(), HttpMethod.POST, requestEntity, Object.class);
            CommonResult cr = new CommonResult();
            HashMap<String, Object> responseData = new HashMap<>();
            responseData.put("data", re.getBody());
            cr.setStatus(CommonResultStatus.SUCCESS.toString());
            cr.setMessage("success");
            cr.setResponseData(responseData);
            return cr;
        } catch (IOException e) {
            logger.error("系统错误", e);
            CommonResult cr = new CommonResult();
            cr.setStatus(CommonResultStatus.SERVERFAILURE.toString());
            cr.setMessage("系统错误");
            return cr;
        }
    }


}
