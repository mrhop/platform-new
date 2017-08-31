package cn.hopever.platform.cms.service.impl;

import cn.hopever.platform.cms.service.WebsiteTableService;
import cn.hopever.platform.cms.vo.WebsiteVo;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public class WebsiteTableServiceImpl implements WebsiteTableService {
    @Override
    public Page<WebsiteVo> getList(TableParameters body, Principal principal) {
        return null;
    }

    @Override
    public void delete(Long id, Principal principal) {

    }

    @Override
    public WebsiteVo info(Long id, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result update(WebsiteVo websiteVo, MultipartFile[] files, Principal principal) {
        return null;
    }

    @Override
    public VueResults.Result save(WebsiteVo websiteVo, MultipartFile[] files, Principal principal) {
        return null;
    }
}
