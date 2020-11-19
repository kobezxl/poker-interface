package com.cn.poker.service.impl;

import com.cn.poker.dao.WpUsersMapper;
import com.cn.poker.manager.WpStratePackSumManager;
import com.cn.poker.manager.WpStrategyDetailManager;
import com.cn.poker.service.TaskService;
import com.cn.poker.thread.BatchDealUserSum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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


    /**
     * 用户时间汇总初始化
     */
    @Override
    public void userTimeSum() {

        List<Long> list = wpUsersMapper.selectUser();
        CountDownLatch countDownLatch = null;
        int maxThreads = 50;
        for (int i = 0; i < list.size(); i++) {

            //采用多线程处理请求
            //说明是新的开始
            if ((i - 1) % maxThreads == 0) {
                int count = list.size() - i + 1 >= maxThreads ? maxThreads : list.size() - i + 1;
                countDownLatch = new CountDownLatch(count);
            }
            executor.execute(new BatchDealUserSum(list.get(i),wpStrategyDetailManager,wpStratePackSumManager));
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
