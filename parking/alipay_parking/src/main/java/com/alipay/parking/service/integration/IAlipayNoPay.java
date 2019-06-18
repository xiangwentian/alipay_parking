package com.alipay.parking.service.integration;

import java.util.Map;

/**
 * @author liuzhuang
 * @Description: 代扣业务逻辑
 * @date 2019年6月18日
 */
public interface IAlipayNoPay {

	/**
	 * @Description:查询车牌代扣情况
	 * @date 2019年6月18日
	 * @param carNumber车牌号
	 * @return 车牌代扣状态，0：为支持代扣，1：为不支持代扣
	 * @throws Exception
	 */
	public String carAgreementQuery(String carNumber) throws Exception;

	/**
	 * @Description:代扣接口
	 * @date 2019年6月18日
	 * @param params把参数封装成map放到集合中
	 * @return
	 */
	public String parkingOrderPay(Map<String, String> params) throws Exception;

	/**
	 * @Description:代扣状态查询接口
	 * @date 2019年6月18日
	 * @param params
	 *            目前只需要传外部订单号out_biz_trade_no，
	 *            对应代扣接口parkingOrderPay中的out_trade_no参数
	 * @return 返回的信息只需要一个状态值即可，没必要返回平台的一系列数据到上层逻辑
	 * @throws Exception
	 */
	public String parkingOrderQuery(Map<String, String> params) throws Exception;

	/**
	 * @Description:代扣退款接口
	 * @date 2019年6月18日
	 * @param params
	 *            map类型，key值如下 order_no支付宝订单号：代扣时返回的支付宝支付交易流水号，系统唯一 out_order_no
	 *            ISV代扣订单号，ISV唯一 out_refund_no 外部申请退款请求流水，ISV唯一
	 *            refund_fee退款金额，保留小数点后两位 refund_reason退款原因
	 * @return 只需要关系退款是否成功即可
	 * @throws Exception
	 */
	public String parkingOrderRefund(Map<String, String> params) throws Exception;

}
