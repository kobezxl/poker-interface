package com.cn.poker.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 打包购买策略包汇总
 * @author <>
 */
public class WpStratePackSumList implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<WpStratePackSumEntity> list;
	 private int totalGold;

	public int getTotalGold() {
		return totalGold;
	}

	public void setTotalGold(int totalGold) {
		this.totalGold = totalGold;
	}

	public List<WpStratePackSumEntity> getList() {
		return list;
	}

	public void setList(List<WpStratePackSumEntity> list) {
		this.list = list;
	}


}
