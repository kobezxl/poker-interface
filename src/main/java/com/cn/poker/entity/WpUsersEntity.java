package com.cn.poker.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * @author <>
 */
public class WpUsersEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private Long id;
	
	/**
	 * 
	 */
	private String userLogin;
	
	/**
	 * 
	 */
	private String userPass;
	
	/**
	 * 
	 */
	private String userNicename;
	
	/**
	 * 
	 */
	private String userEmail;
	
	/**
	 * 
	 */
	private String userUrl;
	
	/**
	 * 
	 */
	private Date userRegistered;
	
	/**
	 * 
	 */
	private String userActivationKey;
	
	/**
	 * 
	 */
	private Integer userStatus;
	
	/**
	 * 
	 */
	private String displayName;
	
	/**
	 * 
	 */
	private Integer fatherId;
	
	/**
	 * 
	 */
	private String regIp;
	

	public WpUsersEntity() {
		super();
	}

    /**
     * setter for id
     * @param id
     */
	public void setId(Long id) {
		this.id = id;
	}

    /**
     * getter for id
     */
	public Long getId() {
		return id;
	}
	
    /**
     * setter for userLogin
     * @param userLogin
     */
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

    /**
     * getter for userLogin
     */
	public String getUserLogin() {
		return userLogin;
	}
	
    /**
     * setter for userPass
     * @param userPass
     */
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

    /**
     * getter for userPass
     */
	public String getUserPass() {
		return userPass;
	}
	
    /**
     * setter for userNicename
     * @param userNicename
     */
	public void setUserNicename(String userNicename) {
		this.userNicename = userNicename;
	}

    /**
     * getter for userNicename
     */
	public String getUserNicename() {
		return userNicename;
	}
	
    /**
     * setter for userEmail
     * @param userEmail
     */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

    /**
     * getter for userEmail
     */
	public String getUserEmail() {
		return userEmail;
	}
	
    /**
     * setter for userUrl
     * @param userUrl
     */
	public void setUserUrl(String userUrl) {
		this.userUrl = userUrl;
	}

    /**
     * getter for userUrl
     */
	public String getUserUrl() {
		return userUrl;
	}
	
    /**
     * setter for userRegistered
     * @param userRegistered
     */
	public void setUserRegistered(Date userRegistered) {
		this.userRegistered = userRegistered;
	}

    /**
     * getter for userRegistered
     */
	public Date getUserRegistered() {
		return userRegistered;
	}
	
    /**
     * setter for userActivationKey
     * @param userActivationKey
     */
	public void setUserActivationKey(String userActivationKey) {
		this.userActivationKey = userActivationKey;
	}

    /**
     * getter for userActivationKey
     */
	public String getUserActivationKey() {
		return userActivationKey;
	}
	
    /**
     * setter for userStatus
     * @param userStatus
     */
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

    /**
     * getter for userStatus
     */
	public Integer getUserStatus() {
		return userStatus;
	}
	
    /**
     * setter for displayName
     * @param displayName
     */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

    /**
     * getter for displayName
     */
	public String getDisplayName() {
		return displayName;
	}
	
    /**
     * setter for fatherId
     * @param fatherId
     */
	public void setFatherId(Integer fatherId) {
		this.fatherId = fatherId;
	}

    /**
     * getter for fatherId
     */
	public Integer getFatherId() {
		return fatherId;
	}
	
    /**
     * setter for regIp
     * @param regIp
     */
	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

    /**
     * getter for regIp
     */
	public String getRegIp() {
		return regIp;
	}
	
}
