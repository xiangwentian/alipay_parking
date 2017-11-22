package com.alipay.parking.service.integration.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayEcoMycarParkingConfigQueryRequest;
import com.alipay.api.request.AlipayEcoMycarParkingConfigSetRequest;
import com.alipay.api.request.AlipayEcoMycarParkingParkinglotinfoCreateRequest;
import com.alipay.api.request.AlipayEcoMycarParkingParkinglotinfoUpdateRequest;
import com.alipay.api.response.AlipayEcoMycarParkingConfigQueryResponse;
import com.alipay.api.response.AlipayEcoMycarParkingConfigSetResponse;
import com.alipay.api.response.AlipayEcoMycarParkingParkinglotinfoCreateResponse;
import com.alipay.api.response.AlipayEcoMycarParkingParkinglotinfoUpdateResponse;
import com.alipay.parking.common.AliPayUtil;
import com.alipay.parking.service.integration.IAlipayConfig;

@Service
public class AlipayConfigImpl implements IAlipayConfig {

	@Resource(name = "aliPayUtil")
	private AliPayUtil aliPayUtil;

	public String configSet(Map<String, String> params) {
		String isSuccess = "false";
		try {
			AlipayClient alipayClient = aliPayUtil.getInstance();
			AlipayEcoMycarParkingConfigSetRequest request = new AlipayEcoMycarParkingConfigSetRequest();
			// SDK已经封装掉了公共参数，这里只需要传入业务参数
			// 此次只是参数展示，未进行字符串转义，实际情况下请转义
			request.setBizContent(processParams(params, "1"));// 业务数据
			AlipayEcoMycarParkingConfigSetResponse response = alipayClient.execute(request);
			// 判断调用是否成功
			if (response.isSuccess()) {
				// 获取相应数据
				Map<String, String> responseParams = response.getParams();
				// 通过返回数据进行业务处理，可以通过responseParams获取到返回的键值数据
				isSuccess = "true";
			} else {
				// 调用失败处理逻辑
			}

		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return isSuccess;
	}

	private String processParams(Map<String, String> param, String type) {

		String map2Json = "";

		if ("1".equals(type)) {// config.set商户信息配置请求参数
			Map<String, String> params = new HashMap<String, String>();
			params.put("merchant_name", param.get("merchant_name"));
			params.put("merchant_service_phone", param.get("merchant_service_phone"));
			params.put("account_no", param.get("account_no"));
			params.put("merchant_logo", param.get("merchant_logo"));
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			Map<String, String> arrMap = new HashMap<String, String>();
			arrMap.put("interface_name", "alipay.eco.mycar.parking.userpage.query");
			arrMap.put("interface_type", "interface_page");
			arrMap.put("interface_url", param.get("interface_url"));// 链接转码
			list.add(arrMap);

			params.put("interface_info_list", JSON.toJSONString(list));

			map2Json = JSON.toJSONString(params);
		} else if ("2".equals(type)) {// config.query查询商户信息请求参数
			Map<String, String> params = new HashMap<String, String>();
			params.put("interface_name", "alipay.eco.mycar.parking.userpage.query");
			params.put("interface_type", "interface_page");

			map2Json = JSON.toJSONString(params);
		} else if ("3".equals(type)) {
			Map<String, String> params = new HashMap<String, String>();// 组织json串
			// 注:非必填项可用双绰号""代替
			params.put("city_id", param.get("city_id"));// 城市编号（国家统一标准编码）110101
			params.put("equipment_name", param.get("equipment_name"));// isv名称 杭州立方  非必填
			params.put("out_parking_id", param.get("out_parking_id"));// 全局唯一的停车场编号(由设备商系统提供)  10000110001
			params.put("parking_address", param.get("parking_address"));// 停车场地址
			params.put("longitude", param.get("longitude"));// 经度，最长15位字符（包括小数点），注：高德坐标系  114.266418 非必填
			params.put("latitude", param.get("latitude"));// 纬度，最长15位字符（包括小数点），注：高德坐标系   30.548828 非必填
			params.put("parking_start_time", param.get("parking_start_time"));// 营业时间  07:00:00
			params.put("parking_end_time", param.get("parking_end_time"));// 结束时间  03:07:50
			params.put("parking_number", param.get("parking_number"));// 停车位数目  100
			params.put("parking_lot_type", param.get("parking_lot_type"));// 停车场类型，1为小区停车场、2为商圈停车场、3为路面停车场、4为园区停车场、5为写字楼停车场、6为私人停车场
			params.put("parking_type", param.get("parking_type"));// 停车场类型(1为地面，2为地下，3为路边)（多个类型，中间用,隔开
			params.put("payment_mode", param.get("payment_mode"));// 缴费模式（1为停车卡缴费，2为物料缴费，3为中央缴费机）
			params.put("pay_type", param.get("pay_type"));// 支付方式（1为支付宝在线缴费，2为支付宝代扣缴费
			params.put("shopingmall_id", "");// 商圈id 100001008 非必填
			params.put("parking_fee_description", param.get("parking_fee_description"));// 收费说明  小车一小时10元  非必填
			params.put("contact_name", "");// 联系人 非必填
			params.put("contact_mobile", "");// 手机 非必填
			params.put("contact_tel", "");// 座机 非必填
			params.put("contact_email", "");// 邮箱 非必填
			params.put("contact_weixin", "");// 微信 非必填
			params.put("contact_alipay", "");// 联系人支付宝账号 非必填
			params.put("parking_name", param.get("parking_name"));// 停车场名称
			params.put("time_out", "");// 支付超时时间 非必填
		} else {
			// 修改停车场
			// TODO
		}

		return map2Json;
	}

	public String configQuery(Map<String, String> params) {
		String returnMsg = "";
		try {
			AlipayClient alipayClient = aliPayUtil.getInstance();
			// 实例化具体API对应的request类,类名称和接口名称对应,
			AlipayEcoMycarParkingConfigQueryRequest request = new AlipayEcoMycarParkingConfigQueryRequest();
			// SDK已经封装掉了公共参数，这里只需要传入业务参数
			// 此次只是参数展示，未进行字符串转义，实际情况下请转义
			request.setBizContent(processParams(null, "2"));// 业务数据
			AlipayEcoMycarParkingConfigQueryResponse response = alipayClient.execute(request);
			// 判断调用是否成功
			if (response.isSuccess()) {
				// 获取相应数据
				Map<String, String> responseParams = response.getParams();
				// 通过返回数据进行业务处理，可以通过responseParams获取到返回的键值数据
				returnMsg = JSON.toJSONString(responseParams);
			} else {
				// 调用失败处理逻辑
			}
		} catch (AlipayApiException e) {
			// 调用异常逻辑处理
			e.printStackTrace();
		}
		return returnMsg;
	}

	public String parkingCreate(Map<String, String> params) {
		String returnMsg = "";
		try {
			AlipayClient alipayClient = aliPayUtil.getInstance();
			// 实例化具体API对应的request类,类名称和接口名称对应,
			AlipayEcoMycarParkingParkinglotinfoCreateRequest request = new AlipayEcoMycarParkingParkinglotinfoCreateRequest();
			// SDK已经封装掉了公共参数，这里只需要传入业务参数
			// 此次只是参数展示，未进行字符串转义，实际情况下请转义
			request.setBizContent(processParams(params, "3"));// 业务数据
			AlipayEcoMycarParkingParkinglotinfoCreateResponse response = alipayClient.execute(request);
			// 判断调用是否成功
			if (response.isSuccess()) {
				// 获取相应数据
				Map<String, String> responseParams = response.getParams();
				// 通过返回数据进行业务处理，可以通过responseParams获取到返回的键值数据
				returnMsg = JSON.toJSONString(responseParams);
			} else {
				// 调用失败处理逻辑
			}
		} catch (AlipayApiException e) {
			// 调用异常逻辑处理
			e.printStackTrace();
		}
		return returnMsg;
	}

	public String parkingUpdate(Map<String, String> params) {
		String isSuccess = "false";
		try {
			AlipayClient alipayClient = aliPayUtil.getInstance();

			AlipayEcoMycarParkingParkinglotinfoUpdateRequest request = new AlipayEcoMycarParkingParkinglotinfoUpdateRequest();
			// SDK已经封装掉了公共参数，这里只需要传入业务参数

			request.setBizContent(JSONObject.toJSONString(processParams(params, "4")));

			AlipayEcoMycarParkingParkinglotinfoUpdateResponse response = alipayClient.execute(request);

			if (response.isSuccess()) {
				System.out.println("调用成功");
				isSuccess = "success";
			} else {
				System.out.println("调用失败");
			}
		} catch (AlipayApiException e) {
			// 调用异常逻辑处理
			e.printStackTrace();
		}
		return isSuccess;
	}

}
