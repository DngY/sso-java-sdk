package com.viathink.cio.service;

import com.viathink.cio.utils.HttpManager;

public class CioService {

	  protected HttpManager client = null;
	  protected String accessToken = null;
	  
	  protected CioService () {
	    this.client = new HttpManager();
	  }

	  public String getAccessToken() {
		return accessToken;
	  }

	  public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	  }
	  
}
