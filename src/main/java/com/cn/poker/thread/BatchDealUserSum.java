package com.cn.poker.thread;

import com.cn.poker.dao.WpStrateMapper;
import com.cn.poker.dao.WpStrateSingleSumMapper;
import com.cn.poker.entity.*;
import com.cn.poker.manager.WpStrateManager;
import com.cn.poker.manager.WpStratePackSumManager;
import com.cn.poker.manager.WpStrategyDetailManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量处理用户时间汇总
 * 6人桌： 全部策略包 （截止时间大于当前时间）
 存在  : 不查了 ，直接 以这个为准 初始化
 不存在：查， 单池底  3bet池底   4bet池底  (截止时间大于当前时间)  按截止 时间倒叙 ，区第一条
 开始初始化 6人桌打包策略包
 再初始化   6人桌单个策略包

 8人桌： 全部策略包 （截止时间大于当前时间）
 存在  : 不查了 ，直接 以这个为准 初始化
 不存在：查， 单池底  3bet池底   4bet池底  (截止时间大于当前时间)  按截止 时间倒叙 ，区第一条
 开始初始化 8人桌打包策略包
 再初始化   8人桌单个策略包

 6人桌   8人桌 打包的都没查到 ，初始化打包购买策略包为0
 再查单个购买 按照截止时间倒叙取 最后一条
 查到了  初始化单个策略包
 没查到  初始化为0
 */
public class BatchDealUserSum implements Runnable{
    private Long userId;
    private WpStrategyDetailManager wpStrategyDetailManager;
    private WpStratePackSumManager wpStratePackSumManager;
    private WpStrateSingleSumMapper wpStrateSingleSumMapper;
    private WpStrateMapper wpStrateMapper;
    public BatchDealUserSum(Long userId, WpStrategyDetailManager wpStrategyDetailManager, WpStratePackSumManager wpStratePackSumManager, WpStrateSingleSumMapper wpStrateSingleSumMapper, WpStrateMapper wpStrateMapper) {
        this.userId = userId;
        this.wpStrategyDetailManager = wpStrategyDetailManager;
        this.wpStratePackSumManager = wpStratePackSumManager;
        this.wpStrateSingleSumMapper = wpStrateSingleSumMapper;
        this.wpStrateMapper = wpStrateMapper;
    }

    @Override
    public void run() {
        Integer userIds = userId.intValue();
        //1.初始化单个策略包
        /*
        1.查询所有的用户的id ，从用户维度去初始化
        2.查看所有的策略包。根据购买记录去初始化
        */
        //查看用户购买的策略包目前 只有 6人桌  8 人桌  支持购买
        //(全部 , 单池底  3bet池底   4 bet池底)
        //查看6人桌，再看8人桌
        for (int ty = 2; ty <= 3; ty++) {
            String type =  ty+"";
            String type_num = type + "-4";
            WpStrategyDetailEntity wpStrategyDetailEntity = wpStrategyDetailManager.selectByUserIdV1(type_num,userIds);
            if(wpStrategyDetailEntity == null){
                wpStrategyDetailEntity = wpStrategyDetailManager.selectByUserIdV2(type,userIds);
            }
            //没有打包购买策略包
            if(wpStrategyDetailEntity==null){
                List<WpStratePackSumEntity> list = getStratePackZeroSumList(userIds,type);
                wpStratePackSumManager.insertBatch(list);
                //有打包购买策略包
            }else {
                List<WpStratePackSumEntity> list = new ArrayList<>();
                if(wpStrategyDetailEntity.getDayCount()==-1){   //-1  代表永久
                    list = getStratePackSumListV1(userIds,wpStrategyDetailEntity,-1);
                }else {
					/*if(wpStrategyDetailEntity.getEndDate().after(new Date())){ //截止时间大于当前时间 ，
					}*/
                    list = getStratePackSumListV1(userIds,wpStrategyDetailEntity,1);
                }
                wpStratePackSumManager.insertBatch(list);
            }
        }

        //再以打包购买策略包为准 初始化单个策略包
        for (int i = 2; i <= 3; i++) {     // 6人桌    8人桌
            for (int j = 1; j <= 3; j++) {   // 单底池     3bet底池    6bet底池      全部
                StrateInfoVo strateInfoVo = new StrateInfoVo(userIds,i,j);
                List<WpStratePackSumEntity>	 stratePackSumList =  wpStratePackSumManager.getWpStratePackSumByUserId(strateInfoVo);//userId和type
                if(stratePackSumList!=null && stratePackSumList.size()>0){
                    List<WpStrateSingleSumEntity> list = getStrateSingleSum(i,j,userIds,stratePackSumList.get(0));    //初始化不为0
                    wpStrateSingleSumMapper.insertBatch(list);
                }else {
                    List<WpStrateSingleSumEntity> list = getStrateSingleSumZero(i,j,userIds);  //初始化为0
                    wpStrateSingleSumMapper.insertBatch(list);
                }
            }

        }



    }

    private List<WpStrateSingleSumEntity> getStrateSingleSumZero(int type, int poolType, Integer userId) {
        List<WpStrateSingleSumEntity> list = new ArrayList<>();
        WpStrateSingleSumEntity wpStrateSingleSumEntity = null;
        WpStrateEntity wpStrateEntity = new WpStrateEntity(poolType,type);
        List<WpStrateEntity> strateEntityList =  wpStrateMapper.getList(wpStrateEntity);
        for (WpStrateEntity strateEntity : strateEntityList) {
            wpStrateSingleSumEntity = new WpStrateSingleSumEntity(userId,strateEntity.getId(),type,poolType,null,null);
           list.add(wpStrateSingleSumEntity);
        }
        return list;
    }

