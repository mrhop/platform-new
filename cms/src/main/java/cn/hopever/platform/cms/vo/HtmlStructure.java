package cn.hopever.platform.cms.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Donghui Huo on 2017/11/7.
 */
public class HtmlStructure {
    @Data
    public static class Row {
        private Map<String, Map<String, Integer>> position;
        private List<Column> columns = new ArrayList<>();
        private List<Object> objects = new ArrayList<>();
    }

    @Data
    public static class Column {
        private Map<String, Map<String, Integer>> position;
        private Object object;
        private List<Object> objects = new ArrayList<>();
        private List<Row> rows = new ArrayList<>();
    }
}
