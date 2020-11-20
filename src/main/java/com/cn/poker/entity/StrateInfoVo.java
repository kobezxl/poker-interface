package com.cn.poker.entity;

import lombok.Data;
import lombok.NonNull;

/**
 * 购买策略包详情页面
 */
@Data
public class StrateInfoVo {
    private Integer id;
    @NonNull
    private Integer userId;
    private  Object type;
    private Integer poolType;
    private Double gold;
    private int month;
    private int year;
    private int forver;

    public StrateInfoVo(Integer id, @NonNull Integer userId, Object type, Integer poolType, Double gold, int month, int year, int forver) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.poolType = poolType;
        this.gold = gold;
        this.month = month;
        this.year = year;
        this.forver = forver;
    }

    public StrateInfoVo(@NonNull Integer userId, Object type, Integer poolType) {
        this.userId = userId;
        this.type = type;
        this.poolType = poolType;
    }

    public StrateInfoVo() {
    }
}
