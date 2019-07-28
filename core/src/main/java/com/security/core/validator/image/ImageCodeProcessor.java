package com.security.core.validator.image;

import javax.imageio.ImageIO;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import com.security.core.validator.entity.ImageCode;
import com.security.core.validator.processor.AbstractValidateCodeProcessor;

@Component(value = "imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {
	
	@Override
	protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
		
		ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
	}

}
