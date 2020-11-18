package com.cn.poker.dao;

import com.cn.poker.common.dao.BaseMapper;
import com.cn.poker.entity.StrateInfoVo;
import com.cn.poker.entity.WpStratePackSumEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 打包购买策略包汇总
 * @author <>
 */
@Mapper
public interface WpStratePackSumMapper extends BaseMapper<WpStratePackSumEntity> {

    /**
     * 根据userId  和   type 查询用户打包购买汇总
     * @param strateInfoVo
     * @return
     */
    List<WpStratePackSumEntity> getWpStratePackSumByUserId(StrateInfoVo strateInfoVo);

    /**
     * 批量初始化用户打包购买策略包汇总
     * @param list
     */
    void insertBatch(@Param("stratePackSumList") List<WpStratePackSumEntity> list);
}
