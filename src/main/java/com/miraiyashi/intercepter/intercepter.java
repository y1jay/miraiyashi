package com.miraiyashi.intercepter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
@Slf4j
public class intercepter implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("===============================================");
        System.out.println("==================== BEGIN ====================");
        System.out.println("Request URI ===> " + request.getRequestURI());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("==================== END ======================");
        System.out.println("===============================================");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
