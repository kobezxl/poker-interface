package com.cn.poker.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class User {
    @NonNull
    private String userName;
    @NonNull
    private String password;

    public User(@NonNull String userName, @NonNull String password) {
        this.userName = userName;
        this.password = password;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
