package com.cn.poker.service.impl;

import java.util.Map;

import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.Query;
import com.cn.poker.common.entity.R;
import com.cn.poker.common.util.CommonUtils;
import com.cn.poker.entity.WpStratePackSumEntity;
import com.cn.poker.manager.WpStratePackSumManager;
import com.cn.poker.service.WpStratePackSumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 打包购买策略包汇总
 * @author <>
 */
@Service("wpStratePackSumService")
public class WpStratePackSumServiceImpl implements WpStratePackSumService {

	@Autowired
	private WpStratePackSumManager wpStratePackSumManager;

    /**
     * 分页查询
     * @param params
     * @return
     */
	@Override
	public Page<WpStratePackSumEntity> listWpStratePackSum(Map<String, Object> params) {
		Query query = new Query(params);
		Page<WpStratePackSumEntity> page = new Page<>(query);
		wpStratePackSumManager.listWpStratePackSum(page, query);
		return page;
	}

    /**
     * 新增
     * @param wpStratePackSum
     * @return
     */
	@Override
	public R saveWpStratePackSum(WpStratePackSumEntity wpStratePackSum) {
		int count = wpStratePackSumManager.saveWpStratePackSum(wpStratePackSum);
		return CommonUtils.msg(count);
	}

    /**
     * 根据id查询
     * @param id
     * @return
     */
	@Override
	public R getWpStratePackSumById(Long id) {
		WpStratePackSumEntity wpStratePackSum = wpStratePackSumManager.getWpStratePackSumById(id);
		return CommonUtils.msg(wpStratePackSum);
	}

    /**
     * 修改
     * @param wpStratePackSum
     * @return
     */
	@Override
	public R updateWpStratePackSum(WpStratePackSumEntity wpStratePackSum) {
		int count = wpStratePackSumManager.updateWpStratePackSum(wpStratePackSum);
		return CommonUtils.msg(count);
	}

    /**
     * 删除
     * @param id
     * @return
     */
	@Override
	public R batchRemove(Long[] id) {
		int count = wpStratePackSumManager.batchRemove(id);
		return CommonUtils.msg(id, count);
	}

}
