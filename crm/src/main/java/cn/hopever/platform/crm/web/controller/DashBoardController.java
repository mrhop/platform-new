package cn.hopever.platform.crm.web.controller;

import cn.hopever.platform.crm.config.CommonMethods;
import cn.hopever.platform.crm.service.*;
import cn.hopever.platform.utils.tools.DateFormat;
import cn.hopever.platform.utils.web.ChartModal;
import cn.hopever.platform.utils.web.SelectOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;

/**
 * Created by Donghui Huo on 2017/9/6.
 */
@RestController
//@CrossOrigin
@RequestMapping(value = "/dashboard", produces = "application/json")
public class DashBoardController {

    @Autowired
    private ClientTableService clientTableService;
    @Autowired
    private CountryTableService countryTableService;
    @Autowired
    private OrderTableService orderTableService;
    @Autowired
    private RelatedUserTableService relatedUserTableService;
    @Autowired
    private ClientTrackTableService clientTrackTableService;
    @Autowired
    private ClientLevelTableService clientLevelTableService;


    @RequestMapping(value = "/clientorigin", method = {RequestMethod.POST})
    public List<List<?>> clientorigin(@RequestBody(required = false) Map body, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Principal principal, Authentication authentication) throws Exception {
        if (CommonMethods.isAdmin(principal)) {
            Float orderAmount = null;
            if (body != null && body.get("orderAmount") != null) {
                orderAmount = Float.valueOf(body.get("orderAmount").toString());
            }
            List<Object[]> list = clientTableService.analyzeClientOrigin(orderAmount);
            if (list != null && list.size() > 0) {
                List<List<?>> listReturn = new ArrayList<>();
                List<Object> listLabel = new ArrayList<>();
                List<Object> listValue = new ArrayList<>();
                for (Object[] oArr : list) {
                    listLabel.add(oArr[0]);
                    listValue.add(oArr[1]);
                }
                listReturn.add(listLabel);
                listReturn.add(listValue);
                return listReturn;
            }
        }
        return null;
    }

    @RequestMapping(value = "/country", method = {RequestMethod.POST})
    public List<List<?>> country(@RequestBody(required = false) Map body, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Principal principal, Authentication authentication) throws Exception {
        if (CommonMethods.isAdmin(principal)) {
            Date beginDate = null;
            Date endDate = null;
            if (body != null) {
                if (body.get("beginDate") != null) {
                    beginDate = new Date(Long.valueOf(body.get("beginDate").toString()));
                }
                if (body.get("endDate") != null) {
                    endDate = new Date(Long.valueOf(body.get("endDate").toString()));
                }
            }
            List<SelectOption> listCountry = countryTableService.getCountryOptions(principal);

            List<Object[]> countryClients = clientTableService.analyzeClientFromCountry(beginDate, endDate);
            List<Object[]> countryOrderAnalyze = orderTableService.analyzeOrderFromCountry(beginDate, endDate);

            if (listCountry != null && listCountry.size() > 0) {
                List<List<?>> listReturn = new ArrayList<>();
                List<String> listCountries = new ArrayList<>();
                List<Integer> listClients = new ArrayList<>();
                List<Float> listOrderAmount = new ArrayList<>();
                List<Integer> listOrderQuantity = new ArrayList<>();
                for (SelectOption selectOption : listCountry) {
                    listCountries.add(selectOption.getLabel());
                    boolean flagClient = false;
                    boolean flagOrder = false;
                    for (Object[] oArr : countryClients) {
                        if (selectOption.getValue().toString().equals(oArr[0].toString())) {
                            flagClient = true;
                            listClients.add(Integer.valueOf(oArr[1].toString()));
                            break;
                        }
                    }
                    for (Object[] oArr : countryOrderAnalyze) {
                        if (selectOption.getValue().toString().equals(oArr[0].toString())) {
                            flagOrder = true;
                            if (oArr[1] != null) {
                                listOrderAmount.add(11f);
                            } else {
                                listOrderAmount.add(0f);
                            }
                            listOrderQuantity.add(Integer.valueOf(oArr[2].toString()));
                            break;
                        }
                    }
                    if (!flagClient) {
                        listClients.add(0);
                    }
                    if (!flagOrder) {
                        listOrderAmount.add(0f);
                        listOrderQuantity.add(0);
                    }
                }
                listReturn.add(listCountries);
                listReturn.add(listClients);
                listReturn.add(listOrderAmount);
                listReturn.add(listOrderQuantity);
                return listReturn;
            }
        }
        return null;
    }

