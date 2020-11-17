package com.cn.poker.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * @author <>
 */
public class WpStrategyDetailEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private Integer id;
	
	/**
	 * 
	 */
	private Integer userId;
	
	/**
	 * 
	 */
	private Date startDate;
	
	/**
	 * 
	 */
	private Date endDate;
	
	/**
	 * 
	 */
	private Integer poolType;
	
	/**
	 * 
	 */
	private Integer type;
	
	/**
	 * 
	 */
	private Integer gold;
	
	/**
	 * 
	 */
	private String typeNum;
	
	/**
	 * 
	 */
	private Date createDate;
	
	/**
	 * 
	 */
	private Integer strategyId;
	
	/**
	 * 
	 */
	private Integer dayCount;
	

	public WpStrategyDetailEntity() {
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
     * setter for startDate
     * @param startDate
     */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

    /**
     * getter for startDate
     */
	public Date getStartDate() {
		return startDate;
	}
	
    /**
     * setter for endDate
     * @param endDate
     */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

    /**
     * getter for endDate
     */
	public Date getEndDate() {
		return endDate;
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
     * setter for gold
     * @param gold
     */
	public void setGold(Integer gold) {
		this.gold = gold;
	}

    /**
     * getter for gold
     */
	public Integer getGold() {
		return gold;
	}

	public String getTypeNum() {
		return typeNum;
	}

	public void setTypeNum(String typeNum) {
		this.typeNum = typeNum;
	}

	/**
     * setter for createDate
     * @param createDate
     */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

    /**
     * getter for createDate
     */
	public Date getCreateDate() {
		return createDate;
	}
	
    /**
     * setter for strategyId
     * @param strategyId
     */
	public void setStrategyId(Integer strategyId) {
		this.strategyId = strategyId;
	}

    /**
     * getter for strategyId
     */
	public Integer getStrategyId() {
		return strategyId;
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
	
}
