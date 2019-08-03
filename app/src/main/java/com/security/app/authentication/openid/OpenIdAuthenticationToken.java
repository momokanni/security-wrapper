package com.security.app.authentication.openid;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
/**
 * 用来封装请求认证令牌的数据（openid + providerId）
 * @author sca
 */
public class OpenIdAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private final Object principal;
	private String providerId;
	
	public OpenIdAuthenticationToken(String openId,String providerId) {
		super(null);
		this.principal = openId;
		this.providerId = providerId;
		setAuthenticated(false);
	}
	
	public OpenIdAuthenticationToken(Object principal,Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		// must user super, as we override
		super.setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
	
		return null;
	}

	@Override
	public Object getPrincipal() {

		return this.principal;
	}

	public String getProviderId() {
		return providerId;
	}
	
	@Override
	public void setAuthenticated(boolean isAuthenticated) {
		if(isAuthenticated) {
			throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead ");
		}
		
		super.setAuthenticated(isAuthenticated);
	}
	
	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
	}
}
