package com.cn.poker.service;

import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.R;
import com.cn.poker.entity.WpStratePackSumEntity;

import java.util.Map;


/**
 * 打包购买策略包汇总
 * @author <>
 */
public interface WpStratePackSumService {

    /**
     * 分页查询
     * @param params
     * @return
     */
	Page<WpStratePackSumEntity> listWpStratePackSum(Map<String, Object> params);

    /**
     * 新增
     * @param wpStratePackSum
     * @return
     */
	R saveWpStratePackSum(WpStratePackSumEntity wpStratePackSum);

    /**
     * 根据id查询
     * @param id
     * @return
     */
	R getWpStratePackSumById(Long id);

    /**
     * 修改
     * @param wpStratePackSum
     * @return
     */
	R updateWpStratePackSum(WpStratePackSumEntity wpStratePackSum);

    /**
     * 删除
     * @param id
     * @return
     */
	R batchRemove(Long[] id);
	
}
