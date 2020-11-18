package com.cn.poker.manager;

import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.Query;
import com.cn.poker.entity.WpStratePackSumEntity;

import java.util.List;


/**
 * 打包购买策略包汇总
 * @author <>
 */
public interface WpStratePackSumManager {

    /**
     * 分页查询
     * @param page
     * @param search
     * @return
     */
	List<WpStratePackSumEntity> listWpStratePackSum(Page<WpStratePackSumEntity> page, Query search);

    /**
     * 新增
     * @param wpStratePackSum
     * @return
     */
	int saveWpStratePackSum(WpStratePackSumEntity wpStratePackSum);

    /**
     * 根据id查询
     * @param id
     * @return
     */
	WpStratePackSumEntity getWpStratePackSumById(Long id);

    /**
     * 修改
     * @param wpStratePackSum
     * @return
     */
	int updateWpStratePackSum(WpStratePackSumEntity wpStratePackSum);

    /**
     * 删除
     * @param id
     * @return
     */
	int batchRemove(Long[] id);
	
}
