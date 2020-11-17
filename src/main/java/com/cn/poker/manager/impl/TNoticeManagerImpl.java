package com.cn.poker.manager.impl;

import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.Query;
import com.cn.poker.dao.TNoticeMapper;
import com.cn.poker.entity.TNoticeEntity;
import com.cn.poker.manager.TNoticeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 公告
 * @author <>
 */
@Component("tNoticeManager")
public class TNoticeManagerImpl implements TNoticeManager {

	@Autowired
	private TNoticeMapper tNoticeMapper;

    /**
     * 分页查询
     * @param page
     * @param search
     * @return
     */
	@Override
	public List<TNoticeEntity> listTNotice(Page<TNoticeEntity> page, Query search) {
		return tNoticeMapper.listForPage(page, search);
	}

    /**
     * 新增
     * @param tNotice
     * @return
     */
	@Override
	public int saveTNotice(TNoticeEntity tNotice) {
		return tNoticeMapper.save(tNotice);
	}

    /**
     * 根据id查询
     * @param id
     * @return
     */
	@Override
	public TNoticeEntity getTNoticeById(Long id) {
		TNoticeEntity tNotice = tNoticeMapper.getObjectById(id);
		return tNotice;
	}

    /**
     * 修改
     * @param tNotice
     * @return
     */
	@Override
	public int updateTNotice(TNoticeEntity tNotice) {
		return tNoticeMapper.update(tNotice);
	}

    /**
     * 删除
     * @param id
     * @return
     */
	@Override
	public int batchRemove(Long[] id) {
		int count = tNoticeMapper.batchRemove(id);
		return count;
	}
	
}
