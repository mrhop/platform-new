package cn.hopever.platform.cms.service;

import cn.hopever.platform.cms.vo.BlockTagVo;
import cn.hopever.platform.utils.web.GenericService;
import cn.hopever.platform.utils.web.SelectOption;

import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/31.
 * 关于block tag，主要用于一些内置的tag的使用，比如 hotnews， topevent， mediaLib，fileLib，mediaSlider等
 * 所以其没有额外或者多余的操作
 */
public interface BlockTagTableService extends GenericService<BlockTagVo> {
    public List<SelectOption> getOptions();
}
