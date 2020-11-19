package com.cn.poker.dao;

import com.cn.poker.common.dao.BaseMapper;
import com.cn.poker.entity.WpUsersEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * @author <>
 */
@Mapper
public interface WpUsersMapper extends BaseMapper<WpUsersEntity> {

    WpUsersEntity getUserByName(String userName);

    /**
     * 获取所有没有初始化的userId
     * @return
     */
    List<Long> selectUser();


    /**
     * 批量更新用户初始化状态
     * @param list
     */
    void batchUpdateUser(List<Long> list);
}
