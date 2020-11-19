package com.cn.poker.thread;

import com.cn.poker.entity.WpStratePackSumEntity;
import com.cn.poker.entity.WpStrategyDetailEntity;
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
    public BatchDealUserSum(Long userId,WpStrategyDetailManager wpStrategyDetailManager,WpStratePackSumManager wpStratePackSumManager) {
        this.userId = userId;
        this.wpStrategyDetailManager = wpStrategyDetailManager;
        this.wpStratePackSumManager = wpStratePackSumManager;
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
        String type =  "2";
        type = type + "-4";
        WpStrategyDetailEntity wpStrategyDetailEntity = wpStrategyDetailManager.selectByUserIdV1(type,userIds);
        if(wpStrategyDetailEntity == null){
            type =  "2";
            wpStrategyDetailEntity = wpStrategyDetailManager.selectByUserIdV2(type,userIds);
        }
        //没有打包购买策略包
        if(wpStrategyDetailEntity==null){
            List<WpStratePackSumEntity> list = getStratePackSumList(userIds,type);
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
        //先查购买记录，有没有

    }



    /**
     * 批量初始化用户打包购买策略包汇总
     * @param userId
     * @return
     */
    private List<WpStratePackSumEntity>  getStratePackSumList(Integer userId,String type){
        List<WpStratePackSumEntity> list = new ArrayList<>();
        WpStratePackSumEntity wpStratePackSumEntity = null;
        for (int i = 1; i <= 4; i++) {
            wpStratePackSumEntity = new WpStratePackSumEntity();
            wpStratePackSumEntity.setPoolType(i);
            wpStratePackSumEntity.setUserId(userId);
            wpStratePackSumEntity.setType(Integer.parseInt(type));//6人桌
            list.add(wpStratePackSumEntity);
        }
        for (int i = 1; i <= 4; i++) {
            wpStratePackSumEntity = new WpStratePackSumEntity();
            wpStratePackSumEntity.setPoolType(i);
            wpStratePackSumEntity.setUserId(userId);
            wpStratePackSumEntity.setType(3);//8人桌
            list.add(wpStratePackSumEntity);
        }
        return list;
    }
    private List<WpStratePackSumEntity> getStratePackSumListV1(Integer userId,WpStrategyDetailEntity wpStrategyDetailEntity, int a) {
        List<WpStratePackSumEntity> list = new ArrayList<>();
        WpStratePackSumEntity wpStratePackSumEntity = null;
        Integer otherType = 0;
        if(wpStrategyDetailEntity.getType()==2){
            otherType=3;
        }
        if(wpStrategyDetailEntity.getType()==3){
            otherType=2;
        }
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
            for (int i = 1; i <= 4; i++) {
                wpStratePackSumEntity = new WpStratePackSumEntity();
                wpStratePackSumEntity.setPoolType(i);
                wpStratePackSumEntity.setUserId(userId);
                wpStratePackSumEntity.setType(otherType);
                list.add(wpStratePackSumEntity);
            }
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
            for (int i = 1; i <= 4; i++) {
                wpStratePackSumEntity = new WpStratePackSumEntity();
                wpStratePackSumEntity.setPoolType(i);
                wpStratePackSumEntity.setUserId(userId);
                wpStratePackSumEntity.setType(otherType);
                list.add(wpStratePackSumEntity);
            }
        }
        return list;
    }
}
