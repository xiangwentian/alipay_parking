package com.alipay.parking.service.integration;

import java.util.Map;

/**
 * 接入停车平台流程接口
 * @author liuzhuang
 * 使用场景说明：
 */
public interface IAlipayParking {

    /**
     * 车辆入场
     * @param params
     * @return
     */
    public String carEnterInfo(Map<String, String> params);

    /**
     * 车牌出场
     * @param params
     * @return
     */
    public String carExitInfo(Map<String, String> params);

    /**
     * 车牌查询
     * @param params
     * @return
     */
    public String vehicleQuery(Map<String, String> params);

    /**
     * 订单同步
     * @param params
     * @return
     */
    public String orderSync(Map<String, String> params);

    /**
     * 订单更新
     * @param params
     * @return
     */
    public String orderUpdate(Map<String, String> params);
}
