package com.security.permission.repository.spec;

import com.security.permission.domain.Admin;
import com.security.permission.dto.AdminCondition;
import com.security.permission.repository.support.PermissionSpecification;
import com.security.permission.repository.support.QueryWrapper;

/**
 * @Description 
 * @Author sca
 * @Date 2019-08-19 11:45
 **/
public class AdminSpec extends PermissionSpecification<Admin, AdminCondition> {

	public AdminSpec(AdminCondition condition) {
		super(condition);
	}

	@Override
	protected void addCondition(QueryWrapper<Admin> queryWrapper) {
		addLikeCondition(queryWrapper, "username");
	}

}
