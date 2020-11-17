package com.cn.poker.controller;

import com.cn.poker.common.controller.AbstractController;
import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.R;
import com.cn.poker.entity.TNoticeEntity;
import com.cn.poker.service.TNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 公告
 * @author <>
 */
@RestController
@RequestMapping("/notice")
public class TNoticeController extends AbstractController {
	
	@Autowired
	private TNoticeService tNoticeService;
	
	/**
	 * 列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	public Page<TNoticeEntity> list(@RequestBody Map<String, Object> params) {
		return tNoticeService.listTNotice(params);
	}
		
	/**
	 * 新增
	 * @param tNotice
	 * @return
	 */

	@RequestMapping("/save")
	public R save(@RequestBody TNoticeEntity tNotice) {
		return tNoticeService.saveTNotice(tNotice);
	}
	
	/**
	 * 根据id查询详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/info")
	public R getById(@RequestBody Long id) {
		return tNoticeService.getTNoticeById(id);
	}
	
	/**
	 * 修改
	 * @param tNotice
	 * @return
	 */
	@RequestMapping("/update")
	public R update(@RequestBody TNoticeEntity tNotice) {
		return tNoticeService.updateTNotice(tNotice);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/remove")
	public R batchRemove(@RequestBody Long[] id) {
		return tNoticeService.batchRemove(id);
	}
	
}
