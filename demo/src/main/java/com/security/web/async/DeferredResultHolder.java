package com.security.web.async;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * 介于  “接收请求线程” 和  “接收响应线程” 中间
 * 用来传递 DeferredResult 的对象 （类似于线程间对象共享）
 * 
 * 例： 下订单
 * 	  1、 请求经由 应用1的 "线程1"，插入到消息队列。（"线程1"释放，继续接收新的请求）
 * 	  2、应用2监听并处理消息。
 *    3、应用2处理完将回执给消息队列
 *    4、应用1的 "线程2"监听线程消息处理结果，并返回响应
 * @author Administrator
 *
 */
@Component
public class DeferredResultHolder {

	/**
	 * key : value == 订单号 : 处理结果
	 */
	@Getter
	@Setter
	private Map<String, DeferredResult<String>> map = new HashMap<String, DeferredResult<String>>();

	
	
}
