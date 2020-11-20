package com.cn.poker.dao;

import com.cn.poker.common.dao.BaseMapper;
import com.cn.poker.entity.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 策略包
 * @author <>
 */
@Mapper
public interface WpStrateMapper extends BaseMapper<WpStrateEntity> {

    /**
     * 获取策略包明细
     * @param strateInfoVo
     * @return
     */
    StrateInfoVo getWpStrateInfo(StrateInfoVo strateInfoVo);
    StrateInfoVo getWpStrateInfo1(StrateInfoVo strateInfoVo);

    /**
     * 根据用户id获取钻石数量
     * @param userId
     * @return
     */
    WpIceInfo selectGoldByUserId(int userId);

    /**
     * 获取打包明细
     * @param userId
     * @return
     */
    List<PackInfo> packaging(int userId);

    /**
     * 扣除用户钻石
     * @param wpIceInfo1
     */
    void subtract(WpIceInfo wpIceInfo1);

    List<WpStragePrice>  packagType();

    List<WpStrateEntity> getList(WpStrateEntity wpStrateEntity);
}
