package com.security.permission.init;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * @Description 系统初始化器
 * @Author sca
 * @Date 2019-08-19 11:41
 **/
@Slf4j
@Component
public class SystemDataInitializer implements ApplicationListener<ContextRefreshedEvent> {
	
	/**
	 * 系统中所有的{@link DataInitializer}接口实现
	 */
	@Autowired(required = false)
	private List<DataInitializer> dataInitializers;
	
	/**
	 * 循环调用系统中所有的{@link DataInitializer}
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		if(CollectionUtils.isNotEmpty(dataInitializers)){
			
			dataInitializers.sort((initor1, initor2) -> {
				return initor1.getIndex().compareTo(initor2.getIndex());
			});
			
			dataInitializers.stream().forEach(dataInitializer -> {
				try {
					dataInitializer.init();
				} catch (Exception e) {
					log.info("系统数据初始化失败: {} : {}" , dataInitializer.getClass().getSimpleName(), e);
				}
			});
		}
	}

}
