package com.security.core.validator.processor;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import com.security.core.util.Tools;
import com.security.core.validator.entity.ValidateCode;
import com.security.core.validator.enums.ValidateCodeType;
import com.security.core.validator.exception.ValidateCodeException;
import com.security.core.validator.inteface.ValidateCodeGenerator;
import com.security.core.validator.inteface.ValidateCodeRepository;

/**
 * @Description 
 * @Author sca
 * @Date 2019-08-03 17:55
 **/
@Slf4j
public abstract class AbstractValidateCodeProcessor<T extends ValidateCode> implements ValidateCodeProcessor{

	/**
	 * 操作session的工具
	 */
	//private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	
	@Autowired
	private ValidateCodeRepository validateRepository;
	
	/**
	 * 注入map形式的validateCodeGenerator，
	 * spring会在启动时自动收集所有该接口的实现类
	 */
	@Autowired
	private Map<String, ValidateCodeGenerator> validateCodeGenerators;
	
	/**
	 * 1、先生成验证码
	 * 2、再判断发送方式
	 */ 
	@Override
	public void create(ServletWebRequest request) throws Exception {
		T validateCode = generate(request);
		save(request,validateCode);
		send(request,validateCode);
	}
	
	
	public T generate(ServletWebRequest req) {
		//通过请求获取验证码类型
		String type = Tools.getProcessorType(req);
		//通过类型确定ValidateCodeGenerator的具体实现
		String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
		ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
		if (validateCodeGenerator == null) {
			log.error("processor is not exist");
		}
		return (T) validateCodeGenerator.generate(req);
	}
	
	/**
	 * 发送校验码，由子类实现
	 * 
	 * @param request
	 * @param validateCode
	 * @throws Exception
	 */
	protected abstract void send(ServletWebRequest request, T validateCode) throws Exception;
	
	/**
	 * 将验证码保存到session中
	 * @param request
	 * @param validateCode
	 */
	private void save(ServletWebRequest request, T validateCode) {
		ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
		/**
		 * sessionStrategy.setAttribute(request,  getSessionKey(request), code);
		 */
		validateRepository.save(request, code, getValidateCodeType(request));
	}
	
	/**
	 * 构建验证码放入session时的key
	 * 
	 * @param request
	 * @return
	 */
	private String getSessionKey(ServletWebRequest request) {
		return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
	}
	
	/**
	 * 校验
	 */
	@Override
	public void validate(ServletWebRequest servletWebRequest) {

		//1、获取请求验证码类型
		ValidateCodeType processorType = getValidateCodeType(servletWebRequest);
		//2、通过类型从缓存读取值并转化为对应的实体对象
		T codeInSession = (T) validateRepository.get(servletWebRequest, processorType);
		String codeInRequest;
		try {
			codeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), processorType.getParamNameValidate());
		} catch (ServletRequestBindingException e) {
			throw new ValidateCodeException("获取验证码的值失败");
		}

		if (codeInSession == null) {
			throw new ValidateCodeException(processorType.getTypeName() + "验证码不存在");
		}

		if (codeInSession.isExpired()) {
			validateRepository.remove(servletWebRequest, processorType);
			throw new ValidateCodeException(processorType.getTypeName() + "验证码已过期");
		}

		//3、判断（有值、无值）
		if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException(processorType.getTypeName() + "验证码的值不能为空");
		}

		if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException(processorType.getTypeName() + "验证码不匹配");
		}

		validateRepository.remove(servletWebRequest, processorType);
	}
	
	
	/**
	 * 根据请求的url获取校验码的类型
	 * 
	 * @param request
	 * @return
	 */
	private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
		String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
		return ValidateCodeType.valueOf(type.toUpperCase());
	}
}
