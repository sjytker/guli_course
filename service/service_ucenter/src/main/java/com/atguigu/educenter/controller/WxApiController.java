package com.atguigu.educenter.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.ConstantPropertiesUtil;
import com.atguigu.educenter.utils.HttpClientUtils;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;
import java.util.HashMap;

//@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {


    /**
     * @param code
     * @param state
     * @return
     */

    @Autowired
    private UcenterMemberService memberService;

    @GetMapping("callback")
    public String callback(String code, String state) {
        try {
            //得到授权临时票据code
            System.out.println(code);
            System.out.println(state);

            //从redis中将state获取出来，和当前传入的state作比较
            //如果一致则放行，如果不一致则抛出异常：非法访问

            //向认证服务器发送请求换取access_token
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            String accessTokenUrl = String.format(baseAccessTokenUrl,
                    ConstantPropertiesUtil.WX_OPEN_APP_ID,
                    ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                    code);

            String result = null;
            try {
                result = HttpClientUtils.get(accessTokenUrl);
                System.out.println("accessToken=============" + result);
            } catch (Exception e) {
                throw new GuliException(20001, "获取access_token失败");
            }

            //解析json字符串
            Gson gson = new Gson();
            HashMap map = gson.fromJson(result, HashMap.class);
            String accessToken = (String) map.get("access_token");
            String openid = (String) map.get("openid");

            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
            String userInfo = HttpClientUtils.get(userInfoUrl);
            ;

            //解析json
            HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
            String nickname = (String) userInfoMap.get("nickname");
            String headimgurl = (String) userInfoMap.get("headimgurl");
            //查询数据库当前用用户是否曾经使用过微信登录
            UcenterMember member = memberService.getOpenIdMember(openid);
            if (member == null) {
                //访问微信的资源服务器，获取用户信息
                //向数据库中插入一条记录
                member = new UcenterMember();
                member.setNickname(nickname);
                member.setOpenid(openid);
                member.setAvatar(headimgurl);
                memberService.save(member);
            }

            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());

            return "redirect:http://119.29.169.252::3000?token=" + jwtToken;
        }catch (Exception e) {
            throw new GuliException(20001, "登陆失败");
        }
    }

    @GetMapping("login")
    public String getWxCode(HttpSession session) throws UnsupportedEncodingException {
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        redirectUrl= URLEncoder.encode(redirectUrl, "UTF-8");


        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                "atguigu"
        );
        //重定向到请求的 wx 地址
        return "redirect:" + qrcodeUrl;
    }
}
