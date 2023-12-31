package com.iffat.springboot.interceptors.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component("timeInterceptor")
public class LoadingTimeInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoadingTimeInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod method = (HandlerMethod) handler;
        long start = System.currentTimeMillis();
        request.setAttribute("start", start);

        Random random = new Random();
        int delay = random.nextInt(500);
        Thread.sleep(delay);

        logger.info("Loading time interceptor preHandle() " + method.getMethod().getName());

        /* Only when returning false to send response custom */
//        Map<String, String> json = new HashMap<>();
//        json.put("error", "You do not have access to this page");
//        json.put("data", new Date().toString());
//
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonString = mapper.writeValueAsString(json);
//        response.setContentType("application/json");
//        response.setStatus(401);
//        response.getWriter().write(jsonString);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        long end = System.currentTimeMillis();
        long start = (long) request.getAttribute("start");
        long result = end - start;

        logger.info("ElapsedTime: {} millisecond", result);
        logger.info("Loading time interceptor postHandle() "+ ((HandlerMethod) handler).getMethod().getName());
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
