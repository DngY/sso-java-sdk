package com.viathink.cio.beans;

import java.io.Serializable;

public class AccessToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5096467422545085658L;
	
    private String  accessToken = null;
    private Integer expiresIn = null;
    private String  refreshToken = null;
    private String  cioUserId = null;
    
    
    /**
     * @return the accessToken
     */
    public String getAccessToken() {
      return accessToken;
    }

    /**
     * @param accessToken the accessToken to set
     */
    public void setAccessToken(String accessToken) {
      this.accessToken = accessToken;
    }

    /**
     * @return the expiresIn
     */
    public Integer getExpiresIn() {
      return expiresIn;
    }

    /**
     * @param expiresIn the expiresIn to set
     */
    public void setExpiresIn(Integer expiresIn) {
      this.expiresIn = expiresIn;
    }

    /**
     * @return the refreshToken
     */
    public String getRefreshToken() {
      return refreshToken;
    }

    /**
     * @param refreshToken the refreshToken to set
     */
    public void setRefreshToken(String refreshToken) {
      this.refreshToken = refreshToken;
    }
    
    /**
     * @return the cioUserId
     */
    public String getCioUserId() {
      return cioUserId;
    }

    /**
     * @param cioUserId the cioUserId to set
     */
    public void setCioUserId(String cioUserId) {
      this.cioUserId = cioUserId;
    }
    
    public AccessToken() {
        
    }
    
    public AccessToken (String accessToken) {
      this.accessToken = accessToken;
    }

	public AccessToken(String accessToken, Integer expiresIn,
			String refreshToken, String cioUserId) {
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.refreshToken = refreshToken;
		this.cioUserId = cioUserId;
	}
}
