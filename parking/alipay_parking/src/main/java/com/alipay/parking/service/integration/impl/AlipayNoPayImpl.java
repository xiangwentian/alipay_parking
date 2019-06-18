package com.alipay.parking.service.integration.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayEcoMycarParkingAgreementQueryRequest;
import com.alipay.api.request.AlipayEcoMycarParkingOrderPayRequest;
import com.alipay.api.request.AlipayEcoMycarParkingOrderRefundRequest;
import com.alipay.api.request.AlipayEcoMycarTradeOrderQueryRequest;
import com.alipay.api.response.AlipayEcoMycarParkingAgreementQueryResponse;
import com.alipay.api.response.AlipayEcoMycarParkingOrderPayResponse;
import com.alipay.api.response.AlipayEcoMycarParkingOrderRefundResponse;
import com.alipay.api.response.AlipayEcoMycarTradeOrderQueryResponse;
import com.alipay.parking.common.AliPayUtil;
import com.alipay.parking.service.integration.IAlipayNoPay;

/**
 * @author liuzhuang
 * @Description:代扣接口实现类
 * @date 2019年6月18日
 */
@Service
public class AlipayNoPayImpl implements IAlipayNoPay {

	@Resource(name = "aliPayUtil")
	private AliPayUtil aliPayUtil;

	public String carAgreementQuery(String carNumber) throws Exception {
		AlipayClient alipayClient = aliPayUtil.getInstance();
		AlipayEcoMycarParkingAgreementQueryRequest request = new AlipayEcoMycarParkingAgreementQueryRequest();
		Map<String, String> param = new HashMap<String, String>();
		param.put("car_number", carNumber);
		request.setBizContent(JSON.toJSONString(param));

		AlipayEcoMycarParkingAgreementQueryResponse response = alipayClient.execute(request);
		if (response.isSuccess()) {
			System.out.println("调用成功");
			// TODO
			// 获取相应数据
			Map<String, String> responseParams = response.getParams();
			return responseParams.get("agreement_status");// 这里只是提供一个思路具体可以自行定义处理
		} else {
			System.out.println("调用失败");
			// TODO
			return "invoke error";// 这里只是提供一个思路具体可以自行定义处理
		}
	}

	public String parkingOrderPay(Map<String, String> params) throws Exception {
		AlipayClient alipayClient = aliPayUtil.getInstance();
		AlipayEcoMycarParkingOrderPayRequest request = new AlipayEcoMycarParkingOrderPayRequest();
		Map<String, String> param = new HashMap<String, String>();
		// 建议传递的参数传递时做一下转换，避免业务数据问题，逻辑可自行优化
		param.put("car_number", params.get("car_number"));// 车牌号,如：浙A4B94B
		param.put("out_trade_no", params.get("out_trade_no"));// 支付宝合作商户网站唯一订单号，如:12345
		param.put("subject", params.get("subject"));// 订单标题，描述订单用途，如:黄龙国际停车场代扣缴费
		param.put("total_fee", params.get("total_fee"));// 订单金额，精确到小数点后两位，如：15.5
		// param.put("seller_logon_id",
		// params.get("seller_logon_id"));//卖家支付宝账号：email或手机，非必要信息
		param.put("seller_id", params.get("seller_id"));// 卖家支付宝用户号卖家支付宝账号对应的支付宝唯一用户号。以2088开头的纯16位数
		param.put("parking_id", params.get("parking_id"));// 支付宝停车平台ID
		param.put("out_parking_id", params.get("out_parking_id"));// ISV停车场ID
		param.put("agent_id", params.get("agent_id"));// 代扣返佣的支付宝用户号。以2088开头的纯16位数。注：返佣账号和seller_id收款账号不能为同一个
		param.put("car_number_color", params.get("car_number_color"));// 车牌颜色，平台目前只支持蓝牌、绿牌
		param.put("body", params.get("body"));// 订单描述
		request.setBizContent(JSON.toJSONString(param));

		AlipayEcoMycarParkingOrderPayResponse response = alipayClient.execute(request);
		if (response.isSuccess()) {
			System.out.println("调用成功");
			// 获取相应数据
			Map<String, String> responseParams = response.getParams();
			return responseParams.get("trade_no");// 当交易不明确时是拿不到这个交易号的，需要再进行查询
		} else {
			System.out.println("调用失败");
			return "invoke error";// 这里只是提供一个思路具体可以自行定义处理
		}
	}

	public String parkingOrderQuery(Map<String, String> params) throws Exception {
		AlipayClient alipayClient = aliPayUtil.getInstance();
		AlipayEcoMycarTradeOrderQueryRequest request = new AlipayEcoMycarTradeOrderQueryRequest();
		Map<String, String> param = new HashMap<String, String>();
		param.put("out_biz_trade_no", params.get("out_trade_no"));
		request.setBizContent(JSON.toJSONString(param));
		AlipayEcoMycarTradeOrderQueryResponse response = alipayClient.execute(request);
		if (response.isSuccess()) {
			System.out.println("调用成功");
			Map<String, String> responseParams = response.getParams();
			return responseParams.get("trade_status");
			// 注：当状态不明确是扣款成功还是失败时，要调用查询代扣接口来确认一下最终状态，建议不要立即查询，延迟几秒，也可以把失败订单放到定时任务或消息中去确认
		} else {
			System.out.println("调用失败");
			return "invoke error";// 这里只是提供一个思路具体可以自行定义处理
		}
	}

	public String parkingOrderRefund(Map<String, String> params) throws Exception {
		AlipayClient alipayClient = aliPayUtil.getInstance();
		AlipayEcoMycarParkingOrderRefundRequest request = new AlipayEcoMycarParkingOrderRefundRequest();
		Map<String, String> param = new HashMap<String, String>();
		param.put("order_no", params.get("order_no"));// 支付宝交易号
		param.put("out_order_no", params.get("out_order_no"));// isv交易订单号
		param.put("out_refund_no", params.get("out_refund_no"));// isv退款流水号
		param.put("refund_fee", params.get("refund_fee"));// 退款金额
		param.put("refund_reason", params.get("refund_reason"));// 退款原因
		request.setBizContent(JSON.toJSONString(param));

		AlipayEcoMycarParkingOrderRefundResponse response = alipayClient.execute(request);
		if (response.isSuccess()) {
			System.out.println("调用成功");
			Map<String, String> responseParams = response.getParams();
			return responseParams.get("refund_no");// 支付宝退款流水
		} else {
			System.out.println("调用失败");
			return "invoke error";// 这里只是提供一个思路具体可以自行定义处理
		}
	}

}
