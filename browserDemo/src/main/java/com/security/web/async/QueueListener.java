package com.security.web.async;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * ContextRefreshedEvent ： spring整个容器初始化完成事件
 * @author Administrator
 *
 */
@Slf4j
public class QueueListener implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	private MockQueue mockQueue;

	@Autowired
	private DeferredResultHolder deferredResultHolder;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		new Thread(() -> {
			while (true) {
				if(StringUtils.isNotBlank(mockQueue.getCompleteOrder())) {
					
					String orderNum = mockQueue.getCompleteOrder();
					log.info("返回订单处理结果: {}" , orderNum);
					deferredResultHolder.getMap().get(orderNum).setResult("place order success");
					
					mockQueue.setCompleteOrder(null);
				} else {
					//如果还有正在处理的订单
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		
	}
	
	
}
