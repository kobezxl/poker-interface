package com.cn.poker.dao;

import com.cn.poker.common.dao.BaseMapper;
import com.cn.poker.entity.WpStrateSingleSumEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 单个购买策略包汇总
 * @author <>
 */
@Mapper
public interface WpStrateSingleSumMapper extends BaseMapper<WpStrateSingleSumEntity> {

    /**
     * 批量插入单个策略包汇总
     * @param list
     */
    void insertBatch(@Param("strateSingleSumList") List<WpStrateSingleSumEntity> list);


    /**
     * 批量插入单个策略包汇总
     * @param list
     */
    void updateBatch(@Param("strateSingleSumList") List<WpStrateSingleSumEntity> list);

    void update1(WpStrateSingleSumEntity wpStrateSingleSumEntity);

    void update2(WpStrateSingleSumEntity wpStrateSingleSumEntity);

    void update3(WpStrateSingleSumEntity wpStrateSingleSumEntity);
}
