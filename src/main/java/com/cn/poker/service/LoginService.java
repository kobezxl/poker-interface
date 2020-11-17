package com.cn.poker.service;

import com.cn.poker.common.entity.R;
import com.cn.poker.entity.User;

public interface LoginService {
    /*
    登陆
     */
    R login(User user);
}
