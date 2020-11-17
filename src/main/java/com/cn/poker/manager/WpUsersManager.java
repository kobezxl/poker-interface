package com.cn.poker.manager;

import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.Query;
import com.cn.poker.entity.WpUsersEntity;

import java.util.List;


/**
 * 
 * @author <>
 */
public interface WpUsersManager {

    /**
     * 分页查询
     * @param page
     * @param search
     * @return
     */
	List<WpUsersEntity> listWpUsers(Page<WpUsersEntity> page, Query search);

    /**
     * 新增
     * @param wpUsers
     * @return
     */
	int saveWpUsers(WpUsersEntity wpUsers);

    /**
     * 根据id查询
     * @param id
     * @return
     */
	WpUsersEntity getWpUsersById(Long id);

    /**
     * 修改
     * @param wpUsers
     * @return
     */
	int updateWpUsers(WpUsersEntity wpUsers);

    /**
     * 删除
     * @param id
     * @return
     */
	int batchRemove(Long[] id);

    WpUsersEntity getUserByName(String userName);
}
