package edu.uw.cirg.truenth.oauth.model;

/**
 * Provides grant types supported by this API and our CS.
 * 
 * @author Victor de Lima Soares
 * @since 0.5 Sep 11, 2015
 * @version 1.0
 *
 */
public enum TrueNTHGrantType{
	CODE("authorization_code");
	
	private String grantType;
	
	private TrueNTHGrantType(String grantType) {
	    this.grantType=grantType;
	}
	
	public String toString(){
	    return grantType;
	}
}