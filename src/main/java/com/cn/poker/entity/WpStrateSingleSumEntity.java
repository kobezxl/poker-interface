package com.cn.poker.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 单个购买策略包汇总
 * @author <>
 */
public class WpStrateSingleSumEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 用户id
	 */
	private Integer userId;
	
	/**
	 * 策略包id
	 */
	private Integer packageId;
	
	/**
	 * 类型:1.单挑， 2.6人桌，3.8人桌
	 */
	private Integer type;
	
	/**
	 * 底池类型:1单次底池，2. 3bet底池，3. 4bet底池
	 */
	private Integer poolType;
	
	/**
	 * 开始时间
	 */
	private Date startTime;
	
	/**
	 * 结束时间
	 */
	private Date endTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
	

	public WpStrateSingleSumEntity() {
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
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

    /**
     * getter for userId
     */
	public Integer getUserId() {
		return userId;
	}
	
    /**
     * setter for packageId
     * @param packageId
     */
	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}

    /**
     * getter for packageId
     */
	public Integer getPackageId() {
		return packageId;
	}
	
    /**
     * setter for type
     * @param type
     */
	public void setType(Integer type) {
		this.type = type;
	}

    /**
     * getter for type
     */
	public Integer getType() {
		return type;
	}
	
    /**
     * setter for poolType
     * @param poolType
     */
	public void setPoolType(Integer poolType) {
		this.poolType = poolType;
	}

    /**
     * getter for poolType
     */
	public Integer getPoolType() {
		return poolType;
	}
	
    /**
     * setter for startTime
     * @param startTime
     */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

    /**
     * getter for startTime
     */
	public Date getStartTime() {
		return startTime;
	}
	
    /**
     * setter for endTime
     * @param endTime
     */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

    /**
     * getter for endTime
     */
	public Date getEndTime() {
		return endTime;
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
