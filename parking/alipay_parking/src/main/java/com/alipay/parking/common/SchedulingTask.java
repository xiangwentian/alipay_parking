package com.alipay.parking.common;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author liuzhuang
 * @Description:定时任务
 * @背景：对接的过程中有isv的同学不知道怎么写轮询方法
 * @应用场景：对代扣结果未知的订单做一个定时任务，去查询订单是否成功扣款，如果扣款成功并且线下支付过的，需要同步订单，然后把代扣的钱退回用户
 * @date 2019年6月18日
 */
//@Configuration
//@EnableScheduling
public class SchedulingTask {

	/**
	 * @Description:查询订单结果，处理相关逻辑
	 * @date 2019年6月18日
	 */
//	public void queryOrderResult() {
//		TimerTask task = new TimerTask() {
//			@Override
//			public void run() {
//				// TODO
//				// 对于失败的订单可以定期的执行
//				// 可以把结果未知的订单放到一个列表中，定时执行一下，先判断列表不为空时去查询结果，当结果明确时调用订单同步接口把结果同步到停车平台，展示给用户记录信息
//			}
//		};
//		Timer timer = new Timer();
//		long delay = 0;// 任务执行前的毫秒延迟
//		long intevalPeriod = 1 * 1000;// 执行间隔时间
//		// schedules the task to be run in an interval
//		// task待执行的任务
//		timer.scheduleAtFixedRate(task, delay, intevalPeriod);
//	}
}
