package com.security.web.entity;

import java.util.Date;
import javax.validation.constraints.Past;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonView;
import com.security.validator.MyConstraint;
import org.springframework.security.core.SpringSecurityCoreVersion;

@Data
public class User extends BaseEntity {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	public interface userSimpleView {};
	
	public interface userDetailView extends userSimpleView {};

	/**
	 * 用户名
	 */
	@MyConstraint(message = "this is test")
	@JsonView(userSimpleView.class)
	private String userName;
	/**
	 * 密码
	 * Hibernate Validator
	 */
	@NotBlank(message = "密码不能为空")
	@JsonView(userDetailView.class)
	private String password;

	/**
	 * 手机号码
	 */
	private String tel;
	
	@Past(message = "生日必须为过去式")
	@JsonView(userSimpleView.class)
	private int birthday;
	/**
	 * 锁定状态
	 */
	private String lockStatus;
	/**
	 * 权限列表
	 */
	private String authorities;
	/**
	 * 是否启用
	 */
	private String enabled;

}
