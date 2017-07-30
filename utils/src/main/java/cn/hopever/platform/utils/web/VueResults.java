package cn.hopever.platform.utils.web;

import com.sun.net.httpserver.Authenticator;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class VueResults {

    static class Success {
        Success(String title, String message) {
            this.title = title;
            this.message = message;
        }

        private String title;
        private String message;
    }

    public static Map generateSuccess(String title, String message) {
        Map map = new HashMap();
        map.put("success", new VueResults.Success(title, message));
        return map;
    }
}
