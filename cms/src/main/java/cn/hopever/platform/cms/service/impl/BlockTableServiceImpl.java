package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.service.BlockTableService;
import cn.hopever.platform.cms.vo.BlockVo;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public class BlockTableServiceImpl implements BlockTableService {
    @Override
    public Page<BlockVo> getList(TableParameters body, Principal principal) {
        return null;
    }

    @Override
    public void delete(Long id, Principal principal) {

    }

    @Override
    public BlockVo info(Long id, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result update(BlockVo BlockVo, MultipartFile[] files, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result save(BlockVo BlockVo, MultipartFile[] files, Principal principal) {
        return null;
    }
}
