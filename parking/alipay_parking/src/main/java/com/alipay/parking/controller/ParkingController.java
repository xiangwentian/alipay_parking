package com.alipay.parking.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.parking.service.integration.IAlipayNoPay;
import com.alipay.parking.service.integration.IAlipayParking;

/**
 * 车辆出入场逻辑
 * 
 * @author liuzhuang 使用场景说明：
 */
@Controller
@RequestMapping("/parking/pages")
public class ParkingController extends baseController {
	private static final Logger logger = LoggerFactory.getLogger("parking");// 信息日志

	@Resource
	private IAlipayParking iAlipayParking; // 停车缴费逻辑业务处理

	@Resource
	private IAlipayNoPay iAlipayNopay;// 代扣逻辑

	@RequestMapping("/toIndex")
	public String toIndex(HttpServletRequest request, HttpServletResponse response) {
		return "index";
	}

	/**
	 * 入场
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/carEnterInfo")
	public @ResponseBody String carEnterInfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = super.getParametersFromPage(request);
		String isSuccess = iAlipayParking.carEnterInfo(params);
		return isSuccess;
	}

	/**
	 * 出场
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/carExitInfo")
	public @ResponseBody String carExitInfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = super.getParametersFromPage(request);
		String isSuccess = iAlipayParking.carExitInfo(params);
		return isSuccess;
	}

	/**
	 * 车牌查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/vehicleQuery")
	public @ResponseBody String vehicleQuery(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = super.getParametersFromPage(request);
		String isSuccess = iAlipayParking.vehicleQuery(params);
		return isSuccess;
	}

	/**
	 * 订单同步
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/orderSync")
	public @ResponseBody String orderSync(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = super.getParametersFromPage(request);
		String isSuccess = iAlipayParking.orderSync(params);
		return isSuccess;
	}

	/**
	 * 订单更新
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/orderUpdate")
	public @ResponseBody String orderUpdate(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = super.getParametersFromPage(request);
		String isSuccess = iAlipayParking.orderUpdate(params);
		return isSuccess;
	}

	/**
	 * @Description:订单退款，可以提供页面一个可视化的请求处理接口
	 * @date 2019年6月18日
	 * @param request
	 * @param response
	 * @return 可以返回一个处理结果标识或其它json串，返回结果按业务需求自行处理
	 */
	public @ResponseBody String OrderRefund(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = super.getParametersFromPage(request);
		String result = iAlipayNopay.parkingOrderRefund(params);
		return null;
	}

}
