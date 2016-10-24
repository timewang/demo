package com.webhybird.module.sso.abstractvalidate;

/**
 * Created by wangzhongfu on 2015/5/18.
 */
public interface LoginValidateByUserNameAndPassword {
    /**
     * 根据用户名和密码验证
     * @param loginname
     * @param password
     * @return
     */
    boolean validate(String loginname,String password);

}
