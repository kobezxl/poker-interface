package com.cn.poker.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.cn.poker.common.annotations.SameUrlData;
import com.cn.poker.common.util.ExpiryMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class SameUrlDataInterceptor extends HandlerInterceptorAdapter {
    private static Logger LOG = LoggerFactory.getLogger(SameUrlDataInterceptor.class);

    /**
     * 是否阻止提交,fasle阻止,true放行
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            SameUrlData annotation = method.getAnnotation(SameUrlData.class);
            if (annotation != null) {
                if(repeatDataValidator(request)){
                    //请求数据相同
                    LOG.warn("please don't repeat submit,url:"+ request.getServletPath());
                    JSONObject result = new JSONObject();
                    result.put("statusCode","500");
                    result.put("message","请勿重复请求");
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json; charset=utf-8");
                    response.getWriter().write(result.toString());
                    response.getWriter().close();
                    //                    拦截之后跳转页面
                    //                    String formRequest = request.getRequestURI();
                    //                    request.setAttribute("myurl", formRequest);
                    //                    request.getRequestDispatcher("/WebRoot/common/error/jsp/error_message.jsp").forward(request, response);
                    return false;
                }else {//如果不是重复相同数据
                    return true;
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }
    /**
     * 验证同一个url数据是否相同提交,相同返回true
     * @param httpServletRequest
     * @return
     */
    public boolean repeatDataValidator(HttpServletRequest httpServletRequest){
        //获取请求参数map
        String token = httpServletRequest.getHeader("token");
        Map<String, String[]> parameterMapNew = new HashMap<>();
        parameterMapNew.put("userId",new String[]{token});

        if (StringUtils.isBlank(token)){
            //如果没有token，直接放行
            return false;
        }
        //过滤过后的请求内容
        String params = JSONObject.toJSONString(parameterMapNew);

        System.out.println("params==========="+params);

        String url = httpServletRequest.getRequestURI();
        Map<String,String> map = new HashMap<>();
        //key为接口，value为参数
        map.put(url, params);
        String nowUrlParams = map.toString();

//        StringRedisTemplate smsRedisTemplate = SpringKit.getBean(StringRedisTemplate.class);
        String redisKey = token + url;
        ExpiryMap instance = ExpiryMap.getInstance();
        String preUrlParams = (String) instance.get(redisKey);
        if(preUrlParams == null){
            //如果上一个数据为null,表示还没有访问页面
            //存放并且设置有效期，2秒
            instance.put(redisKey,nowUrlParams,5000);
            return false;
        }else{//否则，已经访问过页面
            if(preUrlParams.equals(nowUrlParams)){
                //如果上次url+数据和本次url+数据相同，则表示重复添加数据
                return true;
            }else{//如果上次 url+数据 和本次url加数据不同，则不是重复提交
                instance.put(redisKey,nowUrlParams,5000);
                return false;
            }
        }
    }
}
