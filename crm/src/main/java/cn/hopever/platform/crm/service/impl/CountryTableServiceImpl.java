package cn.hopever.platform.crm.service.impl;

import cn.hopever.platform.crm.domain.CountryTable;
import cn.hopever.platform.crm.repository.CountryTableRepository;
import cn.hopever.platform.crm.service.CountryTableService;
import cn.hopever.platform.crm.vo.CountryVo;
import cn.hopever.platform.crm.vo.CountryVoAssembler;
import cn.hopever.platform.utils.web.SelectOption;
import cn.hopever.platform.utils.web.TableParameters;
import cn.hopever.platform.utils.web.VueResults;
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
import java.util.List;

/**
 * Created by Donghui Huo on 2017/10/10.
 */
@Service
@Transactional
public class CountryTableServiceImpl implements CountryTableService {
    @Autowired
    private CountryTableRepository countryTableRepository;

    @Autowired
    private CountryVoAssembler countryVoAssembler;

    @Override
    public Page<CountryVo> getList(TableParameters body, Principal principal) {
        PageRequest pageRequest = new PageRequest(body.getPager().getCurrentPage() - 1, body.getPager().getPageSize(), Sort.Direction.ASC, "id");
        Page<CountryTable> page = countryTableRepository.findAll(pageRequest);
        List<CountryVo> list = new ArrayList<>();
        for (CountryTable countryTable : page) {
            list.add(countryVoAssembler.toResource(countryTable));
        }
        return new PageImpl<>(list, pageRequest, page.getTotalElements());
    }

    @Override
    public void delete(Long id, Principal principal) {
        countryTableRepository.delete(id);
    }

    @Override
    public CountryVo info(Long id, Principal principal) {
        return countryVoAssembler.toResource(countryTableRepository.findOne(id));
    }

    @Override
    public VueResults.Result update(CountryVo countryVo, MultipartFile[] files, Principal principal) {
        CountryTable countryTable = countryTableRepository.findOne(countryVo.getId());
        countryTableRepository.save(countryVoAssembler.toDomain(countryVo, countryTable));
        return null;
    }

    @Override
    public VueResults.Result save(CountryVo countryVo, MultipartFile[] files, Principal principal) {
        CountryTable countryTable = new CountryTable();
        countryTableRepository.save(countryVoAssembler.toDomain(countryVo, countryTable));
        return null;
    }

    @Override
    public List<SelectOption> getCountryOptions(Principal principal) {
        Iterable<CountryTable> countryTables = countryTableRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        List<SelectOption> list = new ArrayList<>();
        for (CountryTable countryTable : countryTables) {
            list.add(new SelectOption(countryTable.getName(), countryTable.getId()));
        }
        return list;
    }
}
