package cn.hopever.platform.utils.web;

/**
 * Created by Donghui Huo on 2017/8/31.
 */
public interface GenericVoAssembler<T1, T2> {
    public T1 toResource(T2 t2);

    public T2 toDomain(T1 t1, T2 t2);

}
