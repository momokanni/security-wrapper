package com.security.core.validator.enums;

import com.security.core.properties.SecurityConstants;
import lombok.Getter;

/**
 * @author sca
 */
public enum ValidateCodeType {
	
	SMS("短信"){
		public String getParamNameVaildate() {
			return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
		}
	},
	
	IMAGE("图片"){
		public String getParamNameVaildate() {
			return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
		}
	};

	ValidateCodeType(String typeName) {
		this.typeName = typeName;
	}

	@Getter
	private String typeName;

	public abstract String getParamNameVaildate();

}
