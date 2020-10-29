package com.atguigu.msmservice.controller;


import com.aliyuncs.utils.StringUtils;
import com.atguigu.commonutils.R;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone){
        System.out.println("got a phone : " + phone);
        String code = redisTemplate.opsForValue().get(phone);
     //   String code = null;
        if (!StringUtils.isEmpty(code)){
            return R.ok();
        }
    //    code = RandomUtil.getFourBitRandom();
        code = "1234";  // didn't buy aliyun msm, use 1234 to simulate
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        boolean isSend = msmService.send(param, phone);
        if (isSend) {
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.ok();
        }
        return R.error().message("send fail");
    }
}
