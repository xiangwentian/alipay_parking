package com.alipay.parking.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class baseController {
    
    private static final Logger logger = LoggerFactory.getLogger("parking");// 信息日志
    
    /**
     * 处理页面传入参数
     * @param request
     * @return Map<String, String>:页面传参的集合
     */
    public Map<String, String> getParametersFromPage(HttpServletRequest request) {
        Map<String, String> param = new HashMap<String, String>();
        String varName = "default";
        String varValue = null;
        StringBuilder log = new StringBuilder("参数打印:");
        Enumeration e = request.getParameterNames();
        while (e.hasMoreElements()) { // 循环获取参数
            Object obj = e.nextElement();
            varName = obj.toString();
            varValue = request.getParameter(varName);
            param.put(varName, varValue);
            log.append(varName).append("=").append(varValue);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(log.toString());
        }
        return param;
    }
}
