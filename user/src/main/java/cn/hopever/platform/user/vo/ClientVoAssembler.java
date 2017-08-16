package cn.hopever.platform.user.vo;

import cn.hopever.platform.user.domain.ClientResouceScopeTable;
import cn.hopever.platform.user.domain.ClientRoleTable;
import cn.hopever.platform.user.domain.ClientTable;
import cn.hopever.platform.user.domain.ResouceScopeTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class ClientVoAssembler {


    public ClientVoAssembler() {
    }

    public ClientVo toResource(ClientTable clientTable) {
        ClientVo resource = new ClientVo();
        BeanUtils.copyNotNullProperties(clientTable, resource, "clientSecret");
        //关联其他资源
        if (clientTable.getAuthoritiesBasic() != null) {
            ArrayList<Long> sCrr = new ArrayList<>();
            for (ClientRoleTable crt : clientTable.getAuthoritiesBasic()) {
                sCrr.add(crt.getId());
            }
            resource.setAuthorities(sCrr);
        }
        if (clientTable.getAuthorizedGrantTypes() != null) {
            ArrayList<String> agt = new ArrayList<>();
            for (String grantType : clientTable.getAuthorizedGrantTypes()) {
                switch (grantType) {
                    case "authorization_code":
                        agt.add("授权码");
                        break;
                    case "password":
                        agt.add("密码");
                        break;
                    case "client_credentials":
                        agt.add("客户端");
                }
                resource.setAuthorizedGrantTypesStr(agt.toString());
            }
        }
        if (clientTable.getClientResouceScopeTables() != null) {
            ArrayList<String> crs = new ArrayList<>();
            ArrayList<Long> crl = new ArrayList<>();
            ArrayList<Long> crlAuto = new ArrayList<>();
            for (ClientResouceScopeTable clientResouceScopeTable : clientTable.getClientResouceScopeTables()) {
                ResouceScopeTable rss = clientResouceScopeTable.getScope();
                crs.add(rss.getName());
                crl.add(rss.getId());
                if (clientResouceScopeTable.isAutoApprove()) {
                    crlAuto.add(rss.getId());
                }
            }
            resource.setScopeIds(crl);
            resource.setScopesStr(crs.toString());
            resource.setAutoApprovaledScopeIds(crlAuto);
        }
        return resource;
    }
}
