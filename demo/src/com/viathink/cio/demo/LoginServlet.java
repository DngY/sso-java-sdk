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
		
		// ������֤��Ȩ����洢��֤��Ȩ�����Ϣ
		OAuthV2 oauth = new OAuthV2();
		// ����client_id,�������õ�api_key
		oauth.setClientId("440fda50cf8b4e6184f3f0ac231ca821");
		// ����client_secret,�������õ�api_secret
		oauth.setClientSecret("315a1efa82104ba29be365b6722877d5");
		// �����û���Ȩ��Ļص���ַ
		oauth.setRedirectUri("http://172.16.5.78:8082/Java_SDK_OAuth2_V1.0_Demo/callback");
		// ����scopeȨ��ֵ����������ò���Ĭ��ֵ
		oauth.addScope(Scopes.USER_PROFILE_BASIC_SCOPE);
		
		// Ϊ����callbackҳ��ʹ����֤��Ȩ������Ҫ���䱣�����������ｫ�䱣����application������
		request.getServletContext().setAttribute("oauth", oauth);
		// ��ȡ����������������֤��Ȩurl��ַ
		String authCodeUrl = oauth.getAuthorizationUrl();
		
		System.out.println(authCodeUrl);
		
		//�����û�����֤��Ȩҳ��
		response.sendRedirect(authCodeUrl);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
