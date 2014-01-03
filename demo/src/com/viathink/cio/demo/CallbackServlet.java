package com.viathink.cio.demo;

import java.io.BufferedReader;
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
		
		// 判断在获取code过程中是否出错，若出错打印错误信息，然后返回
		String error = request.getParameter("error");
		if(null != error) {
			BufferedReader bf = new BufferedReader(request.getReader());
			String s = null;
			while ((s=bf.readLine()) != null) 
				System.out.println(s);
			return;
		}
		
		// 取出在application存放的认证授权对象
		OAuthV2 oauth = (OAuthV2)request.getServletContext().getAttribute("oauth");
		// 取出获取的code
		String code = request.getParameter("code");
		System.out.println("code : " + code);
		// 存储AccessToken的对象
		AccessToken accessToken = null;
		// 存储用户基本信息的对象
		UserInfo userInfo = null;
		try {
			// 使用code获取access_token
			accessToken = oauth.tradeAccessTokenWithCode(code);
			System.out.println("accessToken : " + accessToken.getAccessToken());
			
			// 创建用户服务对象，使用id与access_token获取指定id的用户基本信息或者仅适用access_token获取当前登录用户信息
			CioUserService userService = new CioUserService();
			
			// 获取当前登录用户信息
			userInfo = userService.getLoggedInUserProfile(accessToken.getAccessToken());
			
			// 获取指定id的用户信息
			//userInfo = userService.getUserProfileById(accessToken.getCioUserId(), accessToken.getAccessToken());
			System.out.println("UserInfo : " + userInfo.getUsername());
			
			// 将获取到的用户信息放在session中，然后客户重定向用户信息显示页面。这里客户需要根据自己的业务需求进行处理
			request.getSession().setAttribute("userInfo", userInfo);
			response.sendRedirect("profile.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
