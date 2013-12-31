package com.viathink.cio.test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.viathink.cio.beans.AccessToken;
import com.viathink.cio.beans.UserInfo;
import com.viathink.cio.oauthv2.OAuthV2;
import com.viathink.cio.service.CioUserService;

public class CallbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public CallbackServlet() {
        super();
      
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		OAuthV2 oauth = (OAuthV2)request.getServletContext().getAttribute("oauth");
		String code = request.getParameter("code");
		System.out.println("code : " + code);
		AccessToken accessToken = null;
		UserInfo userInfo = null;
		try {
			accessToken = oauth.tradeAccessTokenWithCode(code);
			System.out.println("accessToken : " + accessToken.getAccessToken());
			
			CioUserService userService = new CioUserService();
			//userInfo = userService.getLoggedInUserProfile(accessToken.getAccessToken());
			userInfo = userService.getUserProfileById("1", accessToken.getAccessToken());
			System.out.println("UserInfo : " + userInfo.getUsername());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
