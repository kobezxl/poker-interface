package com.cn.poker.entity;

import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * 策略包购买
 */
@Data
public class OrderVo {
    @NonNull
    private int userId;         //用户id

    private Integer packageId;      //策略包id
    @NonNull
    private int dayCount;       //天数1.30天   2.一年365天    3.永久

    private int daySum;
    private int type;
    private int poolType;

    private Date startDate;
    private Date endDate;
    public OrderVo(int userId, Integer packageId, int dayCount) {
        this.userId = userId;
        this.packageId = packageId;
        this.dayCount = dayCount;
    }

    public OrderVo() {
    }
}
