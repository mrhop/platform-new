package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.service.BlockTagTableService;
import cn.hopever.platform.cms.vo.BlockTagVo;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public class BlockTagTableServiceImpl implements BlockTagTableService {
    @Override
    public Page<BlockTagVo> getList(TableParameters body, Principal principal) {
        return null;
    }

    @Override
    public void delete(Long id, Principal principal) {

    }

    @Override
    public BlockTagVo info(Long id, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result update(BlockTagVo BlockTagVo, MultipartFile[] files, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result save(BlockTagVo BlockTagVo, MultipartFile[] files, Principal principal) {
        return null;
    }
}
