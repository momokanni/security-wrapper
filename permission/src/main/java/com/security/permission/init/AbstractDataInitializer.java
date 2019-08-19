package com.security.permission.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;

/**
 * @Description 抽象数据初始化器，所有的数据初始化器应该继承此类
 * @Author sca
 * @Date 2019-08-19 11:36
 **/
@Slf4j
public abstract class AbstractDataInitializer implements DataInitializer {

	@Override
	@Transactional(rollbackFor = {Exception.class})
	public void init() throws Exception {
		if(isNeedInit()) {
			log.info("使用{}初始化数据" , getClass().getSimpleName());
			doInit();
			log.info("使用{}初始化数据完毕" , getClass().getSimpleName());
		}
	}

	/**
	 * 实际的数据初始化逻辑
	 * @throws Exception
	 */
	protected abstract void doInit() throws Exception;
	
	/**
	 * 是否执行数据初始化行为
	 * @return
	 */
	protected abstract boolean isNeedInit();

}
