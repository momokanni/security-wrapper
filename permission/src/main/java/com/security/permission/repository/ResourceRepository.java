package com.security.permission.repository;

import com.security.permission.domain.Resource;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author sca
 * @Date 2019-08-19 13:35
 **/
@Repository
public interface ResourceRepository extends PermissionRepository<Resource> {

	Resource findByName(String name);

}
