package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.service.InternalBlockTagService;
import cn.hopever.platform.cms.service.InternalBlockTagServiceAdapter;
import cn.hopever.platform.cms.vo.InternalBlockTag;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Donghui Huo on 2017/11/13.
 */
@Service
public class InternalBlockTagServiceImpl implements InternalBlockTagService {

    private Map<String, ? extends InternalBlockTagServiceAdapter> listAdapter = new HashMap<>();

    @Override
    public String generateTagString(InternalBlockTag internalBlockTag) {
        InternalBlockTagServiceAdapter internalBlockTagServiceAdapter = listAdapter.get(internalBlockTag.getTagCode());
        if (internalBlockTagServiceAdapter != null) {
            internalBlockTagServiceAdapter.setInternalBlockTag(internalBlockTag);
            return internalBlockTagServiceAdapter.generateInternalBlockString();
        }
        return null;
    }
}
