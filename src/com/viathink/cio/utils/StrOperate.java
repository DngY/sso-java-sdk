package com.viathink.cio.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.NameValuePair;

import com.viathink.cio.beans.AccessToken;
import com.viathink.cio.beans.OAuthException;
import com.viathink.cio.beans.UserInfo;

public class StrOperate {
	
	private StrOperate(){}
	
	/**
     * 生成queryString
     * @param QueryParamsList
     * @return 不包括？的queryString
     */
    public static String getQueryString(List<NameValuePair> QueryParamsList) {
        StringBuilder queryString = new StringBuilder();
        for(NameValuePair param:QueryParamsList) {
            queryString.append('&');
            queryString.append(param.getName());
            queryString.append('=');
            queryString.append(paramEncode(param.getValue()));
        }
        //去掉第一个&号
        return queryString.toString().substring(1);
    }
    
    /**
     * 对参数进行UTF-8编码，并替换特殊字符
     * 
     * @param paramDecString 待编码的参数字符串
     * @return 完成编码转换的字符串
     */
    public static String paramEncode(String paramDecString) {
        if (!hasValue(paramDecString)) {
            return "";
        }
        try {
            return URLEncoder.encode(paramDecString, "UTF-8").replace("+", "%20")
                    .replace("*", "%2A").replace("%7E", "~")
                    .replace("#", "%23");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    /**
     * 检查字符串是否存在值
     * 
     * @param str 待检验的字符串
     * @return 当 str 不为 null 或 "" 就返回 true
     */
    public static boolean hasValue(String str) {
        return (str != null && !"".equals(str));
    }

    public static AccessToken stringToAccessToken(String responseStr) throws OAuthException {
        if (responseStr == null) {
          throw new OAuthException(1000,"ResponseStr is null");
        }
        JSONObject jObj = StrOperate.toJsonObj(responseStr);
        AccessToken token = new AccessToken();
        if (jObj.containsKey("access_token")) {
          String accessToken = jObj.getString("access_token");
          token.setAccessToken(accessToken);
        } else {
          throw new OAuthException(2000,"Get access_token error : " + responseStr);
        }
        if (jObj.containsKey("expires_in")) {
          int expiresIn = jObj.getInt("expires_in");
          token.setExpiresIn(expiresIn);
        } else {
          throw new OAuthException(2000,"Get access_token error : " + responseStr);
        }
        if (jObj.containsKey("refresh_token")) {
          String refreshToken = jObj.getString("refresh_token");
          token.setRefreshToken(refreshToken);
        }
        if (jObj.containsKey("profile_user_id")) {
          String cioUserId = jObj.getString("profile_user_id");
          token.setCioUserId(cioUserId);
        }
        return token;
      }
    
    public static UserInfo stringToUserInfo(String responseStr) throws OAuthException {
        if (responseStr == null) {
          throw new OAuthException(2000,"responseStr is null");
        }
        
        JSONObject jObj = StrOperate.toJsonObj(responseStr);
        UserInfo userInfo = new UserInfo();
        if (jObj.containsKey("userId")) {
          String userId = jObj.getString("userId");
          userInfo.setUserId(userId);
        }else {
         throw new OAuthException(2000,"Get user_profile error : " + responseStr);
        } 
        if (jObj.containsKey("username")) {
          String username = jObj.getString("username");
          userInfo.setUsername(username);
        } 
        if (jObj.containsKey("displayName")) {
          String displayName = jObj.getString("displayName");
          userInfo.setDisplayName(displayName);
        }
        if (jObj.containsKey("avatar")) {
          String avatar = jObj.getString("avatar");
          userInfo.setAvatar(avatar);
        }
        
        return userInfo;
      }


    public static JSONObject toJsonObj(String jsonStr) throws OAuthException {
        try {
          JSONObject result = JSONObject.fromObject(jsonStr);
          return result;
        } catch (Exception ex) {
          throw new OAuthException(1001,"Illegal JSON format : " + jsonStr);
        }
      }

}
