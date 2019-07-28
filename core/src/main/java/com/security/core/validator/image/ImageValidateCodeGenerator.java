package com.security.core.validator.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import com.security.core.properties.SecurityProperties;
import com.security.core.validator.entity.ImageCode;
import com.security.core.validator.inteface.ValidateCodeGenerator;

/**
 * @author sca
 */
@Slf4j
public class ImageValidateCodeGenerator implements ValidateCodeGenerator {

	@Autowired
	@Getter
	@Setter
	private SecurityProperties securityProperties;
	
	@Override
	public ImageCode generate(ServletWebRequest req) {
		
		int width = ServletRequestUtils.getIntParameter(req.getRequest(), "width", securityProperties.getCode().getImgCode().getWidth());
		int height = ServletRequestUtils.getIntParameter(req.getRequest(), "height", securityProperties.getCode().getImgCode().getHeight());
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		Random random = new Random();
		
		//绘图
		Graphics g = image.getGraphics();
		g.setColor(getRandColor(200,250));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Time new Roman",Font.ITALIC,20));
		g.setColor(getRandColor(160,200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x , y , x+xl , y+yl);
		}
		
		//生成4位随机数
		String sRand  = "";
		for (int i = 0; i < ServletRequestUtils.getIntParameter(req.getRequest(), "length", securityProperties.getCode().getImgCode().getLength()); i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(rand, 13 * i + 6, 16);
		}
		log.info("生成的图片验证码为: {}" , sRand);
		g.dispose();
		return new ImageCode(image, sRand, 60);
	}
	
	/**
	 * 生成随机背景条纹
	 * @param fc
	 * @param bc
	 * @return
	 */
	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if(fc > 255) {
			fc = 255;
		}
		if(bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b =  fc + random.nextInt(bc - fc);
		
		return new Color(r, g, b);
	}
}
