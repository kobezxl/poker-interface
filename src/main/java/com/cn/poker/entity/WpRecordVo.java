package com.cn.poker.entity;

import lombok.Data;

import java.util.Date;

@Data
public class WpRecordVo {
    private int id;
    private int dayCount;
    private Date createDate;
    private String name;
    private int gold;
    private String pakgeType;
    private int userId;

}
