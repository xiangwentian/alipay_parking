package com.alipay.parking.test;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author liuzhuang
 * @Description:各种定时实现方式验证;
 * @背景：对接的过程中有isv的同学不知道怎么写轮询方法
 * @应用场景：对代扣结果未知的订单做一个定时任务，去查询订单是否成功扣款，如果扣款成功并且线下支付过的，需要同步订单，然后把代扣的钱退回用户
 * @date 2019年6月18日
 */
public class SchedullingTaskTest {

	volatile int m = 5;

	public void test() {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				System.out.println("timer task!!!");
				m--;
			}
		};
		Timer timer = new Timer();
		long delay = 0;// 500毫秒后启动
		long period = 1000;// 1秒
		timer.scheduleAtFixedRate(task, delay, period);
		while (m == 0) {
			// TODO这里处理逻辑
			timer.cancel();
		}

	}

	public static void test1() {
		Runnable runnable = new Runnable() {
			public void run() {
				System.out.println("Hello !!");
			}
		};
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
		service.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
	}

	public static void test2() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				System.out.println("11232");
			}
		}, 0, 1000);
	}

	public static void main(String[] args) {
		SchedullingTaskTest st = new SchedullingTaskTest();
		st.test();

	}
}
