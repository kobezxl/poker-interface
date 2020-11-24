package com.cn.poker.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 打包购买策略包汇总
 * @author <>
 */
public class WpStratePackSumEntity implements Serializable {
	
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

	private int status;   //是否已购:1是  0.否


	private int month;
	private int year;
	private int forver;

	private int daySum;

	private Date currentTime;

	public Date getCurrentTime() {
		return new Date();
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	public int getDaySum() {
		return daySum;
	}

	public void setDaySum(int daySum) {
		this.daySum = daySum;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getForver() {
		return forver;
	}

	public void setForver(int forver) {
		this.forver = forver;
	}

	public WpStratePackSumEntity() {
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
