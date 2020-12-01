package com.cn.poker.common.config;

import com.cn.poker.common.interceptor.SameUrlDataInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvcConfigExt extends WebMvcConfigurationSupport {
    /**
     * 防止重复提交拦截器
     */
    @Autowired
    private SameUrlDataInterceptor sameUrlDataInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 避开静态资源
        List<String> resourcePaths = defineResourcePaths();
        registry.addInterceptor(sameUrlDataInterceptor).addPathPatterns("/**").excludePathPatterns(resourcePaths);// 重复请求
    }

    /**
     * 自定义静态资源路径
     *
     * @return
     */

    public List<String> defineResourcePaths() {
        List<String> patterns = new ArrayList<>();
        patterns.add("/assets/**");
        patterns.add("/upload/**");
        patterns.add("/static/**");
        patterns.add("/common/**");
        patterns.add("/error");
        return patterns;
    }
}
