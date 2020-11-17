package com.cn.poker.service.impl;

import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.Query;
import com.cn.poker.common.entity.R;
import com.cn.poker.common.util.CommonUtils;
import com.cn.poker.entity.TNoticeEntity;
import com.cn.poker.manager.TNoticeManager;
import com.cn.poker.service.TNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 公告
 * @author <>
 */
@Service("tNoticeService")
public class TNoticeServiceImpl implements TNoticeService {

	@Autowired
	private TNoticeManager tNoticeManager;

    /**
     * 分页查询
     * @param params
     * @return
     */
	@Override
	public Page<TNoticeEntity> listTNotice(Map<String, Object> params) {
		Query query = new Query(params);
		Page<TNoticeEntity> page = new Page<>(query);
		tNoticeManager.listTNotice(page, query);
		return page;
	}

    /**
     * 新增
     * @param tNotice
     * @return
     */
	@Override
	public R saveTNotice(TNoticeEntity tNotice) {
		tNotice.setContent(tNotice.getContent().replace("&lt;","<").replace("&gt;",">"));
		tNotice.setEnContent(tNotice.getEnContent().replace("&lt;","<").replace("&gt;",">"));
		int count = tNoticeManager.saveTNotice(tNotice);
		return CommonUtils.msg(count);
	}

    /**
     * 根据id查询
     * @param id
     * @return
     */
	@Override
	public R getTNoticeById(Long id) {
		TNoticeEntity tNotice = tNoticeManager.getTNoticeById(id);
		return CommonUtils.msg(tNotice);
	}

    /**
     * 修改
     * @param tNotice
     * @return
     */
	@Override
	public R updateTNotice(TNoticeEntity tNotice) {
		tNotice.setContent(tNotice.getContent().replace("&lt;","<").replace("&gt;",">"));
		tNotice.setEnContent(tNotice.getEnContent().replace("&lt;","<").replace("&gt;",">"));
		int count = tNoticeManager.updateTNotice(tNotice);
		return CommonUtils.msg(count);
	}

    /**
     * 删除
     * @param id
     * @return
     */
	@Override
	public R batchRemove(Long[] id) {
		int count = tNoticeManager.batchRemove(id);
		return CommonUtils.msg(id, count);
	}

}
