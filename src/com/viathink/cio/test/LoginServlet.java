package com.viathink.cio.test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.viathink.cio.beans.Scopes;
import com.viathink.cio.oauthv2.OAuthV2;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public LoginServlet() {
        super();
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		OAuthV2 oauth = new OAuthV2();
		
		oauth.setClientId("d33a7411ed434e72ac6e632a6aaf03d3");
		oauth.setClientSecret("c38c9778cd904a7baf132b9a039e439a");
		oauth.setRedirectUri("http://172.16.5.78:8080/Java_SDK_V1.0/callback");
		oauth.addScope(Scopes.USER_PROFILE_BASIC_SCOPE);
		//oauth.addScope(Scopes.USER_PROFILE_BASIC_SCOPE);
		request.getServletContext().setAttribute("oauth", oauth);
		String authCodeUrl = oauth.getAuthorizationUrl();
		
		System.out.println(authCodeUrl);
		
		response.sendRedirect(authCodeUrl);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
