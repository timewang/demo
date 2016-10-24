package com.webhybird.module.sso.common;

import java.io.Serializable;

/**
 * Created by wangzhongfu on 2015/5/26.
 */
public class Token implements Serializable{

    private String userid;

    private String domain;

    private String clientip;

    private Long maxActiveTime;

    private Long createTime;

    private String tokenUUID;

    private String sessionId;


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getClientip() {
        return clientip;
    }

    public void setClientip(String clientip) {
        this.clientip = clientip;
    }

    public Long getMaxActiveTime() {
        return maxActiveTime;
    }

    public void setMaxActiveTime(Long maxActiveTime) {
        this.maxActiveTime = maxActiveTime;
    }

    public String getTokenUUID() {
        return tokenUUID;
    }

    public void setTokenUUID(String tokenUUID) {
        this.tokenUUID = tokenUUID;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * 将自身转换为JSON
     * @return
     */
    public String toJson(){
        try {
            return JSONUtils.obj2json(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (!userid.equals(token.userid)) return false;
        if (!domain.equals(token.domain)) return false;
        if (!clientip.equals(token.clientip)) return false;
        if (!maxActiveTime.equals(token.maxActiveTime)) return false;
        if (!createTime.equals(token.createTime)) return false;
        if (!tokenUUID.equals(token.tokenUUID)) return false;
        return sessionId.equals(token.sessionId);

    }

    @Override
    public int hashCode() {
        int result = userid.hashCode();
        result = 31 * result + domain.hashCode();
        result = 31 * result + clientip.hashCode();
        result = 31 * result + maxActiveTime.hashCode();
        result = 31 * result + createTime.hashCode();
        result = 31 * result + tokenUUID.hashCode();
        result = 31 * result + sessionId.hashCode();
        return result;
    }
}
