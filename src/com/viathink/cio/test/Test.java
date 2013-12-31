package com.viathink.cio.test;

import com.viathink.cio.beans.Scopes;
import com.viathink.cio.oauthv2.OAuthV2;

public class Test {

	public static void main(String[] args) {
		
		OAuthV2 oauth = new OAuthV2();
		
		oauth.setClientId("93ae934804664794c89ebec1743a62b7");
		oauth.setClientSecret("08bf2f5d9bffaf64e28faab735b0da61");
		oauth.setRedirectUri("http://www.chinesecio.com/callback.php");
		oauth.addScope(Scopes.USER_PROFILE_BASIC_SCOPE);
		
		String authCodeUrl = oauth.getAuthorizationUrl();
		
		try {
			oauth.tradeAccessTokenWithCode("2b0fcaa484873ff8a4b6f40e007724b1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	

}
