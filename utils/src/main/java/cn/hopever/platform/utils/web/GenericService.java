package cn.hopever.platform.utils.web;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public interface GenericService<T> {

    public Page<T> getList(TableParameters body, Principal principal);

    public void delete(Long id, Principal principal);

    public T info(Long id, Principal principal);

    public VueResults.Result update(T t, MultipartFile[] files, Principal principal);

    public VueResults.Result save(T t, MultipartFile[] files, Principal principal);

}
