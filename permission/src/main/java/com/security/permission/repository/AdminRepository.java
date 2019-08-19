package com.security.permission.repository;

import com.security.permission.domain.Admin;
import org.springframework.stereotype.Repository;

/**
 * @Description 
 * @Author sca
 * @Date 2019-08-19 13:35
 **/
@Repository
public interface AdminRepository extends PermissionRepository<Admin> {

	Admin findByUsername(String username);

}
