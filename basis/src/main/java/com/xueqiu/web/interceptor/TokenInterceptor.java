package com.xueqiu.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * Created by 郭根权 on 2018/11/22.
 */
public class TokenInterceptor extends HandlerInterceptorAdapter{

    private static final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    private static final String TOKEN = "token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod){
            Method method = ((HandlerMethod) handler).getMethod();
            Token annotation = method.getAnnotation(Token.class);
            if (annotation != null){
                HttpSession session = request.getSession();

            }
        }
        return super.preHandle(request, response, handler);
    }
}
