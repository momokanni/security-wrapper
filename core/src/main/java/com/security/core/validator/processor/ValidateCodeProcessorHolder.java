/**
 * 
 */
package com.security.core.validator.processor;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.security.core.validator.enums.ValidateCodeType;
import com.security.core.validator.exception.ValidateCodeException;

/**
 * @author sca
 *
 */
@Component
public class ValidateCodeProcessorHolder {

	@Autowired
	private Map<String, ValidateCodeProcessor> validateCodeProcessor;
	
	public ValidateCodeProcessor findProcessorByType(ValidateCodeType type) {
		
		return findProcessorByType(type.toString().toLowerCase());
	}

	/**
	 * processor具体处理类  = type + ValidateCodeProcessor.class.getSimpleName()
	 * @param type
	 * @return
	 */
	public ValidateCodeProcessor findProcessorByType(String type) {
		String processorName = type + ValidateCodeProcessor.class.getSimpleName();
		ValidateCodeProcessor processor = validateCodeProcessor.get(processorName);
		if (processor == null) {
			throw new ValidateCodeException("验证码处理器" + processorName + "不存在");
		}
		return processor;
	}
}
