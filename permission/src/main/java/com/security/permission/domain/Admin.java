package com.security.permission.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @Description 管理员(用户)
 * @Author sca
 * @Date 2019-08-19 11:10
 **/
@Entity
public class Admin implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3521673552808391992L;
	/**
	 * 数据库主键
	 */
	@Id
	@Getter
	@Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 审计日志，记录条目创建时间，自动赋值，不需要程序员手工赋值
	 */
	@Getter
	@Setter
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdTime;
	/**
	 * 用户名
	 */
	@Getter
	@Setter
	private String username;
	/**
	 * 密码
	 */
	@Getter
	@Setter
	private String password;
	/**
	 * 用户的所有角色
	 */
	@Getter
	@Setter
	@OneToMany(mappedBy = "admin", cascade = CascadeType.REMOVE)
	private Set<RoleAdmin> roles = new HashSet<>();
	/**
	 * 用户有权访问的所有url，不持久化到数据库
	 */
	@Setter
	@Transient
	private Set<String> urls = new HashSet<>();
	/**
	 * 用户有权的所有资源id，不持久化到数据库
	 */
	@Transient
	private Set<Long> resourceIds = new HashSet<>();


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}


	public Set<Long> getAllResourceIds() {
		init(resourceIds);
		forEachResource(resource -> resourceIds.add(resource.getId()));
		return resourceIds;
	}


	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Set<String> getUrls() {
		init(urls);
		forEachResource(resource -> urls.addAll(resource.getUrls()));
		return urls;
	}

	private void init(Set<?> data){
		if (CollectionUtils.isEmpty(data)) {
			if (data == null) {
				data = new HashSet<>();
			}
		}
	}

	private void forEachResource(Consumer<Resource> consumer) {
		for (RoleAdmin role : roles) {
			for (RoleResource resource : role.getRole().getResources()) {
				consumer.accept(resource.getResource());
			}
		}
	}

}
