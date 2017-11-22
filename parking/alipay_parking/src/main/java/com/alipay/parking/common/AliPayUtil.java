package com.alipay.parking.common;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

/**
 * 实例化网关
 * @author liuzhuang
 * 使用场景说明：网关做成公共实例化
 */
@Component
public class AliPayUtil {
    @Resource(name = "propertiesUtil")
    private PropertiesUtil propertiesUtil;// 红包活动

    public AlipayClient getInstance() {
        AlipayClient alipayClient = null;
        try {
            alipayClient = new DefaultAlipayClient(propertiesUtil.readValue("alipay.gateway.url"),
                propertiesUtil.readValue("alipay.app_id"),
                propertiesUtil.readValue("alipay.app.private.key"),
                propertiesUtil.readValue("alipay.format"),
                propertiesUtil.readValue("alipay.charset"),
                propertiesUtil.readValue("alipay.public.key"));
            return alipayClient;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return alipayClient;
    }
}
