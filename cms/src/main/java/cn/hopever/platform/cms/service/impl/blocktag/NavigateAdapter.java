package cn.hopever.platform.cms.service.impl.blocktag;

import cn.hopever.platform.cms.service.InternalBlockTagServiceAdapter;

/**
 * Created by Donghui Huo on 2017/11/13.
 */
public class NavigateAdapter extends InternalBlockTagServiceAdapter {
    // 基本是每个页面生成时
    @Override
    public String generateInternalBlockString() {
        return null;
    }

    // 导航处理时，基本每个页面都要用到，处理时，只更新navigate class或者Id包括的block
    @Override
    public String rebuildCache() {
        return null;
    }
}
