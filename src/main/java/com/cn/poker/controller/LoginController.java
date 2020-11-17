package com.cn.poker.controller;

import com.cn.poker.common.entity.R;
import com.cn.poker.entity.User;
import com.cn.poker.entity.WpUsersEntity;
import com.cn.poker.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/poker")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登陆
     * @param
     * @return
     */
    @RequestMapping("/login")
    public R login(@RequestBody User user) {
        return loginService.login(user);
    }


}
