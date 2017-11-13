package cn.hopever.platform.cms.service;

import cn.hopever.platform.cms.vo.InternalBlockTag;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Donghui Huo on 2017/11/13.
 */
public abstract class InternalBlockTagServiceAdapter {
    @Getter
    @Setter
    protected InternalBlockTag internalBlockTag;

    // 判断cache，或者重新生成
    // 内置的tag 由tag type
    public abstract String generateInternalBlockString();

    // 重置cache，比如最热新闻，或者最新新闻，就需要在新增新闻或者，修改新闻点击量的时候重建，
    // 重建的过程包括重建block的缓存，也包括重建关联文章的缓存
    // 所有tag都是类似的
    public abstract String rebuildCache();
}
