package com.cn.poker.service;

import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.R;
import com.cn.poker.entity.WpUsersEntity;
import lombok.NonNull;

import java.util.Map;

/**
 * 
 * @author <>
 */
public interface WpUsersService {

    /**
     * 分页查询
     * @param params
     * @return
     */
	Page<WpUsersEntity> listWpUsers(Map<String, Object> params);

    /**
     * 新增
     * @param wpUsers
     * @return
     */
	R saveWpUsers(WpUsersEntity wpUsers);

    /**
     * 根据id查询
     * @param id
     * @return
     */
	R getWpUsersById(Long id);

    /**
     * 修改
     * @param wpUsers
     * @return
     */
	R updateWpUsers(WpUsersEntity wpUsers);

    /**
     * 删除
     * @param id
     * @return
     */
	R batchRemove(Long[] id);

    WpUsersEntity getUserByName( String userName);
}
