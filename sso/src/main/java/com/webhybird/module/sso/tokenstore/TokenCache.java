package com.webhybird.module.sso.tokenstore;

import com.webhybird.module.sso.common.ProjUtil;
import com.webhybird.module.sso.common.Token;
import com.webhybird.util.RandomUtils;
import org.springframework.util.Assert;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhongfu on 2015/5/26.
 */
public class TokenCache {
    /**
     * TOKEN 缓存
     */
    private static final Map<String,Token> tokenMap = new HashMap<>();
    /**
     * token 默认有效时间
     */
    private static final long defaultActiveTime = 86400L;

    /**
     * 缓存认证票据
     * @param token
     */
    public static void put(Token token){
        Assert.notNull(token, "token信息不能为空");
        Assert.hasText(token.getUserid(),"USERID不能为空");
        tokenMap.put(token.getUserid(),token);
    }

    /**
     * 根据用户登录id获取token信息
     * @param loginId
     * @return
     */
    public static Token get(String loginId){
        Assert.hasText(loginId,"回话ID不能为空");
        return tokenMap.get(loginId);
    }

    /**
     * 校验token的有效性
     * 根据当前时间与token创建时间的差与有效期限的对比
     * @param token
     * @return
     */
    public static boolean isActive(Token token){
        Assert.hasText(token.getUserid(),"USERID不能为空");
        Date currentDate = new Date();
        boolean b = token.getMaxActiveTime() > currentDate.getTime() - token.getCreateTime();
        if(!b){
            remove(token.getUserid());
        }
        return b;
    }

    /**
     * 删除已经失效的token
     * @param loginid
     */
    public static void remove(String loginid){
        Assert.hasText(loginid,"USERID不能为空");
        tokenMap.remove(loginid);
    }

    /**
     *
     * @param username
     * @param redirectUrl
     * @param response
     * @param request
     */
    public static void generateToken(String username,String redirectUrl,HttpServletResponse response,HttpServletRequest request){
        String domain = null;
        String tokenUUID = RandomUtils.uuid2();
        Token token = new Token();
        token.setUserid(username);
        token.setClientip(getClientIp(request));
        token.setCreateTime(new Date().getTime());
        token.setTokenUUID(tokenUUID);
        token.setSessionId(request.getSession().getId());
        if(ProjUtil.getConfigProperties().get("MAX_ACTIVE_TIME") != null && !"".equals(ProjUtil.getConfigProperties().get("MAX_ACTIVE_TIME"))){
            token.setMaxActiveTime(Long.valueOf((String) ProjUtil.getConfigProperties().get("MAX_ACTIVE_TIME")));
        }else {
            token.setMaxActiveTime(defaultActiveTime);
        }
        if(redirectUrl != null && !"".equals(redirectUrl)){
             domain = redirectUrl.substring(redirectUrl.indexOf("//") + 2);
            if(redirectUrl.contains("/")){
                domain = domain.substring(0,domain.indexOf("/"));
            }
            token.setDomain(domain);
        }else{
            token.setDomain(request.getServerName());
            domain = request.getServerName();
        }

        //需要加密
        Cookie cookie = new Cookie("TOKEN", token.toJson());
        cookie.setDomain(domain);
        cookie.setPath("/");
        response.addCookie(cookie);

        put(token);
    }

    /**
     *
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ip!=null && ip.length()>15){ //"***.***.***.***".length() = 15
            if(ip.indexOf(",")>0){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }
        return ip;
    }

}
