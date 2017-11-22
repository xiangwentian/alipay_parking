package com.alipay.parking.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.parking.service.integration.IAlipayConfig;

/**
 * @author liuzhuang
 * 使用场景说明：商户信息配置
 */
@Controller
@RequestMapping("/parkingconfig")
public class ParkingConfigController extends baseController {

    @Resource
    private IAlipayConfig iAlipayConfig; // 对内数据

    /**
     * 商户信息配置
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/configset")
    public String configSet(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = super.getParametersFromPage(request);
        String isSuccess = iAlipayConfig.configSet(params);
        return isSuccess;
    }

    /**
     * 商户配置信息查询
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/configquery")
    public String configQuery(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = super.getParametersFromPage(request);
        String returnMsg = iAlipayConfig.configSet(params);
        return returnMsg;
    }
}
