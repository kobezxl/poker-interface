package com.cn.poker.manager.impl;

import java.util.List;

import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.Query;
import com.cn.poker.dao.WpStratePackSumMapper;
import com.cn.poker.entity.StrateInfoVo;
import com.cn.poker.entity.WpStratePackSumEntity;
import com.cn.poker.manager.WpStratePackSumManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 打包购买策略包汇总
 * @author <>
 */
@Component("wpStratePackSumManager")
public class WpStratePackSumManagerImpl implements WpStratePackSumManager {

	@Autowired
	private WpStratePackSumMapper wpStratePackSumMapper;

    /**
     * 分页查询
     * @param page
     * @param search
     * @return
     */
	@Override
	public List<WpStratePackSumEntity> listWpStratePackSum(Page<WpStratePackSumEntity> page, Query search) {
		return wpStratePackSumMapper.listForPage(page, search);
	}

    /**
     * 新增
     * @param wpStratePackSum
     * @return
     */
	@Override
	public int saveWpStratePackSum(WpStratePackSumEntity wpStratePackSum) {
		return wpStratePackSumMapper.save(wpStratePackSum);
	}

    /**
     * 根据id查询
     * @param id
     * @return
     */
	@Override
	public WpStratePackSumEntity getWpStratePackSumById(Long id) {
		WpStratePackSumEntity wpStratePackSum = wpStratePackSumMapper.getObjectById(id);
		return wpStratePackSum;
	}

    /**
     * 修改
     * @param wpStratePackSum
     * @return
     */
	@Override
	public int updateWpStratePackSum(WpStratePackSumEntity wpStratePackSum) {
		return wpStratePackSumMapper.update(wpStratePackSum);
	}

    /**
     * 删除
     * @param id
     * @return
     */
	@Override
	public int batchRemove(Long[] id) {
		int count = wpStratePackSumMapper.batchRemove(id);
		return count;
	}

	/**
	 *  根据userId  和   type 查询用户打包购买汇总
	 * @param strateInfoVo
	 * @return
	 */
	@Override
	public List<WpStratePackSumEntity> getWpStratePackSumByUserId(StrateInfoVo strateInfoVo) {
		return wpStratePackSumMapper.getWpStratePackSumByUserId(strateInfoVo);
	}

	/**
	 * 批量初始化用户打包购买策略包汇总
	 * @param list
	 */
	@Override
	public void insertBatch(List<WpStratePackSumEntity> list) {
		wpStratePackSumMapper.insertBatch(list);
	}

}
