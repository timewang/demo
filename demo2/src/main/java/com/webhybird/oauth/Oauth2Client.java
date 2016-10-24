package com.webhybird.oauth;

/**
 * ***********************************************************
 * @类名 ：Oauth2Client.java
 *
 * @DESCRIPTION :
 * @AUTHOR : wangzhongfu
 * @DATE : 2016/9/8
 * ***********************************************************
 */
public class Oauth2Client {
    /**
     * 获取 token url
     */
    private String oauthServerTokenUrl;
    /**
     * client id
     */
    private String clientId;
    /**
     * 客户端密钥
     */
    private String clientSecret;
    /**
     * oauth 重定向 url
     */
    private String oauthServerRedirectUrl;

    public String getOauthServerTokenUrl() {
        return oauthServerTokenUrl;
    }

    public void setOauthServerTokenUrl(String oauthServerTokenUrl) {
        this.oauthServerTokenUrl = oauthServerTokenUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getOauthServerRedirectUrl() {
        return oauthServerRedirectUrl;
    }

    public void setOauthServerRedirectUrl(String oauthServerRedirectUrl) {
        this.oauthServerRedirectUrl = oauthServerRedirectUrl;
    }


}
