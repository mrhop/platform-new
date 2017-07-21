package cn.hopever.platform.user.web.hateoas;

import cn.hopever.platform.user.resources.ModuleResource;
import cn.hopever.platform.user.service.ModuleTableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by Donghui Huo on 2016/8/29.
 */
@RestController
@ExposesResourceFor(ModuleResource.class)
@RequestMapping(value = "/hateoas/module", produces = "application/json")
@CrossOrigin
public class ModuleResourceController {
    Logger logger = LoggerFactory.getLogger(ModuleResourceController.class);
    @Autowired
    private ModuleTableService moduleTableService;

    @Autowired
    EntityLinks entityLinks;

    @Autowired
    private ModuleResourceAssembler moduleResourceAssembler;

    @Autowired
    private RedisTemplate redisTemplate;

    @PreAuthorize("#oauth2.hasScope('internal_client') and isAuthenticated()")
    @RequestMapping(value = "/list/{clientId}", method = RequestMethod.GET)
    public Resources<ModuleResource> getList(Principal principal, @PathVariable String clientId) {
        //首先获取到role，然后获取到clientId相关的
        //user client和其他client不一样，user client使用role表，而其他client使用module-role，然后关联module
        //先做user_client的
        String authority = ((OAuth2Authentication) principal).getAuthorities().iterator().next().getAuthority();

        String redisKey = null;
        if ("ROLE_super_admin".equals(authority)) {
            redisKey = "module_list-ROLE_super_admin-" + clientId;
        } else if ("ROLE_admin".equals(authority)) {
            redisKey = "module_list-ROLE_admin-" + clientId;
        } else if ("ROLE_common_user".equals(authority)) {
            if ("user_admin_client".equals(clientId)) {
                redisKey = "module_list-ROLE_common_user-" + clientId;
            } else {
                redisKey = "module_list-ROLE_common_user-" + clientId + "-" + principal.getName();
            }
        }
        List<ModuleResource> list = null;
        if (redisTemplate.hasKey(redisKey)) {
            list = (List) redisTemplate.opsForValue().get(redisKey);
        } else {
            list = moduleResourceAssembler.toResourcesCustomized(moduleTableService.getListByClientAndAuthorityAndUser(clientId, authority, principal.getName()));
            redisTemplate.opsForValue().set(redisKey, list);
        }
        Resources<ModuleResource> wrapped = new Resources<>(list, linkTo(ModuleResourceController.class).slash("/list")
                .withSelfRel());
        return wrapped;
    }
}
