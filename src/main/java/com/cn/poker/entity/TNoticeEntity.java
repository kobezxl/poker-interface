package com.cn.poker.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 公告
 * @author <>
 */
public class TNoticeEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 创建人id
	 */
	private String userId;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 英文-标题
	 */
	private String enTitle;
	
	/**
	 * 英文-内容
	 */
	private String enContent;
	
	/**
	 * 公告类型:1可用，2不可用
	 */
	private Integer state;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date updateTime;
	

	public TNoticeEntity() {
		super();
	}

    /**
     * setter for id
     * @param id
     */
	public void setId(Integer id) {
		this.id = id;
	}

    /**
     * getter for id
     */
	public Integer getId() {
		return id;
	}
	
    /**
     * setter for userId
     * @param userId
     */
	public void setUserId(String userId) {
		this.userId = userId;
	}

    /**
     * getter for userId
     */
	public String getUserId() {
		return userId;
	}
	
    /**
     * setter for title
     * @param title
     */
	public void setTitle(String title) {
		this.title = title;
	}

    /**
     * getter for title
     */
	public String getTitle() {
		return title;
	}
	
    /**
     * setter for content
     * @param content
     */
	public void setContent(String content) {
		this.content = content;
	}

    /**
     * getter for content
     */
	public String getContent() {
		return content;
	}
	
    /**
     * setter for enTitle
     * @param enTitle
     */
	public void setEnTitle(String enTitle) {
		this.enTitle = enTitle;
	}

    /**
     * getter for enTitle
     */
	public String getEnTitle() {
		return enTitle;
	}
	
    /**
     * setter for enContent
     * @param enContent
     */
	public void setEnContent(String enContent) {
		this.enContent = enContent;
	}

    /**
     * getter for enContent
     */
	public String getEnContent() {
		return enContent;
	}
	
    /**
     * setter for state
     * @param state
     */
	public void setState(Integer state) {
		this.state = state;
	}

    /**
     * getter for state
     */
	public Integer getState() {
		return state;
	}
	
    /**
     * setter for createTime
     * @param createTime
     */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

    /**
     * getter for createTime
     */
	public Date getCreateTime() {
		return createTime;
	}
	
    /**
     * setter for updateTime
     * @param updateTime
     */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

    /**
     * getter for updateTime
     */
	public Date getUpdateTime() {
		return updateTime;
	}
	
}
