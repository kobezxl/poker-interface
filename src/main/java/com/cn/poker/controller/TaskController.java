package com.cn.poker.controller;

import com.cn.poker.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@EnableScheduling
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     *  初始化用户时间汇总
     */
//    @Scheduled(cron = "*/5 * * * * ?")
    public void userTimeSum(){
        taskService.userTimeSum();
    }


    /**
     *  初始化用户时间汇总
     */
//    @Scheduled(cron = "*/30 * * * * ?")
    public void userTimeSum1(){
        taskService.userTimeSum1();
    }
}
