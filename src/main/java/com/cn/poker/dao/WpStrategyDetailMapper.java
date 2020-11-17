package com.cn.poker.dao;

import com.cn.poker.common.dao.BaseMapper;
import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.Query;
import com.cn.poker.entity.WpRecordVo;
import com.cn.poker.entity.WpStrategyDetailEntity;
import lombok.NonNull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * @author <>
 */
@Mapper
public interface WpStrategyDetailMapper extends BaseMapper<WpStrategyDetailEntity> {

    /**
     * 获取策略包购买记录---分页
     *
     * @param page
     * @param search
     * @return
     */

    List<WpRecordVo> listRecordForPage(Page<WpRecordVo> page, Query query);

    WpStrategyDetailEntity selectOne(WpStrategyDetailEntity wpStrategyDetailEntity);

    WpStrategyDetailEntity selectByUserId(@Param("type") String type, @Param("userId") Integer userId);
}
