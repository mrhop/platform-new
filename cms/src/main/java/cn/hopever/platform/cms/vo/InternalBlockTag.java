package cn.hopever.platform.cms.vo;

import cn.hopever.platform.cms.domain.WebsiteTable;
import lombok.Data;

/**
 * Created by Donghui Huo on 2017/11/13.
 */
@Data
public class InternalBlockTag {
    private String tagType;
    // 比如自定义tag code 唯一标识
    private String tagCode;
    // 比如媒体库ID
    private Long tagId;
    private Integer tagNum;
    private WebsiteTable websiteTable;
}
