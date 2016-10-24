package com.webhybird.module.sso.controller;

import com.webhybird.module.sso.abstractvalidate.LoginValidateByUserNameAndPassword;
import com.webhybird.module.sso.tokenstore.TokenCache;
import com.webhybird.util.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhongfu on 2015/5/18.
 */
@Controller
@RequestMapping()
public class ValidateController extends BaseController{

    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 基于配置注入
     */
    @Autowired
    private LoginValidateByUserNameAndPassword loginValidate;

    /**
     * 默认跳转到登录页面
     * @return
     */
    @RequestMapping("/")
    public String index(){
        return "login";
    }

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping(value = "authcenter")
    public String loginform(String redirectUrl,Model model){
        model.addAttribute("redirectUrl",redirectUrl);
        return "login";
    }

    /**
     * 登录验证
     * @param loginname
     * @param password
     * @return
     */
    @RequestMapping(value = "authcenter",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> validateLogin(String loginname,String password,String redirectUrl){
        Map<String,String> map = new HashMap<>();
        if(this.loginValidate.validate(loginname, password)){
            TokenCache.generateToken(loginname,redirectUrl,this.response,this.request);
            this.log.info("登录成功！");
            map.put("status","success");
        }else{
            this.log.info("登录失败");
            map.put("status","faile");
        }
        return map;
    }




}
