package com.cn.poker.manager;

import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.Query;
import com.cn.poker.entity.WpStrateSingleSumEntity;

import java.util.List;


/**
 * 单个购买策略包汇总
 * @author <>
 */
public interface WpStrateSingleSumManager {

    /**
     * 分页查询
     * @param page
     * @param search
     * @return
     */
	List<WpStrateSingleSumEntity> listWpStrateSingleSum(Page<WpStrateSingleSumEntity> page, Query search);

    /**
     * 新增
     * @param wpStrateSingleSum
     * @return
     */
	int saveWpStrateSingleSum(WpStrateSingleSumEntity wpStrateSingleSum);

    /**
     * 根据id查询
     * @param id
     * @return
     */
	WpStrateSingleSumEntity getWpStrateSingleSumById(Long id);

    /**
     * 修改
     * @param wpStrateSingleSum
     * @return
     */
	int updateWpStrateSingleSum(WpStrateSingleSumEntity wpStrateSingleSum);

    /**
     * 删除
     * @param id
     * @return
     */
	int batchRemove(Long[] id);
	
}
