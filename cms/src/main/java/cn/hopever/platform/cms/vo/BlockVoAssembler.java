package cn.hopever.platform.cms.vo;

import cn.hopever.platform.cms.domain.BlockTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class BlockVoAssembler implements GenericVoAssembler<BlockVo, BlockTable> {

    @Override
    public BlockVo toResource(BlockTable blockTable) {
        BlockVo blockVo = new BlockVo();
        BeanUtils.copyNotNullProperties(blockTable, blockVo, "content", "script");
        blockVo.setTemplateId(blockTable.getTemplateTable().getId());
        blockVo.setTemplateName(blockTable.getTemplateTable().getName());
        if (blockTable.getWebsiteTable() != null) {
            blockVo.setWebsiteId(blockTable.getWebsiteTable().getId());
            blockVo.setWebsiteName(blockTable.getWebsiteTable().getName());
        }
        if (blockTable.getArticleTable() != null) {
            blockVo.setArticleId(blockTable.getArticleTable().getId());
            blockVo.setArticleTitle(blockTable.getArticleTable().getTitle());
        }
        return blockVo;
    }

    public BlockVo toResourceAll(BlockTable blockTable) {
        BlockVo blockVo = toResource(blockTable);
        blockVo.setContent(blockTable.getContent());
        blockVo.setScript(blockTable.getScript());
        return blockVo;
    }

    @Override
    public BlockTable toDomain(BlockVo blockVo, BlockTable blockTable) {
        BeanUtils.copyNotNullProperties(blockVo, blockTable);
        return blockTable;
    }
}
