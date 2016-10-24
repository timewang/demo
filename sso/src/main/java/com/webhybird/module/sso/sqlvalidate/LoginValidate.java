package com.webhybird.module.sso.sqlvalidate;

import com.webhybird.daobase.MyJdbcTemplate;
import com.webhybird.module.sso.abstractvalidate.LoginValidateByUserNameAndPassword;
import com.webhybird.util.PassWordDigester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * Created by wangzhongfu on 2015/5/20.
 */
@Service(value = "loginValidate")
@Transactional
public class LoginValidate implements LoginValidateByUserNameAndPassword {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MyJdbcTemplate myJdbcTemplate;

    private String querySql;

    /**
     * 根据用户名和密码验证
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public boolean validate(String username, String password) {
        Assert.hasText(username, "登录名不能为空！");
        Assert.hasText(password, "密码不能为空不能为空！");
        try{
            Map<String,Object> map =  this.myJdbcTemplate.queryForMap(this.querySql,username, PassWordDigester.getPassMD5(password));
            if(map != null && map.size() > 0 ){
                return true;
            }
        }catch (EmptyResultDataAccessException e){
            log.error("认证失败，用户名或密码错:",e);
            return false;
        }catch (IncorrectResultSizeDataAccessException e){
            log.error("认证失败，用户名或密码错:",e);
            return false;
        }
        return false;
    }

    public String getQuerySql() {
        return querySql;
    }
    @Value("${QUERY_SQL}")
    public void setQuerySql(String querySql) {
        this.querySql = querySql;
    }
}
