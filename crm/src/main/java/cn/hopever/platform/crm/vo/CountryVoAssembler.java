package cn.hopever.platform.crm.vo;

import cn.hopever.platform.crm.domain.CountryTable;
import cn.hopever.platform.utils.tools.BeanUtils;
import cn.hopever.platform.utils.web.GenericVoAssembler;
import org.springframework.stereotype.Component;

/**
 * Created by Donghui Huo on 2016/9/1.
 */
@Component
public class CountryVoAssembler implements GenericVoAssembler<CountryVo, CountryTable> {

    @Override
    public CountryVo toResource(CountryTable countryTable) {
        CountryVo countryVo = new CountryVo();
        BeanUtils.copyNotNullProperties(countryTable, countryVo);
        return countryVo;
    }

    @Override
    public CountryTable toDomain(CountryVo countryVo, CountryTable countryTable) {
        BeanUtils.copyNotNullProperties(countryVo, countryTable, "id");
        return countryTable;
    }
}
