package com.jjwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateJJWT {
    @Test
    public void createJJWT(){
        JwtBuilder builder = Jwts.builder().setId("888")
                .setSubject("小白")
                .setIssuedAt(new Date())//setIssuedAt用于设置签发时间
                .signWith(SignatureAlgorithm.HS256,"kelly")
                .setExpiration(new Date(System.currentTimeMillis()+60000))//设置token的过期时间为1min
                .claim("roles","admin");//自定义claims,存储所需信息，如角色
        System.out.println(builder.compact());
    }

    @Test
    public void testParseJJWT(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1MzgwMjA4NzcsImV4cCI6MTUzODAyMDkzNywicm9sZXMiOiJhZG1pbiJ9.VuoJOmdRh4ChKMquf3WDq3OkfUBOo_MFQoUdDDbkTsQ";
        Claims claims = Jwts.parser().setSigningKey("kelly").parseClaimsJws(token).getBody();
        System.out.println("id: "+claims.getId());
        System.out.println("subject: "+claims.getSubject());
        //角色
        System.out.println("roles:"+claims.get("roles"));
        //签发时间
        System.out.println("IssuedAt: "+new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss").format(claims.getIssuedAt()));
        //过期时间
        System.out.println("Expiration"+new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss").format(claims.getExpiration()));
    }

}
