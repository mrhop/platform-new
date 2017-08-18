package cn.hopever.platform.user.service.impl;

import cn.hopever.platform.user.domain.ResourceScopeTable;
import cn.hopever.platform.user.repository.CustomResourceScopeTableRepository;
import cn.hopever.platform.user.repository.ResourceScopeTableRepository;
import cn.hopever.platform.user.service.ResourceScopeTableService;
import cn.hopever.platform.user.vo.ResourceScopeVo;
import cn.hopever.platform.user.vo.ResourceScopeVoAssembler;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/18.
 */
@Service("resourceScopeTableService")
@Transactional
public class ResourceScopeTableServiceImpl implements ResourceScopeTableService {

    @Autowired
    private ResourceScopeTableRepository resourceScopeTableRepository;
    @Autowired
    private CustomResourceScopeTableRepository customResourceScopeTableRepository;
    @Autowired
    private ResourceScopeVoAssembler resourceScopeVoAssembler;

    @Override
    public VueResults.Result save(ResourceScopeVo resourceScope) {
        if (resourceScopeTableRepository.findOneByScopeId(resourceScope.getScopeId()) != null) {
            return VueResults.generateError("创建失败", "资源范围账户已存在");
        }
        ResourceScopeTable resourceScopeTable = new ResourceScopeTable();
        resourceScopeTableRepository.save(resourceScopeVoAssembler.toDomain(resourceScope, resourceScopeTable));
        return VueResults.generateSuccess("创建成功", "创建资源范围成功");
    }

    @Override
    public VueResults.Result update(ResourceScopeVo resourceScope) {
        ResourceScopeTable resourceScopeTable = resourceScopeTableRepository.findOne(resourceScope.getId());
        resourceScopeTableRepository.save(resourceScopeVoAssembler.toDomain(resourceScope, resourceScopeTable));
        return VueResults.generateSuccess("更新成功", "更新资源范围成功");

    }

    @Override
    public void delete(long id) {
        resourceScopeTableRepository.delete(id);
    }

    @Override
    public ResourceScopeVo getById(Long id) {
        return resourceScopeVoAssembler.toResource(resourceScopeTableRepository.findOne(id));
    }

    @Override
    public Page<ResourceScopeVo> getList(TableParameters body) {
        PageRequest pageRequest;
        if (body.getSorts() == null || body.getSorts().isEmpty()) {
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.ASC, "id");
        } else {
            String key = body.getSorts().keySet().iterator().next();
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.fromString(body.getSorts().get(key)), key);
        }
        Page<ResourceScopeTable> page = customResourceScopeTableRepository.findByFilters(body.getFilters(), pageRequest);
        List<ResourceScopeVo> list = new ArrayList<>();
        for (ResourceScopeTable resourceScopeTable : page) {
            list.add(resourceScopeVoAssembler.toResource(resourceScopeTable));
        }
        return new PageImpl<ResourceScopeVo>(list, pageRequest, page.getTotalElements());
    }
}
