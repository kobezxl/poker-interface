package com.cn.poker.dao;

import com.cn.poker.common.dao.BaseMapper;
import com.cn.poker.entity.WpUsersEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author <>
 */
@Mapper
public interface WpUsersMapper extends BaseMapper<WpUsersEntity> {

    WpUsersEntity getUserByName(String userName);
}
