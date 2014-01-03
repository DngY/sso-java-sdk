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
		
		// �ж��ڻ�ȡcode�������Ƿ�����������ӡ������Ϣ��Ȼ�󷵻�
		String error = request.getParameter("error");
		if(null != error) {
			BufferedReader bf = new BufferedReader(request.getReader());
			String s = null;
			while ((s=bf.readLine()) != null) 
				System.out.println(s);
			return;
		}
		
		// ȡ����application��ŵ���֤��Ȩ����
		OAuthV2 oauth = (OAuthV2)request.getServletContext().getAttribute("oauth");
		// ȡ����ȡ��code
		String code = request.getParameter("code");
		System.out.println("code : " + code);
		// �洢AccessToken�Ķ���
		AccessToken accessToken = null;
		// �洢�û�������Ϣ�Ķ���
		UserInfo userInfo = null;
		try {
			// ʹ��code��ȡaccess_token
			accessToken = oauth.tradeAccessTokenWithCode(code);
			System.out.println("accessToken : " + accessToken.getAccessToken());
			
			// �����û��������ʹ��id��access_token��ȡָ��id���û�������Ϣ���߽�����access_token��ȡ��ǰ��¼�û���Ϣ
			CioUserService userService = new CioUserService();
			
			// ��ȡ��ǰ��¼�û���Ϣ
			userInfo = userService.getLoggedInUserProfile(accessToken.getAccessToken());
			
			// ��ȡָ��id���û���Ϣ
			//userInfo = userService.getUserProfileById(accessToken.getCioUserId(), accessToken.getAccessToken());
			System.out.println("UserInfo : " + userInfo.getUsername());
			
			// ����ȡ�����û���Ϣ����session�У�Ȼ��ͻ��ض����û���Ϣ��ʾҳ�档����ͻ���Ҫ�����Լ���ҵ��������д���
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
