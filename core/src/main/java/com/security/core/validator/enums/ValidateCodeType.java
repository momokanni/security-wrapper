package com.security.core.validator.enums;

import com.security.core.properties.SecurityConstants;
import lombok.Getter;

/**
 * @author sca
 */
public enum ValidateCodeType {

	/**
	 * 短信验证码
	 */
	SMS("短信"){
		@Override
		public String getParamNameValidate() {
			return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
		}
	},

	/**
	 * 图片验证码
	 */
	IMAGE("图片"){
		@Override
		public String getParamNameValidate() {
			return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
		}
	};

	ValidateCodeType(String typeName) {
		this.typeName = typeName;
	}

	@Getter
	private String typeName;

	public abstract String getParamNameValidate();

}
