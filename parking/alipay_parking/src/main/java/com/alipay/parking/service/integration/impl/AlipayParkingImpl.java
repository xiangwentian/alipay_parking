package com.alipay.parking.service.integration.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayEcoMycarParkingEnterinfoSyncRequest;
import com.alipay.api.request.AlipayEcoMycarParkingExitinfoSyncRequest;
import com.alipay.api.request.AlipayEcoMycarParkingOrderSyncRequest;
import com.alipay.api.request.AlipayEcoMycarParkingVehicleQueryRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipayEcoMycarParkingEnterinfoSyncResponse;
import com.alipay.api.response.AlipayEcoMycarParkingExitinfoSyncResponse;
import com.alipay.api.response.AlipayEcoMycarParkingOrderSyncResponse;
import com.alipay.api.response.AlipayEcoMycarParkingVehicleQueryResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.parking.common.AliPayUtil;
import com.alipay.parking.service.integration.IAlipayParking;

@Service
public class AlipayParkingImpl implements IAlipayParking {

	@Resource(name = "aliPayUtil")
	private AliPayUtil aliPayUtil;

	public String carEnterInfo(Map<String, String> params) {

		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		String invokeStatus = "";

		AlipayClient alipayClient = aliPayUtil.getInstance();

		try {
			// 实例化具体API对应的request类,类名称和接口名称对应,
			AlipayEcoMycarParkingEnterinfoSyncRequest request = new AlipayEcoMycarParkingEnterinfoSyncRequest();
			// SDK已经封装掉了公共参数，这里只需要传入业务参数
			Map<String, String> param = new HashMap<String, String>();// 组织json串
			// 注:非必填项可用双绰号""代替
			param.put("parking_id", params.get("parking_id"));// 停车场id
			param.put("car_number", params.get("car_number"));// 车牌号
			param.put("in_time", sf.format(date));// 上送车辆的时间，格式"yyyy-MM-dd  HH:mm:ss"

			// 参数需要再填写
			System.out.println("组织的参数json形式：" + JSONObject.toJSONString(param));
			request.setBizContent(JSONObject.toJSONString(param));

			AlipayEcoMycarParkingEnterinfoSyncResponse response;
			response = alipayClient.execute(request);

			System.out.println("response:" + JSONObject.toJSONString(response));

			// stauts 返回状态：1为成功，0为失败
			if (response.isSuccess()) {
				Map<String, String> responseParams = response.getParams();
				System.out.println("调用成功:" + JSONObject.toJSONString(responseParams));
				invokeStatus = JSONObject.toJSONString(responseParams);
			} else {
				System.out.println("调用失败");
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return invokeStatus;
	}

	public String carExitInfo(Map<String, String> params) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		try {
			AlipayClient alipayClient = aliPayUtil.getInstance();
			// 实例化具体API对应的request类,类名称和接口名称对应,
			// alipay.eco.mycar.parking.exitinfo.sync
			AlipayEcoMycarParkingExitinfoSyncRequest request = new AlipayEcoMycarParkingExitinfoSyncRequest();
			// SDK已经封装掉了公共参数，这里只需要传入业务参数
			Map<String, String> param = new HashMap<String, String>();// 组织json串
			// 注:非必填项可用双绰号""代替
			param.put("parking_id", params.get("parking_id"));// 停车场id
			param.put("car_number", params.get("car_number"));// 车牌号
			param.put("out_time", sf.format(date));// 上送车辆的时间，格式"yyyy-MM-dd HH:mm:ss"

			// 参数需要再填写
			System.out.println("组织的参数json形式：" + JSONObject.toJSONString(param));
			request.setBizContent(JSONObject.toJSONString(param));

			AlipayEcoMycarParkingExitinfoSyncResponse response;
			response = alipayClient.execute(request);
			System.out.println("AlipayEcoMycarParkingExitinfoSyncResponse：" + JSONObject.toJSONString(response));
			// stauts 返回状态：1为成功，0为失败
			if (response.isSuccess()) {
				Map<String, String> responseParams = response.getParams();
				System.out.println("调用成功:" + JSONObject.toJSONString(responseParams));
			} else {
				System.out.println("调用失败");
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String vehicleQuery(Map<String, String> params) {
		try {
			// 实例化客户端
			AlipayClient alipayClient = aliPayUtil.getInstance();
			AlipayEcoMycarParkingVehicleQueryRequest request = new AlipayEcoMycarParkingVehicleQueryRequest();
			// SDK已经封装掉了公共参数，这里只需要传入业务参数
			// 此次只是参数展示，未进行字符串转义，实际情况下请转义
			Map<String, String> param = new HashMap<String, String>();
			param.put("car_id", params.get("car_id"));

			System.out.println(JSONObject.toJSONString(param));
			AlipaySystemOauthTokenRequest alipaySystemOauthTokenRequest = new AlipaySystemOauthTokenRequest();
			// 授权设置
			alipaySystemOauthTokenRequest.setGrantType("authorization_code");
			// auth_code设置
			alipaySystemOauthTokenRequest.setCode(params.get("authCode"));
			// 换取调用
			AlipaySystemOauthTokenResponse response;
			response = alipayClient.execute(alipaySystemOauthTokenRequest);

			System.out.println(JSONObject.toJSONString("alipayClient.execute result:" + response));

			System.out.println(JSONObject.toJSONString("response.isSuccess():" + response.isSuccess()));

			if (response.isSuccess()) {
				// 调用成功
				String uid = response.getUserId();
				System.out.println("uid:" + uid);
				// 取得令牌
				String access_token = response.getAccessToken();

				System.out.println("access_token:" + access_token);

				request.setBizContent(JSONObject.toJSONString(param));

				AlipayEcoMycarParkingVehicleQueryResponse queryResponse = alipayClient.execute(request, access_token);

				if (queryResponse.isSuccess()) {
					System.out.println("调用成功");
				} else {
					System.out.println("调用失败");
				}
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String orderSync(Map<String, String> params) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		try {
			AlipayClient alipayClient = aliPayUtil.getInstance();

			// 实例化具体API对应的request类,类名称和接口名称对应,
			AlipayEcoMycarParkingOrderSyncRequest request = new AlipayEcoMycarParkingOrderSyncRequest();

			// SDK已经封装掉了公共参数，这里只需要传入业务参数
			Map<String, String> param = new HashMap<String, String>();// 组织json串
			// 注:非必填项可用双绰号""代替
			param.put("user_id", params.get(""));// userid
			param.put("out_parking_id", params.get(""));// userid
			param.put("parking_name", params.get(""));// 停车场名称
			param.put("car_number", params.get(""));// 车牌号
			param.put("out_order_no", params.get(""));// 设备商订单号
			param.put("order_status", params.get(""));// 设备商订单状态，0代表缴费成功1代表退款
			param.put("order_time", params.get(""));// 上送车辆的时间，格式"yyyy-MM-dd
													// HH:mm:ss"
			param.put("order_no", params.get(""));// 支付流水号
			param.put("pay_time", params.get(""));// 付款时间
			param.put("pay_type", "1");// 付款方式，1：支付宝在线缴费
			param.put("pay_money", params.get(""));// 缴费金额
			param.put("in_time", params.get(""));// 入场时间
			param.put("parking_id", params.get(""));// 停车场id
			param.put("in_duration", params.get(""));// 停车时长（以分为单位）
			param.put("card_number", "");// 如果是扫停车卡，填入停车卡卡号

			// 参数需要再填写
			System.out.println("组织的参数json形式：" + JSONObject.toJSONString(param));
			request.setBizContent(JSONObject.toJSONString(param));

			AlipayEcoMycarParkingOrderSyncResponse response = alipayClient.execute(request);

			System.out.println("response:" + JSONObject.toJSONString(response));

			// stauts 返回状态：1为成功，0为失败
			if (response.isSuccess()) {
				Map<String, String> responseParams = response.getParams();
				System.out.println("调用成功:" + JSONObject.toJSONString(responseParams));
			} else {
				System.out.println("调用失败");
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String orderUpdate(Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}

}
