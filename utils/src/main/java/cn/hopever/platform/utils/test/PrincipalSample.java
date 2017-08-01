package cn.hopever.platform.utils.test;

import lombok.Data;

import java.security.Principal;

/**
 * Created by Donghui Huo on 2017/8/1.
 */
@Data
public class PrincipalSample implements Principal {
    private String name;

    public PrincipalSample() {
    }

    public PrincipalSample(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
