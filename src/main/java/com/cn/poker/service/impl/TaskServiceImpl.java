package com.cn.poker.service.impl;

import com.cn.poker.dao.WpStrateMapper;
import com.cn.poker.dao.WpStrateSingleSumMapper;
import com.cn.poker.dao.WpUsersMapper;
import com.cn.poker.entity.*;
import com.cn.poker.manager.WpStrateManager;
import com.cn.poker.manager.WpStratePackSumManager;
import com.cn.poker.manager.WpStrategyDetailManager;
import com.cn.poker.service.TaskService;
import com.cn.poker.thread.BatchDealUserSum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;


@Service("taskService")
public class TaskServiceImpl implements TaskService {

    @Autowired
    private WpUsersMapper wpUsersMapper;

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private WpStrategyDetailManager wpStrategyDetailManager;

    @Autowired
    private WpStratePackSumManager wpStratePackSumManager;

    @Autowired
    private WpStrateSingleSumMapper wpStrateSingleSumMapper;

    @Autowired
    private WpStrateMapper wpStrateMapper;


    /**
     * 用户时间汇总初始化
     */
    @Override
    public void userTimeSum() {

        List<Long> list = wpUsersMapper.selectUser();
        CountDownLatch countDownLatch = null;
        int maxThreads = 50;
        if(list!=null && list.size()>0){
            for (int i = 1; i <= list.size(); i++) {

                //采用多线程处理请求
                //说明是新的开始
                if ((i - 1) % maxThreads == 0) {
                    int count = list.size() - i + 1 >= maxThreads ? maxThreads : list.size() - i + 1;
                    countDownLatch = new CountDownLatch(count);
                }
                executor.execute(new BatchDealUserSum(list.get(i-1),countDownLatch,wpStrategyDetailManager,wpStratePackSumManager,wpStrateSingleSumMapper,wpStrateMapper));
                //每启动maxThreads条线程,主线程进入等待
                if (i % maxThreads == 0 || i == list.size()) {
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
            wpUsersMapper.batchUpdateUser(list);
        }


    }

    @Override
    public void userTimeSum1() {

        List<Long> userList = wpUsersMapper.selectUser();

        for (Long aLong : userList) {
            int userIds = aLong.intValue();
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
            wpUsersMapper.batchUpdateUser(userList);
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

}
