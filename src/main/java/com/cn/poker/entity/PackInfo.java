package com.cn.poker.entity;

import lombok.Data;

/**
 * 打包购买列表
 */
@Data
public class PackInfo {
    private int type;  //6人桌，8人桌
    private int month;
    private int year;
    private int forver;
    private int status;  //是否购买:1.是   0否
    private int restDay;//剩余天数
}
