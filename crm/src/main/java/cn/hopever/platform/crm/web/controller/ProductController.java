package cn.hopever.platform.crm.web.controller;

import cn.hopever.platform.crm.service.ProductCategoryTableService;
import cn.hopever.platform.crm.service.ProductTableService;
import cn.hopever.platform.crm.vo.ProductVo;
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
@CrossOrigin
@RequestMapping(value = "/product", produces = "application/json")
public class ProductController implements GenericController<ProductVo> {
    Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductTableService productTableService;

    @Autowired
    private ProductCategoryTableService productCategoryTableService;

    @Override
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Map getList(@RequestBody TableParameters body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Page<ProductVo> list = productTableService.getList(body, principal);
        Map<String, Object> map = new HashMap<>();
        List<HashMap<String, Object>> listReturn = null;
        if (list != null && list.iterator().hasNext()) {
            listReturn = new ArrayList<>();
            for (ProductVo cv : list) {
                HashMap<String, Object> mapTemp = new HashMap<>();
                mapTemp.put("key", cv.getId());
                List<Object> listTmp = new ArrayList<>();
                listTmp.add(cv.getCode());
                listTmp.add(cv.getName());
                listTmp.add(cv.getProductCategoryId());
                listTmp.add(cv.getCostPrice());
                listTmp.add(cv.getSalePrice());
                listTmp.add(cv.getSpecification());
                listTmp.add(cv.getUnit());
                listTmp.add(cv.getColor());
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
    public ProductVo info(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return productTableService.info(key, principal);
    }

    @Override
    public VueResults.Result update(@RequestParam Long key, @RequestBody ProductVo productVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public VueResults.Result update(@RequestParam(name = "key") Long key, @RequestParam(name = "files", required = false) MultipartFile[] files, ProductVo productVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        productVo.setId(key);
        return productTableService.update(productVo, files, principal);
    }

    @Override
    public VueResults.Result save(@RequestBody ProductVo productVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public VueResults.Result save(@RequestParam(name = "files", required = false) MultipartFile[] files, ProductVo productVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return productTableService.save(productVo, files, principal);
    }


    @Override
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public void delete(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        productTableService.delete(key, principal);
    }

    @Override
    @RequestMapping(value = "/form/rulechange", method = {RequestMethod.GET, RequestMethod.POST})
    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Map mapReturn = new HashMap<>();
        mapReturn.put("productCategories", productCategoryTableService.getProductCategoryOptions(principal));
        return mapReturn;
    }
}
