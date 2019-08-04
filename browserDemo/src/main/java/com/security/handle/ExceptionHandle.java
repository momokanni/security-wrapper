package com.security.handle;

import com.security.exception.UserExpection;
import com.security.web.enums.ResultEnums;
import com.security.web.util.ResultUtil;
import com.security.web.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * @Description 
 * @Author sca
 * @Date 2019-08-03 17:57
 **/
@Slf4j
@ControllerAdvice
public class ExceptionHandle {

	@ResponseBody
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResultVO handlerUserException(Exception e){
		if (e instanceof UserExpection){
			UserExpection userExpection = (UserExpection) e;
			return ResultUtil.error(userExpection.getCode(),userExpection.getMessage());
		}
		log.error("系统错误: {}" , e);
		return ResultUtil.error(ResultEnums.SYSTEM_ERROR.getCode(),ResultEnums.SYSTEM_ERROR.getMsg());
	}
}