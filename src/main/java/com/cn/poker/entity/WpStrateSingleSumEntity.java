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
	
	private int daySum;

	private int status;

	/**
	 * 策略包
	 */
	private String name;

	private Date currentTime;

	public Date getCurrentTime() {
		return new Date();
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDaySum() {
		return daySum;
	}

	public void setDaySum(int daySum) {
		this.daySum = daySum;
	}

	public WpStrateSingleSumEntity() {
		super();
	}

	public WpStrateSingleSumEntity(Integer userId, Integer packageId, Integer type, Integer poolType, Date startTime, Date endTime) {
		this.userId = userId;
		this.packageId = packageId;
		this.type = type;
		this.poolType = poolType;
		this.startTime = startTime;
		this.endTime = endTime;
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

	public int getStatus() {
		if(this.startTime!=null && this.endTime != null){
			if(this.startTime.after(this.endTime)){   //永久
				this.status = 1;
			}else {
				if(this.startTime.before(new Date()) && this.endTime.after(new Date())){
					this.status = 1;
				}else {
					this.status = 0;
				}
			}

		}else {
			this.status = 0;
		}
		if(this.type==1){
			status = 1;
		}
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
