package com.security.core.authencation.sms;

import com.security.core.service.SuperUserDetailsService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Description 短信验证码登录验证方案提供方
 * @Author sca
 * @Date 2019-07-24 11:36
 **/
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

	@Getter
	@Setter
	private SuperUserDetailsService superUserDetailsService;
	
	
	public SmsCodeAuthenticationProvider() {}
	
	/**
	 * 执行身份认证逻辑
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		//1、将Authentication强转成SmsAuthenticationToken以便获取用户手机号
		SmsCodeAuthenticationToken token = (SmsCodeAuthenticationToken) authentication;
		//查询用户信息（手机号）
		UserDetails user = superUserDetailsService.loadUserByTel(token.getPrincipal().toString());
		if(user == null) {
			throw new InternalAuthenticationServiceException("该用户不存在");
		}
		
		//由未认证token变更为已通过认证token
		SmsCodeAuthenticationToken result = new SmsCodeAuthenticationToken(user,user.getAuthorities());
		//将请求详细信息存入已认证token
		result.setDetails(token.getDetails());
		return result;
	}

	/**
	 * 用以提供该provider是否支持处理当前传入token的判断
	 */
	@Override
	public boolean supports(Class<?> authentication) {

		return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