    @RequestMapping(value = "/clientamount", method = {RequestMethod.POST})
    public List<List<?>> clientAmount(@RequestBody(required = false) Map body, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Principal principal, Authentication authentication) throws Exception {
        if (CommonMethods.isAdmin(principal)) {
            Date beginDate = null;
            Date endDate = null;
            if (body != null) {
                if (body.get("beginDate") != null) {
                    beginDate = new Date(Long.valueOf(body.get("beginDate").toString()));
                }
                if (body.get("endDate") != null) {
                    endDate = new Date(Long.valueOf(body.get("endDate").toString()));
                }
            }

            List<Object[]> clientAmounts = clientTableService.analyzeOrderAmountFromClient(beginDate, endDate);
            if (clientAmounts != null && clientAmounts.size() > 0) {
                List<List<?>> listReturn = new ArrayList<>();
                List<Object> listLabel = new ArrayList<>();
                List<Object> listValue = new ArrayList<>();
                for (Object[] oArr : clientAmounts) {
                    listLabel.add(oArr[0]);
                    if (oArr[1] != null) {
                        listValue.add(oArr[1]);
                    } else {
                        listValue.add(0f);
                    }
                }
                listReturn.add(listLabel);
                listReturn.add(listValue);
                return listReturn;
            }
        }
        return null;
    }

    @RequestMapping(value = "/useramount", method = {RequestMethod.POST})
    public List<List<?>> userAmount(@RequestBody(required = false) Map body, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Principal principal, Authentication authentication) throws Exception {
        if (CommonMethods.isAdmin(principal)) {
            Date beginDate = null;
            Date endDate = null;
            if (body != null) {
                if (body.get("beginDate") != null) {
                    beginDate = new Date(Long.valueOf(body.get("beginDate").toString()));
                }
                if (body.get("endDate") != null) {
                    endDate = new Date(Long.valueOf(body.get("endDate").toString()));
                }
            }

            List<Object[]> userAmounts = orderTableService.analyzeOrderAmountFromUser(beginDate, endDate);
            if (userAmounts != null && userAmounts.size() > 0) {
                List<List<?>> listReturn = new ArrayList<>();
                List<Object> listLabel = new ArrayList<>();
                List<Object> listValue = new ArrayList<>();
                for (Object[] oArr : userAmounts) {
                    listLabel.add(oArr[0]);
                    if (oArr[1] != null) {
                        listValue.add(oArr[1]);
                    } else {
                        listValue.add(0f);
                    }
                }
                listReturn.add(listLabel);
                listReturn.add(listValue);
                return listReturn;
            }
        }
        return null;
    }

    @RequestMapping(value = "/ordersum", method = {RequestMethod.POST})
    public List<List<?>> orderSum(@RequestBody(required = false) Map body, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Principal principal, Authentication authentication) throws Exception {
        if (CommonMethods.isAdmin(principal)) {
            Date beginDate = null;
            Date endDate = null;
            Long clientId = null;
            if (body != null) {
                if (body.get("beginDate") != null) {
                    beginDate = new Date(Long.valueOf(body.get("beginDate").toString()));
                }
                if (body.get("endDate") != null) {
                    endDate = new Date(Long.valueOf(body.get("endDate").toString()));
                }
                if (body.get("client") != null) {
                    clientId = Long.valueOf(body.get("client").toString());
                }
            }

            List<Object[]> ordersHistory = orderTableService.analyzeOrderFromClient(beginDate, endDate, clientId);
            if (ordersHistory != null && ordersHistory.size() > 0) {
                List<List<?>> listReturn = new ArrayList<>();
                List<ChartModal.LineModal> listAmount = new ArrayList<>();
                List<ChartModal.LineModal> listQuantity = new ArrayList<>();
                for (Object[] oArr : ordersHistory) {
                    String dateStr = DateFormat.sdfDate.format((Date) oArr[0]);
                    if (oArr[1] != null) {
                        ChartModal.LineModal lineModal = new ChartModal.LineModal(dateStr, oArr[1]);
                        listAmount.add(lineModal);
                    }
                    ChartModal.LineModal lineModal1 = new ChartModal.LineModal(dateStr, oArr[2]);
                    listQuantity.add(lineModal1);
                }
                listReturn.add(listAmount);
                listReturn.add(listQuantity);
                return listReturn;
            }
        }
        return null;
    }

    @RequestMapping(value = "/userordersum", method = {RequestMethod.POST})
    public List<List<?>> userOrderSum(@RequestBody(required = false) Map body, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Principal principal, Authentication authentication) throws Exception {
        Date beginDate = null;
        Date endDate = null;
        Long userId = null;
        if (!CommonMethods.isAdmin(principal)) {
            userId = relatedUserTableService.getOneByAccount(principal.getName()).getId();
        } else {
            if (body != null && body.get("user") != null) {
                userId = Long.valueOf(body.get("user").toString());
            }
        }
        if (body != null) {
            if (body.get("beginDate") != null) {
                beginDate = new Date(Long.valueOf(body.get("beginDate").toString()));
            }
            if (body.get("endDate") != null) {
                endDate = new Date(Long.valueOf(body.get("endDate").toString()));
            }
        }
        List<Object[]> ordersHistory = orderTableService.analyzeOrderFromCreatedUser(beginDate, endDate, userId);
        if (ordersHistory != null && ordersHistory.size() > 0) {
            List<List<?>> listReturn = new ArrayList<>();
            List<ChartModal.LineModal> listAmount = new ArrayList<>();
            List<ChartModal.LineModal> listQuantity = new ArrayList<>();
            for (Object[] oArr : ordersHistory) {
                String dateStr = DateFormat.sdfDate.format((Date) oArr[0]);
                if (oArr[1] != null) {
                    ChartModal.LineModal lineModal = new ChartModal.LineModal(dateStr, oArr[1]);
                    listAmount.add(lineModal);
                }
                ChartModal.LineModal lineModal1 = new ChartModal.LineModal(dateStr, oArr[2]);
                listQuantity.add(lineModal1);
            }
            listReturn.add(listAmount);
            listReturn.add(listQuantity);
            return listReturn;
        }
        return null;
    }

