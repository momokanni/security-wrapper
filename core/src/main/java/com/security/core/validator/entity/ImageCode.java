package com.security.core.validator.entity;

import lombok.Data;
import org.springframework.security.core.SpringSecurityCoreVersion;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 图片验证码
 * @author sca
 */
@Data
public class ImageCode extends ValidateCode {
	/**
	 * 
	 */
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;;
	
	/**
	 * 图片缓存
	 */
	private BufferedImage image;
	
	public ImageCode(BufferedImage image,String code,int expireIn) {
		super(code, expireIn);
		this.image = image;
	}
	
	public ImageCode(BufferedImage image,String code,LocalDateTime expireTime) {
		super(code, expireTime);
		this.image = image;
	}
}
