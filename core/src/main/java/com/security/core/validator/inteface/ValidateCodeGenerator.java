package com.security.core.validator.inteface;

import org.springframework.web.context.request.ServletWebRequest;
import com.security.core.validator.entity.ValidateCode;

public interface ValidateCodeGenerator {

	ValidateCode generate(ServletWebRequest req);
}