    @RequestMapping(value = "/newclient", method = {RequestMethod.POST})
    public List<List<?>> newClient(@RequestBody(required = false) Map body, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Principal principal, Authentication authentication) throws Exception {
        Date beginDate = null;
        Date endDate = null;
        Long userId = null;
        Long clientLevelId = null;
        if (!CommonMethods.isAdmin(principal)) {
            userId = relatedUserTableService.getOneByAccount(principal.getName()).getId();
        } else {
            if (body != null && body.get("user") != null) {
                userId = Long.valueOf(body.get("user").toString());
            }
        }
        if (body != null) {
            if (body.get("beginDate") != null) {
                beginDate = new Date(Long.valueOf(body.get("beginDate").toString()));
            }
            if (body.get("endDate") != null) {
                endDate = new Date(Long.valueOf(body.get("endDate").toString()));
            }
            if (body.get("clientLevel") != null) {
                clientLevelId = Long.valueOf(body.get("clientLevel").toString());
            }
        }
        List<Object[]> clientsHistory = clientTableService.analyzeClientFromCreatedUser(beginDate, endDate, clientLevelId, userId);
        if (clientsHistory != null && clientsHistory.size() > 0) {
            List<List<?>> listReturn = new ArrayList<>();
            List<ChartModal.LineModal> listQuantity = new ArrayList<>();
            LinkedHashMap<Object, List<ChartModal.LineModal>> map = new LinkedHashMap<>();
            for (Object[] oArr : clientsHistory) {
                String dateStr = DateFormat.sdfDate.format((Date) oArr[0]);
                ChartModal.LineModal lineModal = new ChartModal.LineModal(dateStr, oArr[1]);
                listQuantity.add(lineModal);
            }
            listReturn.add(listQuantity);
            return listReturn;
        }
        return null;
    }

    // 最后一个，并做出测试
    @RequestMapping(value = "/clienttrace", method = {RequestMethod.POST})
    public List<List<?>> clientTrace(@RequestBody(required = false) Map body, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Principal principal, Authentication authentication) throws Exception {
        Date beginDate = null;
        Date endDate = null;
        Long userId = null;
        Long clientId = null;
        if (!CommonMethods.isAdmin(principal)) {
            userId = relatedUserTableService.getOneByAccount(principal.getName()).getId();
        } else {
            if (body != null && body.get("user") != null) {
                userId = Long.valueOf(body.get("user").toString());
            }
        }
        if (body != null) {
            if (body.get("beginDate") != null) {
                beginDate = new Date(Long.valueOf(body.get("beginDate").toString()));
            }
            if (body.get("endDate") != null) {
                endDate = new Date(Long.valueOf(body.get("endDate").toString()));
            }
            if (body.get("client") != null) {
                clientId = Long.valueOf(body.get("client").toString());
            }
        }
        List<Object[]> clientTracksHistory = clientTrackTableService.analyzeClientTrackFromTrackUser(beginDate, endDate, clientId, userId);
        if (clientTracksHistory != null && clientTracksHistory.size() > 0) {
            List<List<?>> listReturn = new ArrayList<>();
            List<ChartModal.LineModal> listQuantity = new ArrayList<>();
            LinkedHashMap<Object, List<ChartModal.LineModal>> map = new LinkedHashMap<>();
            for (Object[] oArr : clientTracksHistory) {
                String dateStr = DateFormat.sdfDate.format((Date) oArr[0]);
                ChartModal.LineModal lineModal = new ChartModal.LineModal(dateStr, oArr[1]);
                listQuantity.add(lineModal);
            }
            listReturn.add(listQuantity);
            return listReturn;
        }
        return null;
    }

    @RequestMapping(value = "/options", method = {RequestMethod.GET})
    public Map<String, List<SelectOption>> getOptions(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Principal principal, Authentication authentication) {
        Map<String, List<SelectOption>> mapReturn = new HashMap<>();
        if (CommonMethods.isAdmin(principal)) {
            mapReturn.put("users", relatedUserTableService.getRelatedUserOptions(principal));
        }
        mapReturn.put("clients", clientTableService.getClientOptions(principal));
        mapReturn.put("clientLevels", clientLevelTableService.getClientLevelOptions(principal));
        return mapReturn;
    }
}
