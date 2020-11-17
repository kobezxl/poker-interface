package com.cn.poker.entity;

import lombok.Data;

import java.util.List;
@Data
public class WpStragePriceList {
    private List<WpStragePrice> list;
    private int totalGold;
}
