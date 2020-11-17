package com.cn.poker.service.impl;

import java.util.Map;

import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.Query;
import com.cn.poker.common.entity.R;
import com.cn.poker.common.util.CommonUtils;
import com.cn.poker.entity.WpUsersEntity;
import com.cn.poker.manager.WpUsersManager;
import com.cn.poker.service.WpUsersService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author <>
 */
@Service("wpUsersService")
public class WpUsersServiceImpl implements WpUsersService {

	@Autowired
	private WpUsersManager wpUsersManager;

    /**
     * 分页查询
     * @param params
     * @return
     */
	@Override
	public Page<WpUsersEntity> listWpUsers(Map<String, Object> params) {
		Query query = new Query(params);
		Page<WpUsersEntity> page = new Page<>(query);
		wpUsersManager.listWpUsers(page, query);
		return page;
	}

    /**
     * 新增
     * @param wpUsers
     * @return
     */
	@Override
	public R saveWpUsers(WpUsersEntity wpUsers) {
		int count = wpUsersManager.saveWpUsers(wpUsers);
		return CommonUtils.msg(count);
	}

    /**
     * 根据id查询
     * @param id
     * @return
     */
	@Override
	public R getWpUsersById(Long id) {
		WpUsersEntity wpUsers = wpUsersManager.getWpUsersById(id);
		return CommonUtils.msg(wpUsers);
	}

    /**
     * 修改
     * @param wpUsers
     * @return
     */
	@Override
	public R updateWpUsers(WpUsersEntity wpUsers) {
		int count = wpUsersManager.updateWpUsers(wpUsers);
		return CommonUtils.msg(count);
	}

    /**
     * 删除
     * @param id
     * @return
     */
	@Override
	public R batchRemove(Long[] id) {
		int count = wpUsersManager.batchRemove(id);
		return CommonUtils.msg(id, count);
	}

	@Override
	public WpUsersEntity getUserByName(String userName) {
		return wpUsersManager.getUserByName(userName);
	}

}
