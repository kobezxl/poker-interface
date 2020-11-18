package com.cn.poker.manager.impl;

import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.Query;
import com.cn.poker.dao.WpStrateSingleSumMapper;
import com.cn.poker.entity.WpStrateSingleSumEntity;
import com.cn.poker.manager.WpStrateSingleSumManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 单个购买策略包汇总
 * @author <>
 */
@Component("wpStrateSingleSumManager")
public class WpStrateSingleSumManagerImpl implements WpStrateSingleSumManager {

	@Autowired
	private WpStrateSingleSumMapper wpStrateSingleSumMapper;

    /**
     * 分页查询
     * @param page
     * @param search
     * @return
     */
	@Override
	public List<WpStrateSingleSumEntity> listWpStrateSingleSum(Page<WpStrateSingleSumEntity> page, Query search) {
		return wpStrateSingleSumMapper.listForPage(page, search);
	}

    /**
     * 新增
     * @param wpStrateSingleSum
     * @return
     */
	@Override
	public int saveWpStrateSingleSum(WpStrateSingleSumEntity wpStrateSingleSum) {
		return wpStrateSingleSumMapper.save(wpStrateSingleSum);
	}

    /**
     * 根据id查询
     * @param id
     * @return
     */
	@Override
	public WpStrateSingleSumEntity getWpStrateSingleSumById(Long id) {
		WpStrateSingleSumEntity wpStrateSingleSum = wpStrateSingleSumMapper.getObjectById(id);
		return wpStrateSingleSum;
	}

    /**
     * 修改
     * @param wpStrateSingleSum
     * @return
     */
	@Override
	public int updateWpStrateSingleSum(WpStrateSingleSumEntity wpStrateSingleSum) {
		return wpStrateSingleSumMapper.update(wpStrateSingleSum);
	}

    /**
     * 删除
     * @param id
     * @return
     */
	@Override
	public int batchRemove(Long[] id) {
		int count = wpStrateSingleSumMapper.batchRemove(id);
		return count;
	}
	
}