    private List<WpStrateSingleSumEntity> getStrateSingleSum(int type, int poolType, Integer userId,WpStratePackSumEntity wpStratePackSumEntity) {
        List<WpStrateSingleSumEntity> list = new ArrayList<>();
        WpStrateSingleSumEntity wpStrateSingleSumEntity = null;
        WpStrateEntity wpStrateEntity = new WpStrateEntity(poolType,type);
        List<WpStrateEntity> strateEntityList =  wpStrateMapper.getList(wpStrateEntity);
        for (WpStrateEntity strateEntity : strateEntityList) {
            wpStrateSingleSumEntity = new WpStrateSingleSumEntity(userId,strateEntity.getId(),type,poolType,wpStratePackSumEntity.getStartTime(),wpStratePackSumEntity.getEndTime());
            list.add(wpStrateSingleSumEntity);
        }
        return list;
    }


    /**
     * 批量初始化用户打包购买策略包汇总
     * @param userId
     * @return
     */
    private List<WpStratePackSumEntity>  getStratePackZeroSumList(Integer userId,String type){
        List<WpStratePackSumEntity> list = new ArrayList<>();
        WpStratePackSumEntity wpStratePackSumEntity = null;
        for (int i = 1; i <= 4; i++) {
            wpStratePackSumEntity = new WpStratePackSumEntity();
            wpStratePackSumEntity.setPoolType(i); //1.单池底     2.3bet池底     3.4bet池底     4.全部
            wpStratePackSumEntity.setUserId(userId);
            wpStratePackSumEntity.setType(Integer.parseInt(type));//人桌
            list.add(wpStratePackSumEntity);
        }
/*        for (int i = 1; i <= 4; i++) {
            wpStratePackSumEntity = new WpStratePackSumEntity();
            wpStratePackSumEntity.setPoolType(i);
            wpStratePackSumEntity.setUserId(userId);
            wpStratePackSumEntity.setType(3);//8人桌
            list.add(wpStratePackSumEntity);
        }*/
        return list;
    }
    private List<WpStratePackSumEntity> getStratePackSumListV1(Integer userId,WpStrategyDetailEntity wpStrategyDetailEntity, int a) {
        List<WpStratePackSumEntity> list = new ArrayList<>();
        WpStratePackSumEntity wpStratePackSumEntity = null;
//        Integer otherType = 0;
/*        if(wpStrategyDetailEntity.getType()==2){
            otherType=3;
        }
        if(wpStrategyDetailEntity.getType()==3){
            otherType=2;
        }*/
        //全部策略包
        if (wpStrategyDetailEntity.getTypeNum().contains("-4")) {
	/*		if (a==-1) {//永久
			}else {
			}*/
            for (int i = 1; i <= 4; i++) {
                wpStratePackSumEntity = new WpStratePackSumEntity();
                wpStratePackSumEntity.setPoolType(i);
                wpStratePackSumEntity.setUserId(userId);
                wpStratePackSumEntity.setType(wpStrategyDetailEntity.getType());
                wpStratePackSumEntity.setStartTime(wpStrategyDetailEntity.getStartDate());
                wpStratePackSumEntity.setEndTime(wpStrategyDetailEntity.getEndDate());
                list.add(wpStratePackSumEntity);
            }
   /*         for (int i = 1; i <= 4; i++) {
                wpStratePackSumEntity = new WpStratePackSumEntity();
                wpStratePackSumEntity.setPoolType(i);
                wpStratePackSumEntity.setUserId(userId);
                wpStratePackSumEntity.setType(otherType);
                list.add(wpStratePackSumEntity);
            }*/
            //非全部策略包
        }else {
/*			if (a==-1) {//永久
			}else {
			}*/
            for (int i = 1; i <= 4; i++) {
                if(wpStrategyDetailEntity.getPoolType()==i){
                    wpStratePackSumEntity = new WpStratePackSumEntity();
                    wpStratePackSumEntity.setPoolType(i);
                    wpStratePackSumEntity.setUserId(userId);
                    wpStratePackSumEntity.setType(wpStrategyDetailEntity.getType());
                    wpStratePackSumEntity.setStartTime(wpStrategyDetailEntity.getStartDate());
                    wpStratePackSumEntity.setEndTime(wpStrategyDetailEntity.getEndDate());
                    list.add(wpStratePackSumEntity);
                }else {
                    wpStratePackSumEntity = new WpStratePackSumEntity();
                    wpStratePackSumEntity.setPoolType(i);
                    wpStratePackSumEntity.setUserId(userId);
                    wpStratePackSumEntity.setType(wpStrategyDetailEntity.getType());
                    list.add(wpStratePackSumEntity);
                }

            }
     /*       for (int i = 1; i <= 4; i++) {
                wpStratePackSumEntity = new WpStratePackSumEntity();
                wpStratePackSumEntity.setPoolType(i);
                wpStratePackSumEntity.setUserId(userId);
                wpStratePackSumEntity.setType(otherType);
                list.add(wpStratePackSumEntity);
            }*/
        }
        return list;
    }
}
