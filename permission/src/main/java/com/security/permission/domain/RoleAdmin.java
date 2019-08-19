package com.security.permission.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description 角色用户关系表
 * @Author sca
 * @Date 2019-08-19 11:33
 **/
@Data
@Entity
public class RoleAdmin {

	/**
	 * 数据库表主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 审计日志，记录条目创建时间，自动赋值，不需要程序员手工赋值
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdTime;
	/**
	 * 角色
	 */
	@ManyToOne
	private Role role;
	/**
	 * 管理员
	 */
	@ManyToOne
	private Admin admin;
}
