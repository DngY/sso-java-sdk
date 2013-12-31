package com.viathink.cio.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



/**
 * OAuth鉴权信息通用部分
 */
public class OAuth implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8122500982190482609L;
	protected String oauthVersion;
	protected List<Scopes> scopes = new ArrayList<Scopes>();
	
	
	public OAuth(String oauthVersion) {
		this.oauthVersion = oauthVersion;
	}

	public OAuth addScope(Scopes scope) {
		scopes.add(scope);
		return this;
	}
   
}
