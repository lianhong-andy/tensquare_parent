package com.tensquare.user.listener;

import com.aliyuncs.exceptions.ClientException;
import com.tensquare.user.util.SmsUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component//交给spring管理
@RabbitListener(queues = "sms")
public class SmsListener {
    @Value("${aliyun.sms.template_code}")
    private String template_code;
    @Value("${aliyun.sms.sign_name}")
    private String sign_name;
    @Autowired
    private SmsUtils smsUtils;
    @RabbitHandler
    public void sendSms(Map map) {
        try {
            //获取手机号
            String mobile = (String) map.get("mobile");
            //获取验证码
            String checkCode = (String) map.get("checkCode");
            smsUtils.sendSms(mobile,sign_name,template_code,checkCode);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new RuntimeException("发送验证码失败");
        }
    }
}
