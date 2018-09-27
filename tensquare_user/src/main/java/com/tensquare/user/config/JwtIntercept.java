package com.tensquare.user.config;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtIntercept implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        try {
            //拦截请求后只对符合条件的数据进行操作
            String header = request.getHeader("Authorization");
            if(header!=null&&header.startsWith("Bearer ")){
                String token = header.substring(7);
                Claims claims = jwtUtil.parseJWT(token);
                if(claims!=null) {
                    if (claims.get("roles").equals("admin")) {//管理员角色
                        //存到request域中
                        request.setAttribute("admin_claims",claims);
                    }
                    if (claims.get("roles").equals("user")) {//管理员角色
                        //存到request域中
                        request.setAttribute("user_claims",claims);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;//放行请求
    }



}
