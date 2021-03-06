package com.cn.poker.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.Query;
import com.cn.poker.common.util.DateUtils;
import com.cn.poker.dao.WpStrateMapper;
import com.cn.poker.dao.WpStratePackSumMapper;
import com.cn.poker.dao.WpStrateSingleSumMapper;
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
	@Autowired
    private WpStratePackSumMapper wpStratePackSumMapper;
	@Autowired
    private WpStrateSingleSumMapper wpStrateSingleSumMapper;

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


/*    job 新用户赠送+初始化 策略包时间汇总 ，赠送完之后 记得更新 wp_users----bl_status

    购买 策略包

1.全部策略包 有 购买 (排除系统赠送的),执行策略包 不允许 购买
2.购买过支线策略包 再去购买全部策略包，支线策略包按剩余天数折算成钻石抵扣，购买完全部策略包清空支线策略时间，按全部策略包重新计算、
            3.同策略多次充值 ，时间累计增加*/
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
    @Transactional(rollbackFor = Exception.class)
	public void saveOrerV1(OrderVo orderVo) throws Exception{
        WpStrategyDetailEntity wp = new WpStrategyDetailEntity();
        StrateInfoVo strateInfoVo = new StrateInfoVo();
        int gold = 0;
        //1.查询用户钻石
        WpIceInfo wpIceInfo = wpStrateMapper.selectGoldByUserId(orderVo.getUserId());
        if (wpIceInfo==null) {
            throw new Exception("钻石不够");
        }

        if (orderVo.getPackageId()!=null) {
            if(1==1){
                throw new Exception("暂不支持单个策略包购买");
            }
            //先判断有没有全部策略包
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
        /*    1.打包购买
             2.有全量策略包，不支持支线策略包
            3.购买过支线策略包*/


            Integer poolType = orderVo.getPoolType();
            if (poolType != 4) {  //非全量购买
                //查全部策略包
                WpStratePackSumEntity wpStratePackSumEntity = wpStratePackSumMapper.selectByUserIdAll(orderVo);
                //如果有全部策略包,提示暂不支持购买
                if (wpStratePackSumEntity != null) {
                    throw new Exception("你有" + (orderVo.getType() == 2 ? "6" : "8") + "人桌全部策略包,暂不能购买" + (orderVo.getType() == 2 ? "6" : "8") + "人桌支线策略包");
                } else {
                    //如果没有全部策略包，查看当前购买的支线策略包
                    wpStratePackSumEntity = wpStratePackSumMapper.selectByUserIdOne(orderVo);
                    if (wpStratePackSumEntity == null) {
                        //如果当前购买的支线策略包为空，直接购买
                        saveAndbuy(orderVo,wpIceInfo);
                        wpStratePackSumMapper.update(getWpStratePackSumEntity(orderVo));
                        wpStrateSingleSumMapper.update(getwpStrateSingleSum(orderVo));
                        //购买之后 重置打包策略包的汇总 wp_strate_pack_sum
                        //如果当前购买的支线策略包不为空，购买之后时间累加
                    } else {
                        saveAndbuy(orderVo,wpIceInfo);
                        wpStratePackSumMapper.update1(getWpStratePackSumEntity(orderVo));
                        wpStrateSingleSumMapper.update1(getwpStrateSingleSum(orderVo));
                    }
                }
            } else {
                //全量购买
                WpStratePackSumEntity wpStratePackSumEntity = wpStratePackSumMapper.selectByUserIdAll(orderVo);
                if (wpStratePackSumEntity!=null) {  //说明没有支线策略包，直接累加
                    saveAndbuy(orderVo,wpIceInfo);
                    wpStratePackSumMapper.update2(getWpStratePackSumEntity(orderVo));
                    wpStrateSingleSumMapper.update2(getwpStrateSingleSum(orderVo));
                }else {//先查有没有支线策略包
                    List<WpStratePackSumEntity> list =  wpStratePackSumMapper.selectByUserIdList(orderVo);   //poolType !=4
                    int mortgage = 0;
                    int total = 0;
                    if (list!=null && list.size()>0) {//支线策略包按剩余天数折算成钻石抵扣
                        for (WpStratePackSumEntity stratePackSumEntity : list) {
                            Date startTime = stratePackSumEntity.getStartTime();
                            Date endTime = stratePackSumEntity.getEndTime();
                            Long time = DateUtils.getTime(startTime, endTime);
                            Long time1 = DateUtils.getTime(startTime, new Date());
                            double percent = (time1.doubleValue()) / (time.doubleValue());
                            strateInfoVo = new StrateInfoVo();
                            strateInfoVo.setType(stratePackSumEntity.getType());
                            strateInfoVo.setPoolType(stratePackSumEntity.getPoolType());
                            StrateInfoVo wpStrateInfo1 = wpStrateMapper.getWpStrateInfo1(strateInfoVo);
                            //小于365天  按月计算  大于365天  按 年计算
                            Integer daycount = DateUtils.getDaycount(startTime, endTime);
                            if(daycount<365){
                                int month = wpStrateInfo1.getMonth();
                                int i = daycount / 30;
                                int coin = new Double(Math.floor(i * month * percent)).intValue();//消耗的金币
                                 mortgage = i * month - coin;   //还能抵押
                            }else {
                                int year = wpStrateInfo1.getYear();
                                int i = daycount / 365;
                                int coin = new Double(Math.floor(i * year * percent)).intValue();//消耗的金币
                                 mortgage = i * year - coin;   //还能抵押
                            }
                            total = total+mortgage;
                        }
                        saveAndbuy1(orderVo,wpIceInfo,total);
                        wpStratePackSumMapper.update3(getWpStratePackSumEntity(orderVo));
                        wpStrateSingleSumMapper.update3(getwpStrateSingleSum(orderVo));
                    }else {
                        saveAndbuy(orderVo,wpIceInfo);
                        wpStratePackSumMapper.update3(getWpStratePackSumEntity(orderVo));
                        wpStrateSingleSumMapper.update3(getwpStrateSingleSum(orderVo));
                    }
                }
            }
        }

	}

    private WpStrateSingleSumEntity getwpStrateSingleSum(OrderVo orderVo) {
        WpStrateSingleSumEntity wpStrateSingleSumEntity = new WpStrateSingleSumEntity();
        wpStrateSingleSumEntity.setUserId(orderVo.getUserId());
        wpStrateSingleSumEntity.setType(orderVo.getType());
        wpStrateSingleSumEntity.setPoolType(orderVo.getPoolType());
        wpStrateSingleSumEntity.setStartTime(orderVo.getStartDate());
        wpStrateSingleSumEntity.setEndTime(orderVo.getEndDate());
        wpStrateSingleSumEntity.setDaySum(orderVo.getDaySum());
        return wpStrateSingleSumEntity;
    }


    private WpStratePackSumEntity getWpStratePackSumEntity(OrderVo orderVo) {
        WpStratePackSumEntity wpStratePackSumEntity = new WpStratePackSumEntity();
        wpStratePackSumEntity.setUserId(orderVo.getUserId());
        wpStratePackSumEntity.setType(orderVo.getType());
        wpStratePackSumEntity.setPoolType(orderVo.getPoolType());
        wpStratePackSumEntity.setStartTime(orderVo.getStartDate());
        wpStratePackSumEntity.setEndTime(orderVo.getEndDate());
        wpStratePackSumEntity.setDaySum(orderVo.getDaySum());
        return wpStratePackSumEntity;
    }


    private void saveAndbuy(OrderVo orderVo,WpIceInfo wpIceInfo) throws Exception{
        int dayCount = orderVo.getDayCount();
        //判断是否有购买永久策略包，如果有 禁止购买
        if (dayCount==3) {
            List<WpStrategyDetailEntity> list = wpStrategyDetailMapper.selectForver(orderVo);
            if (list!=null && list.size()>0) {
                throw new Exception("你已有永久策略包,不能重复购买");
            }
        }

        WpStrategyDetailEntity wp = new WpStrategyDetailEntity();
        StrateInfoVo strateInfoVo = new StrateInfoVo();
        strateInfoVo.setType(orderVo.getType());
        strateInfoVo.setPoolType(orderVo.getPoolType());
        StrateInfoVo wpStrateInfo1 = wpStrateMapper.getWpStrateInfo1(strateInfoVo);
        wp.setCreateDate(new Date());
        wp.setPoolType(orderVo.getPoolType());
        wp.setType(orderVo.getType());

        int gold = 0;
        int count = 0;
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
        orderVo.setStartDate(wp.getStartDate());
        orderVo.setEndDate(wp.getEndDate());
        orderVo.setDaySum(wp.getDayCount());
        wpStrategyDetailMapper.save(wp);

    //2.增加用户钻石购买记录
    //3.扣除用户钻石
    WpIceInfo wpIceInfo1 = new WpIceInfo(0.0,orderVo.getUserId(),new Double(gold));
        wpStrateMapper.subtract(wpIceInfo1);
    }

    private void saveAndbuy1(OrderVo orderVo,WpIceInfo wpIceInfo,int coin) throws Exception{
        int dayCount = orderVo.getDayCount();
        //判断是否有购买永久策略包，如果有 禁止购买
        if (dayCount==3) {
            List<WpStrategyDetailEntity> list = wpStrategyDetailMapper.selectForver(orderVo);
            if (list!=null && list.size()>0) {
                throw new Exception("你已有永久策略包,不能重复购买");
            }
        }
        WpStrategyDetailEntity wp = new WpStrategyDetailEntity();
        StrateInfoVo strateInfoVo = new StrateInfoVo();
        strateInfoVo.setType(orderVo.getType());
        strateInfoVo.setPoolType(orderVo.getPoolType());
        StrateInfoVo wpStrateInfo1 = wpStrateMapper.getWpStrateInfo1(strateInfoVo);
        wp.setCreateDate(new Date());
        wp.setPoolType(orderVo.getPoolType());
        wp.setType(orderVo.getType());

        int gold = 0;
        int count = 0;
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
        gold = gold - coin;
        if(gold<0){
            throw new Exception("剩余的策略包时长高于您要购买的时长 如需要转换请联系客服");
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
        orderVo.setStartDate(wp.getStartDate());
        orderVo.setEndDate(wp.getEndDate());
        orderVo.setDaySum(wp.getDayCount());
        wpStrategyDetailMapper.save(wp);

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
