package com.atguigu.msmservice.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.client.naming.utils.StringUtils;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.msmservice.service.MsmService;
import org.springframework.stereotype.Component;

import javax.swing.text.html.FormSubmitEvent;
import java.util.Map;

@Component
public class MsmServiceImpl implements MsmService {

    @Override
    public boolean send(Map<String, Object> param, String phone) {

        if(StringUtils.isEmpty(phone)) return false;

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAI4G2zeUbPPkq1ALCvFzsH", "7bRiMBfkOMU1NdDQwoXPuSEyCh16qF");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        request.putQueryParameter("PhoneNumbers", phone);  // phone
        request.putQueryParameter("SignName", "谷粒教育网站");  // 签名
        request.putQueryParameter("TemplateCode", "SMS_205133772");  // 模板 code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));  // 验证码数据
        try {
            CommonResponse res = client.getCommonResponse(request);
            return res.getHttpResponse().isSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
