package com.cn.poker.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 策略包
 * @author <>
 */
public class WpStrateEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 底池类型:1单次底池，2. 3bet底池，3. 4bet底池
	 */
	private Integer poolType;
	
	/**
	 * 类型:1.单挑， 2.6人桌，3.8人桌
	 */
	private Integer type;
	
	/**
	 * 策略包
	 */
	private String name;
	
	/**
	 * 价格
	 */
	private Integer price;
	
	/**
	 * 天数:-1代表永久
	 */
	private Integer dayCount;
	
	/**
	 * 排序
	 */
	private Integer sort;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 创建时间
	 */
	private Date updateTime;

	private Integer month;//单月购买所需钻石

	private Date startTime;//开始时间
	private Date endTime;//截止时间

	private Integer status;	//是否已购:1是  0.否

	public WpStrateEntity() {
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
     * setter for name
     * @param name
     */
	public void setName(String name) {
		this.name = name;
	}

    /**
     * getter for name
     */
	public String getName() {
		return name;
	}
	
    /**
     * setter for price
     * @param price
     */
	public void setPrice(Integer price) {
		this.price = price;
	}

    /**
     * getter for price
     */
	public Integer getPrice() {
		return price;
	}
	
    /**
     * setter for dayCount
     * @param dayCount
     */
	public void setDayCount(Integer dayCount) {
		this.dayCount = dayCount;
	}

    /**
     * getter for dayCount
     */
	public Integer getDayCount() {
		return dayCount;
	}
	
    /**
     * setter for sort
     * @param sort
     */
	public void setSort(Integer sort) {
		this.sort = sort;
	}

    /**
     * getter for sort
     */
	public Integer getSort() {
		return sort;
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

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getStatus() {
		if(this.startTime!=null && this.endTime != null){
			if(this.startTime.after(this.endTime)){
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

	public void setStatus(Integer status) {
		this.status = 0;
	}
}
