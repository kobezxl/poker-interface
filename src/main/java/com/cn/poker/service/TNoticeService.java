package com.cn.poker.service;


import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.R;
import com.cn.poker.entity.TNoticeEntity;

import java.util.Map;

/**
 * 公告
 * @author <>
 */
public interface TNoticeService {

    /**
     * 分页查询
     * @param params
     * @return
     */
	Page<TNoticeEntity> listTNotice(Map<String, Object> params);

    /**
     * 新增
     * @param tNotice
     * @return
     */
	R saveTNotice(TNoticeEntity tNotice);

    /**
     * 根据id查询
     * @param id
     * @return
     */
	R getTNoticeById(Long id);

    /**
     * 修改
     * @param tNotice
     * @return
     */
	R updateTNotice(TNoticeEntity tNotice);

    /**
     * 删除
     * @param id
     * @return
     */
	R batchRemove(Long[] id);
	
}
