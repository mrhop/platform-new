package cn.hopever.platform.utils.web;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/7.
 */
@Data
public class TreeOption implements Serializable {
    private Long id;
    private String title;
    private String iconClass;
    private String url;
    private boolean emitClick = true;
    private SelectOption emitClickArgs;
    private List<TreeOption> children;
    private boolean refresh = false;

    public TreeOption() {
    }

    public TreeOption(Long id, String title) {
        this.id = id;
        this.title = title;
        this.emitClickArgs = new SelectOption(title, id);
    }

}
