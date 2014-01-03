package com.viathink.cio.demo;

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
		
		// 创建认证授权对象存储认证授权相关信息
		OAuthV2 oauth = new OAuthV2();
		// 设置client_id,即申请获得的api_key
		oauth.setClientId("440fda50cf8b4e6184f3f0ac231ca821");
		// 设置client_secret,即申请获得的api_secret
		oauth.setClientSecret("315a1efa82104ba29be365b6722877d5");
		// 设置用户授权后的回调地址
		oauth.setRedirectUri("http://172.16.5.78:8082/Java_SDK_OAuth2_V1.0_Demo/callback");
		// 设置scope权限值，如果不设置采用默认值
		oauth.addScope(Scopes.USER_PROFILE_BASIC_SCOPE);
		
		// 为了在callback页面使用认证授权对象，需要将其保存起来，这里将其保存在application对象中
		request.getServletContext().setAttribute("oauth", oauth);
		// 获取经过编码后的完整认证授权url地址
		String authCodeUrl = oauth.getAuthorizationUrl();
		
		System.out.println(authCodeUrl);
		
		//引导用户到认证授权页面
		response.sendRedirect(authCodeUrl);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
