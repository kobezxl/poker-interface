package com.cn.poker.service;

import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.R;
import com.cn.poker.entity.WpStrateSingleSumEntity;

import java.util.Map;


/**
 * 单个购买策略包汇总
 * @author <>
 */
public interface WpStrateSingleSumService {

    /**
     * 分页查询
     * @param params
     * @return
     */
	Page<WpStrateSingleSumEntity> listWpStrateSingleSum(Map<String, Object> params);

    /**
     * 新增
     * @param wpStrateSingleSum
     * @return
     */
	R saveWpStrateSingleSum(WpStrateSingleSumEntity wpStrateSingleSum);

    /**
     * 根据id查询
     * @param id
     * @return
     */
	R getWpStrateSingleSumById(Long id);

    /**
     * 修改
     * @param wpStrateSingleSum
     * @return
     */
	R updateWpStrateSingleSum(WpStrateSingleSumEntity wpStrateSingleSum);

    /**
     * 删除
     * @param id
     * @return
     */
	R batchRemove(Long[] id);
	
}
