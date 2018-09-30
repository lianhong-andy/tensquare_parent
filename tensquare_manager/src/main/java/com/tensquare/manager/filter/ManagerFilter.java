package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public String filterType() {
        return "pre";//前置过滤器
    }

    @Override
    public int filterOrder() {
        return 0;//优先级为0，数字越大，优先级越低
    }

    @Override
    public boolean shouldFilter() {
        return true;//是否执行该过滤器，此处为true，说明需要过滤
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("zuul过滤器...");
        //向header中添加鉴权令牌
        RequestContext requestContext = RequestContext.getCurrentContext();
        //获取header
        HttpServletRequest request = requestContext.getRequest();
        if(request.getMethod().equals("OPTIONS")){
            return null;
        }
        String url = request.getRequestURI().toString();
        if(url.indexOf("/admin/login")>0){
            System.out.println("登录页面"+url);
            return null;
        }
        String header = request.getHeader("Authorization");
        if(header!=null&&!"".equals(header)){
            if(header.startsWith("Bearer ")){
                String token = header.substring(7);
                Claims claims = jwtUtil.parseJWT(token);
                if(claims!=null){
                    if(claims.get("roles").equals("admin")){
                        //转发请求头
                        requestContext.addZuulRequestHeader("header",header);
                        System.out.println("token通过验证，添加了头信息"+header);
                        return null;
                    }
                }
            }
        }
        requestContext.setSendZuulResponse(false);//终止运行
        requestContext.setResponseStatusCode(401);//http状态码
        requestContext.setResponseBody("无权限访问");//http状态码
        requestContext.getResponse().setContentType("text/html;charset=UTF-8");
        return null;
    }
}
