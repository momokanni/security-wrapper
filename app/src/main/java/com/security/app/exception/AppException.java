/**
 * 
 */
package com.security.app.exception;

import org.springframework.security.core.SpringSecurityCoreVersion;

/**
 * @author sca
 *
 */
public class AppException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
	
	public AppException(String msg) {
		super(msg);
	}
}
