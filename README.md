CIO OAuth2 API Java SDK

======== 版本信息 ========

当前版本： V1.0.0

发布日期： 

文件大小： 

版本支持： 支持JDK7.0及以上版本


======== 使用说明 ========

在您的项目中导入以下依赖包：

commons-beanutils-1.8.0.jar、commons-collections-3.2.1.jar、commons-lang-2.4.jar、commons-logging-1.1.1.jar
ezmorph-1.0.6.jar、httpclient-4.1.3.jar、httpcore-4.1.4.jar、json-lib-2.3.jar、Java_SDK_OAuth2_V1.0.jar（本SDK生成的jar包）

======== OAuth2.0 认证流程 ======== 

初始化

<code>
    
    // 创建认证授权对象存储认证授权相关信息
		
		OAuthV2 oauth = new OAuthV2();
		
		// 设置client_id,即申请获得的api_key
		
		oauth.setClientId("YOUR_API_KEY");
		
		// 设置client_secret,即申请获得的api_secret
		
		oauth.setClientSecret("YOUR_API_SECRET");
		
		// 设置用户授权后的回调地址
		
		oauth.setRedirectUri("YOUR_CALLBACK_URL");
		
		// 设置scope权限值，如果不设置采用默认值
	
		oauth.addScope(Scopes.USER_PROFILE_BASIC_SCOPE);
	
</code>

引导用户到授权页面

<code>
    
    // 为了在callback页面使用认证授权对象，需要将其保存起来，这里将其保存在application对象中
	
		request.getServletContext().setAttribute("oauth", oauth);
		
		// 获取经过编码后的完整认证授权url地址
		
		String authCodeUrl = oauth.getAuthorizationUrl();
		
		//引导用户到认证授权页面
	
		response.sendRedirect(authCodeUrl);
		
</code>

通过你的回调地址获取code

<code>
    
    // 在回调页面取出在application存放的认证授权对象供后续使用
		
		OAuthV2 oauth = (OAuthV2)request.getServletContext().getAttribute("oauth");
    
    // 获取code
    
    String code = howeverYouGetIt();
    
</code>

用code获取access_token

<code>
  
   // 使用code获取access_token
   
   AccessToken accessToken = null;
   
	 accessToken = oauth.tradeAccessTokenWithCode(code);
	 
</code>

======== 获取资源 ========
<code>
  
  // 存储用户基本信息的对象
  
	UserInfo userInfo = null;
	
	// 创建用户服务对象，使用id与access_token获取指定id的用户基本信息或者仅适用access_token获取当前登录用户信息
	
	CioUserService userService = new CioUserService();
	
	// 获取当前登录用户信息
	
	userInfo = userService.getLoggedInUserProfile(accessToken.getAccessToken());
	
	// 获取指定id的用户信息
	
	//userInfo = userService.getUserProfileById(accessToken.getCioUserId(), accessToken.getAccessToken());
	
</code>

注意： 当前sdk版本仅可以获取用户基本信息

======== 其他说明 ========

1. 例子可参看demo文件夹中实例
