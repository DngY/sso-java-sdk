package com.viathink.cio.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.viathink.cio.beans.OAuthException;
import com.viathink.cio.beans.UserInfo;
import com.viathink.cio.constants.OAuthConstants;
import com.viathink.cio.utils.StrOperate;

public class CioUserService extends CioService{
	
	private static Log logger = LogFactory.getLog(CioService.class);
	
	public UserInfo getUserProfileById(String id, String accessToken) throws OAuthException, Exception {
		if(null == id || "".equals(id)) {
			logger.warn("Id should not be null or empty");
			throw new OAuthException(3001,"Id should not be null or empty");
		}
		if(null == accessToken || "".equals(accessToken) ) {
			logger.warn("AccessToken should not be null or empty");
			throw new OAuthException(3002,"AccessToken should not be null or empty");
		}
		setAccessToken(accessToken);
		String url = OAuthConstants.OAUTH_V2_GET_USER_PROFILE_BASIC + "/" + id;
		logger.info("Get user profile url : " + url);
		
		String result = client.httpRequest(url, accessToken);
		logger.info("User profile info : " + result);
		return StrOperate.stringToUserInfo(result);
		
	}
	
	public UserInfo getLoggedInUserProfile(String accessToken) throws OAuthException, Exception {
		
		if(null == accessToken || "".equals(accessToken) ) {
			logger.warn("AccessToken should not be null or empty");
			throw new OAuthException(3002,"AccessToken should not be null or empty");
		}
		setAccessToken(accessToken);
		String url = OAuthConstants.OAUTH_V2_GET_USER_PROFILE_BASIC + "/" + StrOperate.paramEncode("~me");
		logger.info("Get user profile url : " + url);
		
		String result = client.httpRequest(url, accessToken);
		logger.info("user profile info : " + result);
		return StrOperate.stringToUserInfo(result);
		
	}
}
