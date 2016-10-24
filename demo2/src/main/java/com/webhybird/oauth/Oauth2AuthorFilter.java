/**
 *
 */
package com.webhybird.oauth;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.springframework.http.HttpHeaders;

/**
 * ***********************************************************
 * @类名 ：Oauth2AuthorFilter.java
 *
 * @DESCRIPTION : 检验 accesstoken ，没有则检验授权码。有授权码，获取 accesstoken，没有返回失败。
 * @AUTHOR : wangzhongfu
 * @DATE : 2016/9/8
 * ***********************************************************
 */
public class Oauth2AuthorFilter implements Filter {

    /**
     * oauth access token 检验地址
     */
    private String accessTokenCheckUrl;

    /**
     * Called by the web container to indicate to a filter that it is being placed into
     * service. The servlet container calls the init method exactly once after instantiating the
     * filter. The init method must complete successfully before the filter is asked to do any
     * filtering work. <br><br>
     * <p>
     * The web container cannot place the filter into service if the init method either<br>
     * 1.Throws a ServletException <br>
     * 2.Does not return within a time period defined by the web container
     *
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if(filterConfig.getInitParameter("accessTokenCheckUrl") != null && !filterConfig.getInitParameter("accessTokenCheckUrl").equals("")){
            this.accessTokenCheckUrl = filterConfig.getInitParameter("accessTokenCheckUrl");
        }
    }

    /**
     * The <code>doFilter</code> method of the Filter is called by the container
     * each time a request/response pair is passed through the chain due
     * to a client request for a resource at the end of the chain. The FilterChain passed in to this
     * method allows the Filter to pass on the request and response to the next entity in the
     * chain.<p>
     * A typical implementation of this method would follow the following pattern:- <br>
     * 1. Examine the request<br>
     * 2. Optionally wrap the request object with a custom implementation to
     * filter content or headers for input filtering <br>
     * 3. Optionally wrap the response object with a custom implementation to
     * filter content or headers for output filtering <br>
     * 4. a) <strong>Either</strong> invoke the next entity in the chain using the FilterChain object (<code>chain.doFilter()</code>), <br>
     * * 4. b) <strong>or</strong> not pass on the request/response pair to the next entity in the filter chain to block the request processing<br>
     * * 5. Directly set headers on the response after invocation of the next entity in the filter chain.
     *
     * @param request
     * @param response
     * @param chain
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        //构建OAuth资源请求
        try {
            OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest((HttpServletRequest) request, ParameterStyle.QUERY); // queryString 方式获取参数
            String accessToken = oauthRequest.getAccessToken();

/*            if(accessToken == null || accessToken.equals("")){
                oAuthFaileResponse(res);
            }*/

            //验证Access Token
            if (!checkAccessToken(accessToken)) {
                // 如果不存在/过期了，返回未验证错误，需重新验证
                oAuthFaileResponse(res);
            }

        } catch (OAuthSystemException e) {
            try {
                oAuthFaileResponse(res);
            } catch (OAuthSystemException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "error trying to access oauth server", ex);
            }
        } catch (OAuthProblemException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "error trying to access oauth server", e);
            try {
                oAuthFaileResponse(res);
            } catch (OAuthSystemException e1) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "error trying to access oauth server", e);
            }
        }

        chain.doFilter(request, response);

    }

    /**
     * oAuth认证失败时的输出
     * @param res
     * @throws OAuthSystemException
     * @throws IOException
     */
    private void oAuthFaileResponse(HttpServletResponse res) throws OAuthSystemException, IOException {
        OAuthResponse oauthResponse = OAuthRSResponse
                .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                .setRealm("oauth-server")
                .setError(OAuthError.ResourceResponse.INVALID_TOKEN)
                .buildHeaderMessage();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=utf-8");
        res.addHeader(OAuth.HeaderType.WWW_AUTHENTICATE, oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
        PrintWriter writer = res.getWriter();
        writer.write("{\"resultCode\": \"authentication_fail\",\"resultMsg\": \"accessToken无效或已过期\"}");
        writer.flush();
        writer.close();
    }

    /**
     * 验证accessToken
     * @param accessToken
     * @return
     * @throws IOException
     */
    private boolean checkAccessToken(String accessToken) throws IOException {
        URL url = new URL(this.accessTokenCheckUrl + "?accessToken=" + accessToken);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.disconnect();
        return HttpServletResponse.SC_OK == conn.getResponseCode();
    }

    /**
     * Called by the web container to indicate to a filter that it is being taken out of service. This
     * method is only called once all threads within the filter's doFilter method have exited or after
     * a timeout period has passed. After the web container calls this method, it will not call the
     * doFilter method again on this instance of the filter. <br><br>
     * <p>
     * This method gives the filter an opportunity to clean up any resources that are being held (for
     * example, memory, file handles, threads) and make sure that any persistent state is synchronized
     * with the filter's current state in memory.
     */
    @Override
    public void destroy() {

    }

    public String getAccessTokenCheckUrl() {
        return accessTokenCheckUrl;
    }

    public void setAccessTokenCheckUrl(String accessTokenCheckUrl) {
        this.accessTokenCheckUrl = accessTokenCheckUrl;
    }
}
