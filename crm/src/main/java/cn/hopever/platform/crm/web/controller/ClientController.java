package cn.hopever.platform.crm.web.controller;

import cn.hopever.platform.utils.security.CommonMethods;
import cn.hopever.platform.crm.service.*;
import cn.hopever.platform.crm.vo.ClientVo;
import cn.hopever.platform.utils.web.GenericController;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Donghui Huo on 2017/9/5.
 */
@RestController
//@CrossOrigin
@RequestMapping(value = "/client", produces = "application/json")
public class ClientController implements GenericController<ClientVo> {
    Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientTableService clientTableService;

    @Autowired
    private ClientLevelTableService clientLevelTableService;

    @Autowired
    private ClientOriginTableService clientOriginTableService;

    @Autowired
    private CountryTableService countryTableService;

    @Autowired
    private RelatedUserTableService relatedUserTableService;

    // 根据当前用户的权限获取列表是所有的还是个人的
    // 过滤条件除了name code email cellphone  telephone clientOriginId clientLevelId  traded countryId  createdUserId
    // 需要加上一个成交额
    // action 需要跟上一个追踪和一个订单查询
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Map getList(@RequestBody TableParameters body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Page<ClientVo> list = clientTableService.getList(body, principal);
        Map<String, Object> map = new HashMap<>();
        List<HashMap<String, Object>> listReturn = null;
        if (list != null && list.iterator().hasNext()) {
            listReturn = new ArrayList<>();
            for (ClientVo cv : list) {
                HashMap<String, Object> mapTemp = new HashMap<>();
                mapTemp.put("key", cv.getId());
                List<Object> listTmp = new ArrayList<>();
                listTmp.add(cv.getCode());
                listTmp.add(cv.getName());
                listTmp.add(cv.isTraded());
                listTmp.add(cv.getOrderAmount());
                listTmp.add(cv.getClientOriginId());
                listTmp.add(cv.getClientLevelId());
                listTmp.add(cv.getCreatedUserId());
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
        map.put("sorts", body.getSorts());
        return map;
    }

    @Override
    @RequestMapping(value = "/info", method = {RequestMethod.GET})
    public ClientVo info(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return clientTableService.info(key, principal);
    }

    @Override
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result update(@RequestParam Long key, @RequestBody ClientVo clientVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        clientVo.setId(key);
        return clientTableService.update(clientVo, null, principal);
    }

    @Override
    public VueResults.Result update(@RequestParam(name = "key") Long key, @RequestParam(name = "files", required = false) MultipartFile[] files, ClientVo clientVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result save(@RequestBody ClientVo clientVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return clientTableService.save(clientVo, null, principal);
    }

    @Override
    public VueResults.Result save(@RequestParam(name = "files", required = false) MultipartFile[] files, ClientVo clientVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    // 删除要慎重，当有成单的不可删除!!
    @Override
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        clientTableService.delete(key, principal);
    }

    // 判断client是否已成单，才给出clientlevel的select列表，需要给出来源 country 其他的不需要给出，更新的时候可以更新来源/
    @Override
    @RequestMapping(value = "/form/rulechange", method = {RequestMethod.GET, RequestMethod.POST})
    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Map mapReturn = new HashMap<>();
        if (body != null) {
            // 给出options
            mapReturn.put("clientOrigins", clientOriginTableService.getClientOriginOptions(principal));
            if ("list".equals(body.get("type"))) {
                mapReturn.put("clientLevels", clientLevelTableService.getClientLevelOptions(principal));
                if (CommonMethods.isAdmin(principal)) {
                    // 进行用户列表的返回
                    mapReturn.put("createdUsers", relatedUserTableService.getRelatedUserOptions(principal));
                }
            } else if ("form".equals(body.get("type"))) {
                mapReturn.put("countries", countryTableService.getCountryOptions(principal));
                if (CommonMethods.isAdmin(principal)) {
                    mapReturn.put("relatedUsers", relatedUserTableService.getRelatedUserOptions(principal));
                }
                if (key == null) {
                    mapReturn.put("clientLevels", clientLevelTableService.getClientLevelNoOrderAmountOptions(principal));
                } else {
                    if (!clientTableService.info(key, principal).isTraded()) {
                        mapReturn.put("clientLevels", clientLevelTableService.getClientLevelNoOrderAmountOptions(principal));
                    }
                }
            }
        }
        return mapReturn;
    }
}
