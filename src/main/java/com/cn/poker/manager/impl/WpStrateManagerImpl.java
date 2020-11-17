package com.cn.poker.manager.impl;

import java.util.Date;
import java.util.List;

import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.Query;
import com.cn.poker.common.util.DateUtils;
import com.cn.poker.dao.WpStrateMapper;
import com.cn.poker.dao.WpStrategyDetailMapper;
import com.cn.poker.entity.*;
import com.cn.poker.manager.WpStrateManager;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * 策略包
 * @author <>
 */
@Component("wpStrateManager")
public class WpStrateManagerImpl implements WpStrateManager {

	@Autowired
	private WpStrateMapper wpStrateMapper;
	@Autowired
	private WpStrategyDetailMapper wpStrategyDetailMapper;

    /**
     * 分页查询
     * @param page
     * @param search
     * @return
     */
	@Override
	public List<WpStrateEntity> listWpStrate(Page<WpStrateEntity> page, Query search) {
		return wpStrateMapper.listForPage(page, search);
	}


    /**
     * 根据id查询
     * @param id
     * @return
     */
	@Override
	public WpStrateEntity getWpStrateById(Long id) {
		WpStrateEntity wpStrate = wpStrateMapper.getObjectById(id);
		return wpStrate;
	}

	@Override
	public StrateInfoVo getWpStrateInfo(StrateInfoVo strateInfoVo) {
	    if(strateInfoVo.getId()!=null){
            return wpStrateMapper.getWpStrateInfo(strateInfoVo);
        }else {
            return wpStrateMapper.getWpStrateInfo1(strateInfoVo);
        }

	}

	@Override
	public WpIceInfo selectGoldByUserId(int userId) {
		return wpStrateMapper.selectGoldByUserId(userId);
	}

	@Override
	public List<PackInfo> packaging(int userId) {
		return wpStrateMapper.packaging(userId);
	}

	@Override
    @Transactional(rollbackFor = Exception.class)
	public void saveOrer(OrderVo orderVo) throws Exception{
        WpStrategyDetailEntity wp = new WpStrategyDetailEntity();
        StrateInfoVo strateInfoVo = new StrateInfoVo();
        int gold = 0;
        //1.查询用户钻石
        WpIceInfo wpIceInfo = wpStrateMapper.selectGoldByUserId(orderVo.getUserId());
        if (wpIceInfo==null) {
            throw new Exception("钻石不够");
        }
        if (orderVo.getPackageId()!=null) {
            WpStrateEntity wpStrate = wpStrateMapper.getObjectById(orderVo.getPackageId());
            strateInfoVo.setId(orderVo.getPackageId());
            StrateInfoVo wpStrateInfo = wpStrateMapper.getWpStrateInfo(strateInfoVo);
            int count = 0;

            wp.setStrategyId(orderVo.getPackageId());
            wp.setCreateDate(new Date());
            wp.setPoolType(wpStrate.getPoolType());
            wp.setType(wpStrate.getType());
            int dayCount = orderVo.getDayCount();
            if (dayCount==1) {
                count=30;
                gold = wpStrateInfo.getMonth();
            }else if (dayCount==2){
                count=365;
                gold=wpStrateInfo.getYear();
            }else if(dayCount==3){
                count=-1;
                gold=wpStrateInfo.getForver();
            }
            wp.setDayCount(count);
            wp.setStartDate(new Date());
            wp.setEndDate(DateUtils.getDateAfter(count));
            wp.setUserId(orderVo.getUserId());
            wp.setGold(gold);
            wp.setTypeNum(wpStrateInfo.getType()+"");
            if ((wpIceInfo.getIceHaveMoney()-wpIceInfo.getIceGetMoney())<gold) {
                throw new Exception("钻石不够,所需钻石:"+gold);
            }
            wpStrategyDetailMapper.save(wp);
        }else {
            int count = 0;

            strateInfoVo.setType(orderVo.getType());
            strateInfoVo.setPoolType(orderVo.getPoolType());
            StrateInfoVo wpStrateInfo1 = wpStrateMapper.getWpStrateInfo1(strateInfoVo);
            wp.setCreateDate(new Date());
            wp.setPoolType(orderVo.getPoolType());
            wp.setType(orderVo.getType());
            int dayCount = orderVo.getDayCount();
            if (dayCount==1) {
                count=30;
                gold = wpStrateInfo1.getMonth();
            }else if (dayCount==2){
                count=365;
                gold=wpStrateInfo1.getYear();
            }else if(dayCount==3){
                count=-1;
                gold=wpStrateInfo1.getForver();
            }
            wp.setDayCount(count);
            wp.setStartDate(new Date());
            wp.setEndDate(DateUtils.getDateAfter(count));
            wp.setUserId(orderVo.getUserId());
            wp.setGold(gold);
            wp.setTypeNum(orderVo.getType()+"-"+orderVo.getPoolType());
            if ((wpIceInfo.getIceHaveMoney()-wpIceInfo.getIceGetMoney())<gold) {
                throw new Exception("钻石不够,所需钻石:"+gold);
            }
            wpStrategyDetailMapper.save(wp);
        }
        //2.增加用户钻石购买记录
        //3.扣除用户钻石
        WpIceInfo wpIceInfo1 = new WpIceInfo(0.0,orderVo.getUserId(),new Double(gold));
        wpStrateMapper.subtract(wpIceInfo1);

	}

    @Override
    public List<WpRecordVo> listRecord(Page<WpRecordVo> page, Query search) {
        List<WpRecordVo> wpRecordVos = wpStrategyDetailMapper.listRecordForPage(page, search);
        for (WpRecordVo wpRecordVo : wpRecordVos) {
            if (!wpRecordVo.getName().contains("-")) {
                String type = getType(wpRecordVo.getName(),wpRecordVo.getPakgeType());
                wpRecordVo.setName(type);
            }else {
                wpRecordVo.setName(getName(wpRecordVo.getName()));
            }
        }
        return wpRecordVos;
    }

    private String getName(String name) {
	    String s = "";
        if (name.equals("2-1")) {
            s = "6人桌/单次加注底池打包购买";
        }
        if (name.equals("2-2")) {
            s = "6人桌/3bet底池打包购买";
        }
        if (name.equals("2-3")) {
            s = "6人桌/4bet底池打包购买";
        }
        if (name.equals("2-4")) {
            s = "6人桌全部策略包购买";
        }
        if (name.equals("3-1")) {
            s = "8人桌/单次加注底池打包购买";
        }
        if (name.equals("3-2")) {
            s = "8人桌/3bet底池打包购买";
        }
        if (name.equals("3-3")) {
            s = "8人桌/4bet底池打包购买";
        }
        if (name.equals("3-4")) {
            s = "8人桌全部策略包购买";
        }
        return  s;
    }

    private String getType(String name, String pakgeType) {
	    String type = "";
	    if (pakgeType.equals("2-1")){
            type = "6人桌/单次加注底池/"+name;
        }
        if (pakgeType.equals("2-2")){
            type = "6人桌/3bet底池/"+name;
        }
        if (pakgeType.equals("2-3")){
            type = "6人桌/4bet底池/"+name;
        }
        if (pakgeType.equals("3-1")){
            type = "8人桌/单次加注底池/"+name;
        }
        if (pakgeType.equals("3-2")){
            type = "8人桌/3bet底池/"+name;
        }
        if (pakgeType.equals("3-3")){
            type = "8人桌/4bet底池/"+name;
        }
	    return type;
    }

    @Override
    public List<WpStragePrice>  packagType() {
        return wpStrateMapper.packagType();
    }

}
