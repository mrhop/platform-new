package cn.hopever.platform.cms.service.impl.blocktag;

import cn.hopever.platform.cms.service.InternalBlockTagServiceAdapter;

/**
 * Created by Donghui Huo on 2017/11/13.
 */
public class NewNewsAdapter extends InternalBlockTagServiceAdapter {
    // 新增新闻时，重置cache，或者更新新闻创建时间时
    // 注意和hotnews一样，需要考虑数量，所以使用map结构
    @Override
    public String generateInternalBlockString() {
        return null;
    }

    @Override
    public String rebuildCache() {
        return null;
    }
}
