package com.security.permission.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description 角色信息
 * @Author sca
 * @Date 2019-08-19 11:33
 **/
@Data
@Entity
public class Role {
	
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
	 * 角色名称
	 */
	@Column(length = 20, nullable = false)
	private String name;
	/**
	 * 角色拥有权限的资源集合
	 */
	@OneToMany(mappedBy = "role", cascade = CascadeType.REMOVE)
	private Set<RoleResource> resources  = new HashSet<>();
	/**
	 * 角色的用户集合
	 */
	@OneToMany(mappedBy = "role", cascade = CascadeType.REMOVE)
	private Set<RoleAdmin> admins = new HashSet<>();
	
}
