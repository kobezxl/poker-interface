package com.cn.poker.service;

import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.R;
import com.cn.poker.entity.OrderVo;
import com.cn.poker.entity.StrateInfoVo;
import com.cn.poker.entity.WpRecordVo;
import com.cn.poker.entity.WpStrateEntity;

import java.util.Map;


/**
 * 策略包
 * @author <>
 */
public interface WpStrateService {

    /**
     * 分页查询
     * @param params
     * @return
     */
	Page<WpStrateEntity> listWpStrate(Map<String, Object> params);


    /**
     * 根据id查询
     * @param id
     * @return
     */
	R getWpStrateById(Long id);


    /**
     * 获取策略包明细
     * @param strateInfoVo
     * @return
     */
    R getWpStrateInfo(StrateInfoVo strateInfoVo);

    /**
     * 根据用户id获取 钻石数量
     * @param strateInfoVo
     * @return
     */
    R getGold(StrateInfoVo strateInfoVo);

    /**
     * 打包购买列表
     * @param userId
     * @return
     */
    R packaging(StrateInfoVo strateInfoVo);

    /**
     * 单个购买策略包
     * @param orderVo
     * @return
     */
    R saveOrer(OrderVo orderVo);
    /**
     * 分页查询--策略包购买记录
     * @param params
     * @return
     */
    Page<WpRecordVo> listRecord(Map<String, Object> params);

    R packagType(StrateInfoVo strateInfoVo);
}
