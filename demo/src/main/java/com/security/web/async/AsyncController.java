package com.security.web.async;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import io.swagger.annotations.Api;

/**
 * 线程异步处理rest请求
 * @author Administrator
 *
 */
@Slf4j
@RestController
@Api(tags = "异步处理请求")
public class AsyncController {

	@Autowired
	private MockQueue mockQueue;
	
	@Autowired
	private DeferredResultHolder deferredResultHolder;
	/**
	 * Runnable由主线程调起才能使用，不如DeferredResult异步处理Rest服务
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/order")
	public Callable<String> order() throws Exception {
		logger.info("主线程开始");
		// 在spring管理的线程中单开一个线程
		Callable<String> result = new Callable<String>() {
			@Override
			public String call() throws Exception {
				logger.info("副线程开始");
				Thread.sleep(1000);
				logger.info("副线程结束");
				return "success";
			}
		};
		logger.info("主线程结束");
		return result;
	}*/
	
	/**
	 * Deferred实现下订单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/order")
	public DeferredResult<String> order() throws Exception {
		log.info("主线程开始");
		
		String orderNum = RandomStringUtils.randomNumeric(8);
		mockQueue.setPlaceOrder(orderNum);
		
		DeferredResult<String> result = new DeferredResult<>();
		deferredResultHolder.getMap().put(orderNum, result);
		log.info("主线程结束");
		return result;
	}
}
