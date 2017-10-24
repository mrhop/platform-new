package cn.hopever.platform.crm.service.impl;

import cn.hopever.platform.crm.config.CommonMethods;
import cn.hopever.platform.crm.domain.ProductPriceHistoryTable;
import cn.hopever.platform.crm.domain.ProductTable;
import cn.hopever.platform.crm.repository.*;
import cn.hopever.platform.crm.service.ProductTableService;
import cn.hopever.platform.crm.vo.ProductPriceHistoryVo;
import cn.hopever.platform.crm.vo.ProductPriceHistoryVoAssembler;
import cn.hopever.platform.crm.vo.ProductVo;
import cn.hopever.platform.crm.vo.ProductVoAssembler;
import cn.hopever.platform.utils.moji.MojiUtils;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Donghui Huo on 2017/10/10.
 */
@Service
@Transactional
public class ProductTableServiceImpl implements ProductTableService {
    Logger logger = LoggerFactory.getLogger(ProductTableServiceImpl.class);
    @Autowired
    private MojiUtils mojiUtils;
    @Autowired
    private ProductTableRepository productTableRepository;
    @Autowired
    private ProductCategoryTableRepository productCategoryTableRepository;

    @Autowired
    private CustomProductTableRepository customProductTableRepository;
    @Autowired
    private ProductPriceHistoryTableRepository productPriceHistoryTableRepository;
    @Autowired
    private RelatedUserTableRepository relatedUserTableRepository;


    @Autowired
    private ProductVoAssembler productVoAssembler;
    @Autowired
    private ProductPriceHistoryVoAssembler productPriceHistoryVoAssembler;

    @Override
    public Page<ProductVo> getList(TableParameters body, Principal principal) {
        Map<String, Object> map = body.getFilters();
        if (map.get("productCategoryId") != null) {
            map.put("productCategoryTable", productCategoryTableRepository.findOne(Long.valueOf(map.get("productCategoryId").toString())));
            map.remove("productCategoryId");
        }
        body.setFilters(map);
        PageRequest pageRequest;
        if (body.getSorts() == null || body.getSorts().isEmpty()) {
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.DESC, "createdDate");
        } else {
            String key = body.getSorts().keySet().iterator().next();
            pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.fromString(body.getSorts().get(key)), key);
        }
        Page<ProductTable> page = customProductTableRepository.findByFilters(body.getFilters(), pageRequest);
        List<ProductVo> list = new ArrayList<>();
        for (ProductTable productTable : page) {
            list.add(productVoAssembler.toResource(productTable));
        }
        return new PageImpl<>(list, pageRequest, page.getTotalElements());
    }

    @Override
    public void delete(Long id, Principal principal) {
        productTableRepository.delete(id);
    }

    @Override
    public ProductVo info(Long id, Principal principal) {
        return productVoAssembler.toResource(productTableRepository.findOne(id));
    }

    @Override
    public VueResults.Result update(ProductVo productVo, MultipartFile[] files, Principal principal) {
        ProductTable productTable = productTableRepository.findOne(productVo.getId());
        if (productVo.getProductCategoryId() != null) {
            productTable.setProductCategoryTable(productCategoryTableRepository.findOne(productVo.getProductCategoryId()));
        }
        if (!productVo.getCostPrice().equals(productTable.getCostPrice()) || !productVo.getSalePrice().equals(productTable.getSalePrice())) {
            ProductPriceHistoryTable productPriceHistoryTableTemp = productPriceHistoryTableRepository.findTopByProductTableOrderByBeginDateDesc(productTable);
            ProductPriceHistoryTable productPriceHistoryTable = new ProductPriceHistoryTable();
            productPriceHistoryTable.setCostPrice(productTable.getCostPrice());
            productPriceHistoryTable.setEndDate(new Date());
            productPriceHistoryTable.setBeginDate(productPriceHistoryTableTemp != null ? new Date(productPriceHistoryTableTemp.getEndDate().getTime() + 1000) : productTable.getCreatedDate());
            productPriceHistoryTable.setProductTable(productTable);
            productPriceHistoryTable.setSalePrice(productTable.getSalePrice());
            productPriceHistoryTableRepository.save(productPriceHistoryTable);
            if (productTable.getProductPriceHistoryTables() != null) {
                productTable.getProductPriceHistoryTables().add(productPriceHistoryTable);
            } else {
                List<ProductPriceHistoryTable> list = new ArrayList<>();
                list.add(productPriceHistoryTable);
                productTable.setProductPriceHistoryTables(list);
            }
        }
        List<String> prePictures = productTable.getPictures();
        if (files != null && files.length > 0) {
            try {
                Map<String, List<String>> mapPictures = mojiUtils.uploadImg("crm/product/" + productTable.getCode(), files);
                List<String> list = mapPictures.get("fileKeys");
                if (list != null && list.size() > 0) {
                    if (prePictures != null) {
                        for (int i = 0; i < files.length; i++) {
                            MultipartFile file = files[i];
                            if (!file.isEmpty()) {
                                if (i >= prePictures.size()) {
                                    prePictures.add(list.remove(0));
                                }
                            } else {
                                prePictures.set(i, list.remove(0));
                            }
                        }
                        productTable.setPictures(prePictures);
                    } else {
                        productTable.setPictures(list);
                    }
                }
            } catch (Exception e) {
                logger.error("update theme screemshots failed", e);
            }
        }
        productVoAssembler.toDomain(productVo, productTable);
        productTableRepository.save(productTable);
        return null;
    }

    @Override
    public VueResults.Result save(ProductVo productVo, MultipartFile[] files, Principal principal) {
        ProductTable productTable = new ProductTable();
        if (productVo.getProductCategoryId() != null) {
            productTable.setProductCategoryTable(productCategoryTableRepository.findOne(productVo.getProductCategoryId()));
        }
        productTable.setCode(CommonMethods.generateCode("product"));
        if (files != null && files.length > 0) {
            try {
                Map<String, List<String>> mapPictures = mojiUtils.uploadImg("crm/product/" + productTable.getCode(), files);
                List<String> list = mapPictures.get("fileKeys");
                if (list != null && list.size() > 0) {
                    productTable.setPictures(list);
                }
            } catch (Exception e) {
                logger.error("update theme screemshots failed", e);
            }
        }
        productTable.setCreatedDate(new Date());
        productTable.setCreatedUser(relatedUserTableRepository.findOneByAccount(principal.getName()));
        productVoAssembler.toDomain(productVo, productTable);
        productTableRepository.save(productTable);
        return null;
    }

    @Override
    public List<ProductPriceHistoryVo> getHistoryListByProductId(TableParameters body, Principal principal, Long productId) {
        List<ProductPriceHistoryTable> productPriceHistoryTables = productPriceHistoryTableRepository.findByProductTableOrderByBeginDateAsc(productTableRepository.findOne(productId));
        List<ProductPriceHistoryVo> list = null;
        if (productPriceHistoryTables != null) {
            list = new ArrayList<>();
            for (ProductPriceHistoryTable productPriceHistoryTable : productPriceHistoryTables) {
                list.add(productPriceHistoryVoAssembler.toResource(productPriceHistoryTable));
            }
        }
        return list;
    }
}
