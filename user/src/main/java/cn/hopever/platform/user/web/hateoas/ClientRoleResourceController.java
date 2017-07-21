package cn.hopever.platform.user.web.hateoas;

import cn.hopever.platform.user.resources.ClientRoleResource;
import cn.hopever.platform.user.service.ClientRoleTableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Donghui Huo on 2016/8/29.
 */
@RestController
@ExposesResourceFor(ClientRoleResource.class)
@RequestMapping(value = "/clientrole", produces = "application/json")
@CrossOrigin
public class ClientRoleResourceController {
    Logger logger = LoggerFactory.getLogger(ClientRoleResourceController.class);
    @Autowired
    private ClientRoleTableService clientRoleTableService;

    @Autowired
    EntityLinks entityLinks;

    @Autowired
    private ClientRoleResourceAssembler clientRoleResourceAssembler;
}
