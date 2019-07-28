package com.security.app.validate;

import java.util.concurrent.TimeUnit;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import com.security.core.validator.entity.ValidateCode;
import com.security.core.validator.enums.ValidateCodeType;
import com.security.core.validator.exception.ValidateCodeException;
import com.security.core.validator.inteface.ValidateCodeRepository;

/**
 * @Description 
 * @Author sca
 * @Date 2019-07-24 11:33
 **/
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	
	
	@Override
	public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType codeType) {
		redisTemplate.opsForValue().set(buildKey(request, codeType), code, 30, TimeUnit.MINUTES);
	}

	
	@Override
	public ValidateCode get(ServletWebRequest request, ValidateCodeType codeType) {
		Object value = redisTemplate.opsForValue().get(buildKey(request, codeType));
		if(value == null) {
			return null;
		}
		return (ValidateCode) value;
	}

	
	@Override
	public void remove(ServletWebRequest request, ValidateCodeType codeType) {
		
		redisTemplate.delete(buildKey(request, codeType));
	}
	
	
	private String buildKey(ServletWebRequest request,ValidateCodeType type) {
		String deviceId = request.getHeader("deviceId");
		if(StringUtils.isBlank(deviceId)) {
			throw new ValidateCodeException("请求头中不含deviceId");
		}
		
		return "code:" + type.toString().toLowerCase() + ":" + deviceId;
	}

}
