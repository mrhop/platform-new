package cn.hopever.platform.cms.vo;

import cn.hopever.platform.cms.domain.BlockTagTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class BlockTagVoAssembler implements GenericVoAssembler<BlockTagVo,BlockTagTable> {

    @Override
    public BlockTagVo toResource(BlockTagTable blockTagTable) {
        BlockTagVo blockTagVo = new BlockTagVo();
        BeanUtils.copyNotNullProperties(blockTagTable,blockTagVo);
        return blockTagVo;
    }

    @Override
    public BlockTagTable toDomain(BlockTagVo blockTagVo, BlockTagTable blockTagTable) {
        BeanUtils.copyNotNullProperties(blockTagVo,blockTagTable);
        return blockTagTable;
    }
}
