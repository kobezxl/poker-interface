package com.cn.poker.service;

import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.R;
import com.cn.poker.entity.WpStrategyDetailEntity;

import java.util.Map;

/**
 * 
 * @author <>
 */
public interface WpStrategyDetailService {

    /**
     * 分页查询
     * @param params
     * @return
     */
	Page<WpStrategyDetailEntity> listWpStrategyDetail(Map<String, Object> params);

    /**
     * 新增
     * @param wpStrategyDetail
     * @return
     */
	R saveWpStrategyDetail(WpStrategyDetailEntity wpStrategyDetail);

    /**
     * 根据id查询
     * @param id
     * @return
     */
	R getWpStrategyDetailById(Long id);

    /**
     * 修改
     * @param wpStrategyDetail
     * @return
     */
	R updateWpStrategyDetail(WpStrategyDetailEntity wpStrategyDetail);

    /**
     * 删除
     * @param id
     * @return
     */
	R batchRemove(Long[] id);
	
}
