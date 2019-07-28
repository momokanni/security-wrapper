package com.security.core.validator.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.security.core.properties.SecurityConstants;
import com.security.core.validator.processor.ValidateCodeProcessor;

@RestController
public class ValidatorCodeController {

	@Autowired
	private Map<String, ValidateCodeProcessor> validateCodeProcessors;
	
	@GetMapping(value = SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX +"/{type}")
	public void createCode(HttpServletRequest req,HttpServletResponse rep,@PathVariable String type) throws Exception {
		
		validateCodeProcessors.get(type + "ValidateCodeProcessor").create(new ServletWebRequest(req,rep));
	}

}
