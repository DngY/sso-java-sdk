package com.viathink.cio.constants;

public class OAuthConstants {

    public static final String OAUTH_VERSION_2 = "2.0";
    
    public static final String OAUTH_V2_RESPONSE_TYPE_CODE = "code";
    public static final String OAUTH_V2_RESPONSE_TYPE_TOKEN = "token";
    
    public static final String OAUTH_V2_GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
    public static final String OAUTH_V2_GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
    
    public static final String OAUTH_V2_AUTHORIZE_URL = "http://sso.chinesecio.com/sso-v1/auth2/auth";
    public static final String OAUTH_V2_GET_ACCESS_TOKEN_URL = "http://sso.chinesecio.com/sso-v1/auth2/token";
    
    public static final String OAUTH_V2_GET_USER_PROFILE_BASIC = "http://sso.chinesecio.com/sso-v1/service/profile";
    
    
    public static final String OAUTH_V2_DISPLAY_PAGE = "page";
    public static final String OAUTH_V2_DISPLAY_MOBILE = "mobile";
    public static final String OAUTH_V2_DISPLAY_POPUP = "popup";

    private OAuthConstants(){}
}
