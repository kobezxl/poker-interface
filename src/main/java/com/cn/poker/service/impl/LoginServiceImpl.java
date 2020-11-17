package com.cn.poker.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cn.poker.common.entity.PokerConfig;
import com.cn.poker.common.entity.R;
import com.cn.poker.common.util.CommonUtils;
import com.cn.poker.common.util.HttpClientUtils;
import com.cn.poker.entity.User;
import com.cn.poker.entity.WpUsersEntity;
import com.cn.poker.service.LoginService;
import com.cn.poker.service.WpUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("loginService")
public class LoginServiceImpl implements LoginService{

    @Autowired
    private PokerConfig pokerConfig;

    @Autowired
    private WpUsersService wpUsersService;

    @Override
    public R login(User user) {
        boolean flag=false;
        Map<String,Object> params = new HashMap<>();
        try {
            String json = "log="+user.getUserName()+"&pwd="+user.getPassword()+"";
             flag = HttpClientUtils.doPost(pokerConfig.getUrl(),json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag) {
            WpUsersEntity wpUsersEntity = wpUsersService.getUserByName(user.getUserName());
            if (wpUsersEntity!=null) {
                params.put("userId",wpUsersEntity.getId());
                params.put("code",0);
                params.put("msg","登陆成功");
            }
            return R.ok(params);
        }
        return R.error(1,"登陆失败！");
    }
}
