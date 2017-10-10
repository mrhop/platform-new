package cn.hopever.platform.crm.web.controller;

import cn.hopever.platform.crm.vo.ProductCategoryVo;
import cn.hopever.platform.utils.web.GenericController;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;

/**
 * Created by Donghui Huo on 2017/9/5.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/productcategory", produces = "application/json")
public class ProductCategoryController implements GenericController<ProductCategoryVo> {
    Logger logger = LoggerFactory.getLogger(ProductCategoryController.class);


    @Override
    public Map getList(@RequestBody TableParameters body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    public ProductCategoryVo info(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    public VueResults.Result update(@RequestParam Long key, @RequestBody ProductCategoryVo productCategoryVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    public VueResults.Result update(@RequestParam(name = "key") Long key, @RequestParam(name = "files", required = false) MultipartFile[] files, ProductCategoryVo productCategoryVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    public VueResults.Result save(@RequestBody ProductCategoryVo productCategoryVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    public VueResults.Result save(@RequestParam(name = "files", required = false) MultipartFile[] files, ProductCategoryVo productCategoryVo, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    public void delete(@RequestParam Long key, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

    }

    @Override
    public Map rulechange(@RequestParam(required = false) Long key, @RequestBody(required = false) Map<String, Object> body, Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }
}
