package com.cn.poker.service.impl;

import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.Query;
import com.cn.poker.common.entity.R;
import com.cn.poker.common.util.CommonUtils;
import com.cn.poker.entity.WpStrateSingleSumEntity;
import com.cn.poker.manager.WpStrateSingleSumManager;
import com.cn.poker.service.WpStrateSingleSumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * 单个购买策略包汇总
 * @author <>
 */
@Service("wpStrateSingleSumService")
public class WpStrateSingleSumServiceImpl implements WpStrateSingleSumService {

	@Autowired
	private WpStrateSingleSumManager wpStrateSingleSumManager;

    /**
     * 分页查询
     * @param params
     * @return
     */
	@Override
	public Page<WpStrateSingleSumEntity> listWpStrateSingleSum(Map<String, Object> params) {
		Query query = new Query(params);
		Page<WpStrateSingleSumEntity> page = new Page<>(query);
		wpStrateSingleSumManager.listWpStrateSingleSum(page, query);
		return page;
	}

    /**
     * 新增
     * @param wpStrateSingleSum
     * @return
     */
	@Override
	public R saveWpStrateSingleSum(WpStrateSingleSumEntity wpStrateSingleSum) {
		int count = wpStrateSingleSumManager.saveWpStrateSingleSum(wpStrateSingleSum);
		return CommonUtils.msg(count);
	}

    /**
     * 根据id查询
     * @param id
     * @return
     */
	@Override
	public R getWpStrateSingleSumById(Long id) {
		WpStrateSingleSumEntity wpStrateSingleSum = wpStrateSingleSumManager.getWpStrateSingleSumById(id);
		return CommonUtils.msg(wpStrateSingleSum);
	}

    /**
     * 修改
     * @param wpStrateSingleSum
     * @return
     */
	@Override
	public R updateWpStrateSingleSum(WpStrateSingleSumEntity wpStrateSingleSum) {
		int count = wpStrateSingleSumManager.updateWpStrateSingleSum(wpStrateSingleSum);
		return CommonUtils.msg(count);
	}

    /**
     * 删除
     * @param id
     * @return
     */
	@Override
	public R batchRemove(Long[] id) {
		int count = wpStrateSingleSumManager.batchRemove(id);
		return CommonUtils.msg(id, count);
	}

}
