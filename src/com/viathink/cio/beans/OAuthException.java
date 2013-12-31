package com.viathink.cio.beans;

public class OAuthException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6507434718336988889L;
	private int errorCode;
	private String errorMsg;

	  /**
	   * @return the errorCode
	   */
	  public int getErrorCode() {
	    return errorCode;
	  }

	  /**
	   * @param errorCode the errorCode to set
	   */
	  public void setErrorCode(int errorCode) {
	    this.errorCode = errorCode;
	  }

	  /**
	   * @return the errorMsg
	   */
	  public String getErrorMsg() {
	    return errorMsg;
	  }

	  /**
	   * @param errorMsg the errorMsg to set
	   */
	  public void setErrorMsg(String errorMsg) {
	    this.errorMsg = errorMsg;
	  }
	  
	  public OAuthException (int code, String msg) {
	    super(msg);
	    this.errorCode = code;
	    this.errorMsg = msg;
	  }
}
