package com.cn.poker.manager;

import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.Query;
import com.cn.poker.entity.TNoticeEntity;

import java.util.List;


/**
 * 公告
 * @author <>
 */
public interface TNoticeManager {

    /**
     * 分页查询
     * @param page
     * @param search
     * @return
     */
	List<TNoticeEntity> listTNotice(Page<TNoticeEntity> page, Query search);

    /**
     * 新增
     * @param tNotice
     * @return
     */
	int saveTNotice(TNoticeEntity tNotice);

    /**
     * 根据id查询
     * @param id
     * @return
     */
	TNoticeEntity getTNoticeById(Long id);

    /**
     * 修改
     * @param tNotice
     * @return
     */
	int updateTNotice(TNoticeEntity tNotice);

    /**
     * 删除
     * @param id
     * @return
     */
	int batchRemove(Long[] id);
	
}
