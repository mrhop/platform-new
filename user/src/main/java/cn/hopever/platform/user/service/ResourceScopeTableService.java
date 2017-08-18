package cn.hopever.platform.user.service;

import cn.hopever.platform.user.vo.ResourceScopeVo;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.data.domain.Page;

/**
 * Created by Donghui Huo on 2017/8/18.
 */
public interface ResourceScopeTableService {

    public VueResults.Result save(ResourceScopeVo resourceScope);

    public VueResults.Result update(ResourceScopeVo resourceScope);

    public void delete(long id);

    public ResourceScopeVo getById(Long id);

    public Page<ResourceScopeVo> getList(TableParameters body);

}
