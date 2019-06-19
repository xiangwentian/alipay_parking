package com.alipay.parking.test;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.alipay.parking.service.integration.IAlipayConfig;

/**
 * @author liuzhuang 使用场景说明：配置的Test，商户信息配置，商户信息查询、录入停车场、停车场修改
 */
public class ParkingConfigTest extends BaseTest {
	@Resource
	private IAlipayConfig iAlipayConfig;

	@Test
	public void configSet() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("merchant_name", "");
		params.put("merchant_service_phone", "");
		params.put("account_no", "");
		params.put("merchant_logo", "");
		params.put("interface_url", "");// 链接转码

		iAlipayConfig.configSet(params);
	}

	public void configQuery() {
		iAlipayConfig.configQuery(null);
	}

	/**
	 * 更新，创建停车场，去掉废弃代码
	 */
	public void parkingCreate() {
		Map<String, String> params = new HashMap<String, String>();// 组织json串
		// 注:非必填项可用双绰号""代替
		// params.put("city_id", "");//城市编号（国家统一标准编码）110101
		// params.put("equipment_name", "");//isv名称 杭州立方 非必填
		params.put("out_parking_id", "");// 全局唯一的停车场编号(由设备商系统提供) 10000110001
		params.put("parking_address", "");// 停车场地址
		// params.put("longitude", "");//经度，最长15位字符（包括小数点），注：高德坐标系 114.266418
		// 非必填
		// params.put("latitude", "");//纬度，最长15位字符（包括小数点），注：高德坐标系 30.548828 非必填
		// params.put("parking_start_time", "");//营业时间 07:00:00
		// params.put("parking_end_time", "");//结束时间 03:07:50
		// params.put("parking_number", "");//停车位数目 100
		params.put("parking_lot_type", "");// 停车场类型，1为小区停车场、2为商圈停车场、3为路面停车场、4为园区停车场、5为写字楼停车场、6为私人停车场
		// params.put("parking_type", "");//停车场类型(1为地面，2为地 下，3为路边)（多个类型，中间用,隔开
		params.put("parking_poiid", "");// 高德地图唯一标识
		params.put("parking_mobile", "");// 停车场客服电话 手机号
		// params.put("payment_mode", "");//缴费模式（1为停车卡缴费，2为物料缴费，3为中央缴费机）
		params.put("pay_type", "");// 支付方式（1为支付宝在线缴费，2为支付宝代扣缴费
		params.put("shopingmall_id", "");// 商圈id 100001008 非必填
		params.put("parking_fee_description", "");// 收费说明 小车一小时10元 非必填
		// params.put("contact_name", "");//联系人 非必填
		// params.put("contact_mobile", "");//手机 非必填
		// params.put("contact_tel", "");//座机 非必填
		// params.put("contact_email", "");//邮箱 非必填
		// params.put("contact_weixin", "");//微信 非必填
		// params.put("contact_alipay", "");//联系人支付宝账号 非必填
		params.put("parking_name", "");// 停车场名称
		params.put("time_out", "");// 支付超时时间 非必填

		iAlipayConfig.parkingCreate(params);
	}

	/**
	 * /** 更新，修改停车场，去掉废弃代码
	 */
	public void parkingUpdate() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("parking_id", "");
		params.put("out_parking_id", "");
		params.put("parking_address", "");
		params.put("parking_name", "");
		params.put("time_out", "");
		params.put("parking_lot_type", "");
		params.put("pay_type", "");
		params.put("parking_poiid", "");
		params.put("parking_mobile", "");
		params.put("shopingmall_id", "");
		params.put("parking_fee_description", "");

		iAlipayConfig.parkingUpdate(params);
	}
}
