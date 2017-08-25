package cn.hopever.platform.user.config;

import cn.hopever.platform.utils.json.JacksonUtil;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Configuration
@ConfigurationProperties("app")
@Data
public class BaseConfig {

    private String name;
    private String passwordRandom;
    private List superAdminMenu;
    private List commonAdminMenu;
    private List commonUserMenu;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    public List getSuperAdminMenu() {
        try {
            InputStream i = Thread.currentThread().getContextClassLoader().getResourceAsStream("super_admin_menu.json");
            byte[] bArr = new byte[0];
            bArr = new byte[i.available()];
            i.read(bArr);
            String data = new String(bArr, "utf-8");
            return JacksonUtil.mapper.readValue(data, List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List getCommonAdminMenu() {
        try {
            InputStream i = Thread.currentThread().getContextClassLoader().getResourceAsStream("common_admin_menu.json");
            byte[] bArr = new byte[0];
            bArr = new byte[i.available()];
            i.read(bArr);
            String data = new String(bArr, "utf-8");
            return JacksonUtil.mapper.readValue(data, List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List getCommonUserMenu() {
        try {
            InputStream i = Thread.currentThread().getContextClassLoader().getResourceAsStream("common_user_menu.json");
            byte[] bArr = new byte[0];
            bArr = new byte[i.available()];
            i.read(bArr);
            String data = new String(bArr, "utf-8");
            return JacksonUtil.mapper.readValue(data, List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
