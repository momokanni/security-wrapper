package com.security.permission.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotEmpty;

/**
 * @Description
 * @Author sca
 * @Date 2019-08-19 11:35
 **/
@Data
public class AdminInfo {
	
	private Long id;
	/**
	 * 角色id 
	 */
	@NotEmpty(message = "角色id不能为空")
	private Long roleId;
	/**
	 * 用户名
	 */
	@NotEmpty(message = "用户名不能为空")
	private String username;

}