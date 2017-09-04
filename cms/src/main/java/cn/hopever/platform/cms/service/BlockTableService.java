package cn.hopever.platform.cms.service;

import cn.hopever.platform.cms.vo.BlockVo;
import cn.hopever.platform.utils.web.GenericService;

import java.util.List;

/**
 * Created by Donghui Huo on 2017/8/31.
 * 根据template 获取blocks
 * 根据websiteId 和template 获取blocks
 * 根据article和template获取blocks
 * 上述的获取blocks是整体页面的blocks，包含覆盖性的
 * 分页查询的过程中，过滤条件是template， website，article【给出的是单独属于这个article，或者这个website的，或者关联的，当选择article时，会自动过滤其所属的templat】
 * 其他则没有差别
 * 缺少一个formatblockContent的操作？？？？？？后续预览的时候添加
 **/
public interface BlockTableService extends GenericService<BlockVo> {
    public List<BlockVo> getBlocksByTemplate(Long templateId);

    public List<BlockVo> getBlocksByTemplateAndWebsite(Long templateId, Long websiteId);

    public List<BlockVo> getBlocksByArticle(Long articleId);
}
