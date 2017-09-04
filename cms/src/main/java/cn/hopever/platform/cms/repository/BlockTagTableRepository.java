package cn.hopever.platform.cms.repository;

import cn.hopever.platform.cms.domain.BlockTagTable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Donghui Huo on 2016/8/30.
 */
public interface BlockTagTableRepository extends PagingAndSortingRepository<BlockTagTable, Long> {
    public BlockTagTable findOneByTagId(String tagId);
}
