package com.cn.poker.manager;
import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.Query;
import com.cn.poker.entity.*;
import lombok.NonNull;

import java.util.List;


/**
 * 策略包
 * @author <>
 */
public interface WpStrateManager {

    /**
     * 分页查询
     * @param page
     * @param search
     * @return
     */
	List<WpStrateEntity> listWpStrate(Page<WpStrateEntity> page, Query search);


    /**
     * 根据id查询
     * @param id
     * @return
     */
	WpStrateEntity getWpStrateById(Long id);

    /**
     * 获取策略包明细
     * @param strateInfoVo
     * @return
     */

    StrateInfoVo getWpStrateInfo(StrateInfoVo strateInfoVo);

    /**
     * 根据用户id获取钻石数量
     * @param userId
     * @return
     */
    WpIceInfo selectGoldByUserId( int userId);

    /**
     * 打包构面列表
     * @param userId
     * @return
     */
    List<PackInfo> packaging(int userId);

    /**
     * 添加投注记录
     * @param orderVo
     */
    void saveOrer(OrderVo orderVo) throws  Exception;

    List<WpRecordVo> listRecord(Page<WpRecordVo> page, Query query);

    List<WpStragePrice>  packagType();
}
