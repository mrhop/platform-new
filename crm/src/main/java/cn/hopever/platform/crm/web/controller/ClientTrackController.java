package cn.hopever.platform.crm.web.controller;

import cn.hopever.platform.utils.security.CommonMethods;
import cn.hopever.platform.crm.service.ClientTrackTableService;
import cn.hopever.platform.crm.service.RelatedUserTableService;
import cn.hopever.platform.crm.vo.ClientTrackVo;
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
@RequestMapping(value = "/clienttrack", produces = "application/json")
public class ClientTrackController implements GenericController<ClientTrackVo> {
    Logger logger = LoggerFactory.getLogger(ClientTrackController.class);

    @Autowired
    private ClientTrackTableService clientTrackTableService;

    @Autowired
    private RelatedUserTableService relatedUserTableService;

    @Override
    public Map getList(@RequestBody TableParameters body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Map getList(@RequestParam Long clientId, @RequestBody TableParameters body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Page<ClientTrackVo> list = clientTrackTableService.getList(body, principal, clientId);
        Map<String, Object> map = new HashMap<>();
        List<HashMap<String, Object>> listReturn = null;
        if (list != null && list.iterator().hasNext()) {
            listReturn = new ArrayList<>();
            for (ClientTrackVo cv : list) {
                HashMap<String, Object> mapTemp = new HashMap<>();
                mapTemp.put("key", cv.getId());
                List<Object> listTmp = new ArrayList<>();
                listTmp.add(cv.getTrackDate());
                listTmp.add(cv.getResult());
                listTmp.add(cv.getDuration());
                listTmp.add(cv.getTrackUserId());
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
    public ClientTrackVo info(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return clientTrackTableService.info(key, principal);
    }

    @Override
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result update(@RequestParam Long key, @RequestBody ClientTrackVo clientTrackVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        clientTrackVo.setId(key);
        return clientTrackTableService.update(clientTrackVo, null, principal);
    }

    @Override
    public VueResults.Result update(@RequestParam(name = "key") Long key, @RequestParam(name = "files", required = false) MultipartFile[] files, ClientTrackVo clientTrackVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result save(@RequestBody ClientTrackVo clientTrackVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return clientTrackTableService.save(clientTrackVo, null, principal);
    }

    @Override
    public VueResults.Result save(@RequestParam(name = "files", required = false) MultipartFile[] files, ClientTrackVo clientTrackVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        clientTrackTableService.delete(key, principal);
    }

    @Override
    @RequestMapping(value = "/form/rulechange", method = {RequestMethod.GET, RequestMethod.POST})
    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if (CommonMethods.isAdmin(principal)) {
            // 进行用户列表的返回
            Map mapReturn = new HashMap<>();
            mapReturn.put("trackUsers", relatedUserTableService.getRelatedUserOptions(principal));
            return mapReturn;
        }
        return null;
    }
}
