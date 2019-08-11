package com.security.web.entity;

import javax.validation.constraints.Past;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonView;
import com.security.validator.MyConstraint;
import org.springframework.security.core.SpringSecurityCoreVersion;

/**
 * @Description 
 * @Author sca
 * @Date 2019-08-03 17:58
 **/
@Data
public class User extends BaseEntity {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	public interface UserSimpleView {};
	
	public interface UserDetailView extends UserSimpleView {};

	/**
	 * 用户名
	 */
	@MyConstraint(message = "this is test")
	@JsonView(UserSimpleView.class)
	private String userName;
	/**
	 * 密码
	 * Hibernate Validator
	 */
	@JsonView(UserDetailView.class)
	private String password;

	/**
	 * 手机号码
	 */
	private String tel;
	
	@Past(message = "生日必须为过去式")
	@JsonView(UserSimpleView.class)
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
