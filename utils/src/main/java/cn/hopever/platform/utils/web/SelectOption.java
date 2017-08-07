package cn.hopever.platform.utils.web;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Donghui Huo on 2017/8/7.
 */
@Data
public class SelectOption implements Serializable {
    private String label;
    private Object value;

    public SelectOption() {
    }

    public SelectOption(String label, Object value) {
        this.label = label;
        this.value = value;
    }
}
