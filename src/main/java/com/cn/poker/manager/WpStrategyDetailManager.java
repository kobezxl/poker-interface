package com.cn.poker.manager;

import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.Query;
import com.cn.poker.entity.WpStrategyDetailEntity;
import lombok.NonNull;

import java.util.List;

/**
 * 
 * @author <>
 */
public interface WpStrategyDetailManager {

    /**
     * 分页查询
     * @param page
     * @param search
     * @return
     */
	List<WpStrategyDetailEntity> listWpStrategyDetail(Page<WpStrategyDetailEntity> page, Query search);

    /**
     * 新增
     * @param wpStrategyDetail
     * @return
     */
	int saveWpStrategyDetail(WpStrategyDetailEntity wpStrategyDetail);

    /**
     * 根据id查询
     * @param id
     * @return
     */
	WpStrategyDetailEntity getWpStrategyDetailById(Long id);

    /**
     * 修改
     * @param wpStrategyDetail
     * @return
     */
	int updateWpStrategyDetail(WpStrategyDetailEntity wpStrategyDetail);

    /**
     * 删除
     * @param id
     * @return
     */
	int batchRemove(Long[] id);

    WpStrategyDetailEntity selectOne(WpStrategyDetailEntity wpStrategyDetailEntity);

    WpStrategyDetailEntity selectByUserId(String type, @NonNull Integer userId);

    WpStrategyDetailEntity selectByUserIdV1(String type, @NonNull Integer userId);

    WpStrategyDetailEntity selectByUserIdV2(String type, @NonNull Integer userId);
}
