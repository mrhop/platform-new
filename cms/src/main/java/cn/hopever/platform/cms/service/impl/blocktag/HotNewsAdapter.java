package cn.hopever.platform.cms.service.impl.blocktag;

import cn.hopever.platform.cms.service.InternalBlockTagServiceAdapter;

/**
 * Created by Donghui Huo on 2017/11/13.
 */
public class HotNewsAdapter extends InternalBlockTagServiceAdapter {

    // cache 需要关联到不同的数量上，所以用map作为缓存结构
    @Override
    public String generateInternalBlockString() {
        return null;
    }

    @Override
    public String rebuildCache() {
        return null;
    }
}
