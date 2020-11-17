package com.cn.poker.entity;

import lombok.Data;

import java.util.Date;

@Data
public class WpIceInfo {
    private int iceId;
    private Double iceHaveMoney;
    private int iceUserId;
    private Double iceGetMoney;
    private int userType;
    private Date endTime;
    private String nickName;

    public WpIceInfo(Double iceHaveMoney, int iceUserId, Double iceGetMoney) {
        this.iceHaveMoney = iceHaveMoney;
        this.iceUserId = iceUserId;
        this.iceGetMoney = iceGetMoney;
    }

    public WpIceInfo() {
    }
}
