package cn.hopever.platform.user.web.controller;

import cn.hopever.platform.user.service.ClientTableService;
import cn.hopever.platform.user.vo.ClientVo;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Donghui Huo on 2016/8/29.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/client", produces = "application/json")
public class ClientController {
    Logger logger = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    private ClientTableService clientTableService;

    // @PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Map getList(@RequestBody TableParameters body) {
        Page<ClientVo> list = clientTableService.getList(body);
        Map<String, Object> map = new HashMap<>();
        List<HashMap<String, Object>> listReturn = null;
        if (list != null && list.iterator().hasNext()) {
            listReturn = new ArrayList<>();
            for (ClientVo cv : list) {
                HashMap<String, Object> mapTemp = new HashMap<>();
                mapTemp.put("key", cv.getId());
                List<Object> listTmp = new ArrayList<>();
                listTmp.add(cv.getClientName());
                listTmp.add(cv.getClientId());
                listTmp.add(cv.getAuthorizedGrantTypesStr());
                listTmp.add(cv.getScopesStr());
                mapTemp.put("value", listTmp);
                listReturn.add(mapTemp);
            }
            map.put("rows", listReturn);
            map.put("totalCount", list.getTotalElements());

        } else {
            map.put("rows", null);
            map.put("totalCount", 0);
        }
        map.put("pager", body.getPager());
        map.put("filters", body.getFilters());
        map.put("sorts", body.getSorts());
        return map;
    }

    //@PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/info", method = {RequestMethod.GET})
    public ClientVo info(@RequestParam Long key) {
        //返回user是无法解析的，要使用对象解析为map 的形式
        return clientTableService.getVoById(key);
    }


    //@PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result update(@RequestParam Long key, @RequestBody ClientVo clientVo) {
        clientVo.setId(key);
        return clientTableService.update(clientVo);
    }

    //@PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result save(@RequestBody ClientVo clientVo) {
        return clientTableService.save(clientVo);
    }

    //@PreAuthorize("hasRole('ROLE_super_admin')")
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long key) {
        this.clientTableService.delete(key);
    }

    @RequestMapping(value = "/form/rulechange", method = {RequestMethod.GET, RequestMethod.POST})
    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body) {
        Map mapReturn = new HashMap<>();
        if (body == null) {
            if (key == null) {
                // 新增
                mapReturn.put("scopeIds", clientTableService.getResourceScopeOptions());
            } else {
                // 更新 - 考虑principal对其显示的限制
                mapReturn.put("scopeIds", clientTableService.getResourceScopeOptions());
                mapReturn.put("autoApprovaledScopeIds", clientTableService.getAutoApprovaledScopeOptions(key));
            }
        } else {
            if (body.get("clients") != null) {
            }
        }
        return mapReturn;
    }

}
