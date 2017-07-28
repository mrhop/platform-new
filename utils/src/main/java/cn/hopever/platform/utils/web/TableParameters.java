package cn.hopever.platform.utils.web;

import lombok.Data;

import java.util.Map;

/**
 * Created by Donghui Huo on 2017/7/28.
 */
@Data
public class TableParameters {
    private Pager pager = new Pager();
    private Map<String, Object> filters;
    private Map<String, String> sorts;

    @Data
   public static class Pager {
        private int pageSize = 10;
        private int currentPage = 1;
    }

    public static enum Sorts {
        Desc("desc"), Asc("asc");
        private String value;

        private Sorts(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
}


