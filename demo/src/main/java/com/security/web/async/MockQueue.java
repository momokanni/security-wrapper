package com.security.web.async;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 模拟消息队列
 * @author Administrator
 *
 */
@Slf4j
@Data
@Component
public class MockQueue {
	
	private String placeOrder;
	
	private String completeOrder;

	public String getPlaceOrder() {
		return placeOrder;
	}

	public void setPlaceOrder(String placeOrder) throws Exception {
		new Thread(() -> {
			log.info("接到下单请求: {}" , placeOrder);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.completeOrder = placeOrder;
			log.info("下单处理完毕：{}" , placeOrder);
		}).start();
	}
}
