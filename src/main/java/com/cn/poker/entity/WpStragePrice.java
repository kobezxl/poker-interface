package com.cn.poker.entity;

import lombok.Data;

import java.util.Date;

@Data
public class WpStragePrice {
    private int id;
    private int month;
    private int year;
    private int forver;
    private String type;
    private int status;  //是否已购买: 1. 是   0. 否
    private Date startDate;  //开始时间
    private Date endDate;       //截止时间
//    private int totalGold;
}
