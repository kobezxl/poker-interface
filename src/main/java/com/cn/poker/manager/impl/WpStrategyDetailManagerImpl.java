package com.cn.poker.manager.impl;

import java.util.List;

import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.Query;
import com.cn.poker.dao.WpStrategyDetailMapper;
import com.cn.poker.entity.WpStrategyDetailEntity;
import com.cn.poker.manager.WpStrategyDetailManager;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 
 * @author <>
 */
@Component("wpStrategyDetailManager")
public class WpStrategyDetailManagerImpl implements WpStrategyDetailManager {

	@Autowired
	private WpStrategyDetailMapper wpStrategyDetailMapper;

    /**
     * 分页查询
     * @param page
     * @param search
     * @return
     */
	@Override
	public List<WpStrategyDetailEntity> listWpStrategyDetail(Page<WpStrategyDetailEntity> page, Query search) {
		return wpStrategyDetailMapper.listForPage(page, search);
	}

    /**
     * 新增
     * @param wpStrategyDetail
     * @return
     */
	@Override
	public int saveWpStrategyDetail(WpStrategyDetailEntity wpStrategyDetail) {
		return wpStrategyDetailMapper.save(wpStrategyDetail);
	}

    /**
     * 根据id查询
     * @param id
     * @return
     */
	@Override
	public WpStrategyDetailEntity getWpStrategyDetailById(Long id) {
		WpStrategyDetailEntity wpStrategyDetail = wpStrategyDetailMapper.getObjectById(id);
		return wpStrategyDetail;
	}

    /**
     * 修改
     * @param wpStrategyDetail
     * @return
     */
	@Override
	public int updateWpStrategyDetail(WpStrategyDetailEntity wpStrategyDetail) {
		return wpStrategyDetailMapper.update(wpStrategyDetail);
	}

    /**
     * 删除
     * @param id
     * @return
     */
	@Override
	public int batchRemove(Long[] id) {
		int count = wpStrategyDetailMapper.batchRemove(id);
		return count;
	}

	@Override
	public WpStrategyDetailEntity selectOne(WpStrategyDetailEntity wpStrategyDetailEntity) {
		return wpStrategyDetailMapper.selectOne(wpStrategyDetailEntity);
	}

	@Override
	public WpStrategyDetailEntity selectByUserId(String type, @NonNull Integer userId) {
		return wpStrategyDetailMapper.selectByUserId(type,userId);
	}

	@Override
	public WpStrategyDetailEntity selectByUserIdV1(String type, @NonNull Integer userId) {
		return wpStrategyDetailMapper.selectByUserIdV1(type,userId);
	}
	@Override
	public WpStrategyDetailEntity selectByUserIdV2(String type, @NonNull Integer userId) {
		return wpStrategyDetailMapper.selectByUserIdV2(type,userId);
	}

}
