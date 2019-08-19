/**
 * 
 */
package com.security.permission.service;

import com.security.permission.dto.AdminCondition;
import com.security.permission.dto.AdminInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Description 管理员服务
 * @Author sca
 * @Date 2019-08-19 13:38
 **/
public interface AdminService {

	/**
	 * 创建管理员
	 * @param adminInfo
	 * @return
	 */
	AdminInfo create(AdminInfo adminInfo);
	/**
	 * 修改管理员
	 * @param adminInfo
	 * @return
	 */
	AdminInfo update(AdminInfo adminInfo);
	/**
	 * 删除管理员
	 * @param id
	 */
	void delete(Long id);
	/**
	 * 获取管理员详细信息
	 * @param id
	 * @return
	 */
	AdminInfo getInfo(Long id);
	/**
	 * 分页查询管理员
	 * @param condition
	 * @return
	 */
	Page<AdminInfo> query(AdminCondition condition, Pageable pageable);

}
