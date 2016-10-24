package com.webhybird.oauth;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

/**
 * ***********************************************************
 * @类名 ：AccessTokenUtil.java
 *
 * @DESCRIPTION :
 * @AUTHOR : wangzhongfu
 * @DATE : 2016/9/8
 * ***********************************************************
 */
public class AccessTokenUtil {

    /**
     * 根据授权码获取accessToken
     * @param authCode
     * @param oauth2Client
     * @return
     * @throws OAuthProblemException
     * @throws OAuthSystemException
     */
    public static OAuthAccessTokenResponse makeTokenRequestWithAuthCode(String authCode,Oauth2Client oauth2Client) throws OAuthProblemException, OAuthSystemException {

        OAuthClientRequest request = OAuthClientRequest
                .tokenLocation(oauth2Client.getOauthServerTokenUrl())
                .setClientId(oauth2Client.getClientId())
                .setClientSecret(oauth2Client.getClientSecret())
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setCode(authCode)
                .setRedirectURI(oauth2Client.getOauthServerRedirectUrl())
                .buildBodyMessage();

        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

        OAuthAccessTokenResponse oauthResponse = oAuthClient.accessToken(request);

        System.out.println("Access Token: " + oauthResponse.getAccessToken());
        System.out.println("Expires In: " + oauthResponse.getExpiresIn());

        return oauthResponse;

    }

}
