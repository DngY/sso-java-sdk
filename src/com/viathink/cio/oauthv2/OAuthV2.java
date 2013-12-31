package com.viathink.cio.oauthv2;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.viathink.cio.beans.AccessToken;
import com.viathink.cio.beans.OAuth;
import com.viathink.cio.beans.OAuthException;
import com.viathink.cio.beans.Scopes;
import com.viathink.cio.constants.OAuthConstants;
import com.viathink.cio.utils.HttpManager;
import com.viathink.cio.utils.StrOperate;

/**
 * 
 * OAuth version 2 认证参数实体类 
 *
 */
public class OAuthV2 extends OAuth {
	
	private static final long serialVersionUID = 4952264040006267070L;
	private static Log logger = LogFactory.getLog(OAuthV2.class);
	
	private String clientId = ""; //申请应用时分配到的app_key
	private String clientSecret =""; //申请应用时分配到的app_secret
	private String redirectUri = ""; //授权回调地址
	private String responseType = OAuthConstants.OAUTH_V2_RESPONSE_TYPE_CODE; //响应类型(code或token),默认为code
	private String display = OAuthConstants.OAUTH_V2_DISPLAY_PAGE; //显示授权页的类型(目前可选参数为page、mobile和popup),默认授权页为page授权页
	private String authorizeCode = null; //用来换取accessToken的授权码
	private String grantType = OAuthConstants.OAUTH_V2_GRANT_TYPE_AUTHORIZATION_CODE; // 获取accessToken时传递的授权类型(authorization_code或refresh_token)默认为authorization_code
	
	public OAuthV2() {
			super(OAuthConstants.OAUTH_VERSION_2);
	    }
	
 	/**
     * @param redirectUri 认证成功后重定向地址
     */
    public OAuthV2(String redirectUri) {
    	super(OAuthConstants.OAUTH_VERSION_2);
        this.redirectUri = redirectUri;
    }
    
    /**
     * @param clientId 应用申请到的app_key
     * @param clientSecret 应用申请到的app_secret
     * @param redirectUri 认证成功后重定向地址
     */
    public OAuthV2(String clientId, String clientSecret, String redirectUri) {
    	super(OAuthConstants.OAUTH_VERSION_2);
    	this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }
    
    /**
     * 根据初始化的OAuthV2对象生成完整的认证url地址
     * @return 编码后的认证url
     */
    public String getAuthorizationUrl() {
    	if(this.redirectUri == null || this.redirectUri.isEmpty()) {
    		logger.warn("Redirect url cannot be null or empty, did you forget to set it?");
    		return null;
    	}
    	StringBuilder authorizationUrl = new StringBuilder(OAuthConstants.OAUTH_V2_AUTHORIZE_URL);
    	authorizationUrl.append("?client_id=").append(this.clientId).append("&redirect_uri=").append(StrOperate.paramEncode(this.redirectUri)).append("&response_type=").append(this.responseType).append("&display=").append(this.display);
    	
    	if(this.scopes.isEmpty()) {
    		this.addScope(Scopes.USER_PROFILE_BASIC_SCOPE);
    	}
    	authorizationUrl.append("&scope=").append(StrOperate.paramEncode(generateScopeString()));
    	return authorizationUrl.toString();
    	
    }
    
    /**
     * 将list中的scopes转换为以空格分隔的字符串
     * @return scopes组成的字符串
     */
    private String generateScopeString() {
        if (this.scopes == null || this.scopes.isEmpty()) {
          return "";
        } else {
          StringBuilder scopeStr = new StringBuilder();
          for (Scopes sco : this.scopes) {
            scopeStr.append(sco.getValue()).append(" ");
          }
          scopeStr.deleteCharAt(scopeStr.length() - 1); // 去掉最后一个空格分割符
          return scopeStr.toString();
        }
      }
    
    /**
     * 
     * @param code 从授权服务器获取到的授权码
     * @return AccessToken 从服务器获取到的accessToken对象
     * @throws Exception
     */
    public AccessToken tradeAccessTokenWithCode (String code) throws OAuthException,Exception {
        this.authorizeCode = code;
        String url = OAuthConstants.OAUTH_V2_GET_ACCESS_TOKEN_URL;
        String queryString = StrOperate.getQueryString(getAccessTokenByCodeParamsList());
       
		return StrOperate.stringToAccessToken(new HttpManager().httpPost(url, queryString)) ;
    	
      }
    
    /**
     * Authorize code grant方式中，生成AccessToken阶段需要的参数
     * @return AccessToken阶段需要的参数封装成的list
     */
    public List<NameValuePair> getAccessTokenByCodeParamsList() {
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
            paramsList.add(new BasicNameValuePair("client_id",clientId));
            paramsList.add(new BasicNameValuePair("client_secret",clientSecret));
            paramsList.add(new BasicNameValuePair("redirect_uri", redirectUri));
            paramsList.add(new BasicNameValuePair("grant_type",  "authorization_code"));
            paramsList.add(new BasicNameValuePair("code",  authorizeCode));
        return paramsList;
    }
    
    /**重定向地址*/
    public String getRedirectUri() {
        return redirectUri;
    }

    /**重定向地址*/
    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    /**应用的APP KEY*/
    public String getClientId() {
        return clientId;
    }

    /**应用的APP KEY*/
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**授权类型*/
    public String getResponeType() {
        return responseType;
    }

    /**应用申请到的APP SECRET*/
    public String getClientSecret() {
        return clientSecret;
    }

    /**应用申请到的APP SECRET*/
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    /**授权类型*/
    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    /** 显示授权页的类型，默认授权页为page授权页 */
    public String getDisplay() {
        return display;
    }

    /** 显示授权页的类型，默认授权页为page授权页 */
    public void setDispaly(String display) {
        this.display = display;
    }

    /**授权码*/
    public String getAuthorizeCode() {
        return authorizeCode;
    }

    /**授权码*/
    public void setAuthorizeCode(String authorizeCode) {
        this.authorizeCode = authorizeCode;
    }

    /**确定授权类型，authorization_code或refresh_token*/
    public String getGrantType() {
        return grantType;
    }

    /**确定授权类型，authorization_code或refresh_token*/
    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

}
