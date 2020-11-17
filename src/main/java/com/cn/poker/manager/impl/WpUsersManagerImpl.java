package com.cn.poker.manager.impl;

import java.util.List;

import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.Query;
import com.cn.poker.dao.WpUsersMapper;
import com.cn.poker.entity.WpUsersEntity;
import com.cn.poker.manager.WpUsersManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 
 * @author <>
 */
@Component("wpUsersManager")
public class WpUsersManagerImpl implements WpUsersManager {

	@Autowired
	private WpUsersMapper wpUsersMapper;

    /**
     * 分页查询
     * @param page
     * @param search
     * @return
     */
	@Override
	public List<WpUsersEntity> listWpUsers(Page<WpUsersEntity> page, Query search) {
		return wpUsersMapper.listForPage(page, search);
	}

    /**
     * 新增
     * @param wpUsers
     * @return
     */
	@Override
	public int saveWpUsers(WpUsersEntity wpUsers) {
		return wpUsersMapper.save(wpUsers);
	}

    /**
     * 根据id查询
     * @param id
     * @return
     */
	@Override
	public WpUsersEntity getWpUsersById(Long id) {
		WpUsersEntity wpUsers = wpUsersMapper.getObjectById(id);
		return wpUsers;
	}

    /**
     * 修改
     * @param wpUsers
     * @return
     */
	@Override
	public int updateWpUsers(WpUsersEntity wpUsers) {
		return wpUsersMapper.update(wpUsers);
	}

    /**
     * 删除
     * @param id
     * @return
     */
	@Override
	public int batchRemove(Long[] id) {
		int count = wpUsersMapper.batchRemove(id);
		return count;
	}

	@Override
	public WpUsersEntity getUserByName(String userName) {
		return wpUsersMapper.getUserByName(userName);
	}

}
