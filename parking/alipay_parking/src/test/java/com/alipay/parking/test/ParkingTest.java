package com.alipay.parking.test;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.alipay.parking.service.integration.IAlipayParking;

/**
 * @author liuzhuang
 * 使用场景说明：入场、出场、车牌查询、订单同步、订单更新
 */
public class ParkingTest extends BaseTest {

    @Resource
    private IAlipayParking iAlipayParking; // 逻辑业务处理

    /**
     * 车辆入场Test
     */
    @Test
    public void carIn() {
        Map<String, String> params = new HashMap<String, String>();

        params.put("parking_id", "PI1486347449056133245");
        params.put("car_number", "冀B851LP");

        iAlipayParking.carEnterInfo(params);
    }

    /**
     * 车辆出场Test
     */
    public void carOut() {
        Map<String, String> params = new HashMap<String, String>();

        params.put("parking_id", "PI1486347449056133245");
        params.put("car_number", "冀B851LP");

        iAlipayParking.carExitInfo(params);
    }

    /**
     * 车牌查询Test
     */
    public void vehicleQuery() {
        Map<String, String> params = new HashMap<String, String>();

        params.put("car_id", "");
        params.put("authCode", "");

        iAlipayParking.vehicleQuery(params);
    }

    /**
     * 订单同步Test
     */
    public void orderSync() {
        Map<String, String> params = new HashMap<String, String>();

        //注:非必填项可用双绰号""代替
        params.put("user_id", "");//userid
        params.put("out_parking_id", "");//userid
        params.put("parking_name", "");//停车场名称
        params.put("car_number", "");//车牌号
        params.put("out_order_no", "");//设备商订单号
        params.put("order_status", "");//设备商订单状态，0代表缴费成功1代表退款
        params.put("order_time", "");//上送车辆的时间，格式"yyyy-MM-dd HH:mm:ss"
        params.put("order_no", "");//支付流水号
        params.put("pay_time", "");//付款时间
        params.put("pay_type", "1");//付款方式，1：支付宝在线缴费
        params.put("pay_money", "");//缴费金额
        params.put("in_time", "");//入场时间
        params.put("parking_id", "");//停车场id
        params.put("in_duration", "");//停车时长（以分为单位）
        params.put("card_number", "");//如果是扫停车卡，填入停车卡卡号

        iAlipayParking.orderSync(params);
    }

    /**
     * 订单更新Test
     */
    public void orderUpdate() {
        Map<String, String> params = new HashMap<String, String>();

        //注:非必填项可用双绰号""代替
        //        params.put("user_id", "");//userid
        //        params.put("out_parking_id", "");//userid
        //        params.put("parking_name", "");//停车场名称
        //        params.put("car_number", "");//车牌号
        //        params.put("out_order_no", "");//设备商订单号
        //        params.put("order_status", "");//设备商订单状态，0代表缴费成功1代表退款
        //        params.put("order_time", "");//上送车辆的时间，格式"yyyy-MM-dd HH:mm:ss"
        //        params.put("order_no", "");//支付流水号
        //        params.put("pay_time", "");//付款时间
        //        params.put("pay_type", "1");//付款方式，1：支付宝在线缴费
        //        params.put("pay_money", "");//缴费金额
        //        params.put("in_time", "");//入场时间
        //        params.put("parking_id", "");//停车场id
        //        params.put("in_duration", "");//停车时长（以分为单位）
        //        params.put("card_number", "");//如果是扫停车卡，填入停车卡卡号
        iAlipayParking.orderUpdate(params);
    }
}
