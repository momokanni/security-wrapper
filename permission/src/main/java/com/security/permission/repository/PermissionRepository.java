/**
 * 
 */
package com.security.permission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @Description 
 * @Author sca
 * @Date 2019-08-19 13:41
 **/
@NoRepositoryBean
public interface PermissionRepository<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

}
