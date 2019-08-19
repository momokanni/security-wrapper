package com.security.permission.service.impl;

import com.security.permission.domain.Admin;
import com.security.permission.domain.RoleAdmin;
import com.security.permission.dto.AdminCondition;
import com.security.permission.dto.AdminInfo;
import com.security.permission.repository.AdminRepository;
import com.security.permission.repository.RoleAdminRepository;
import com.security.permission.repository.RoleRepository;
import com.security.permission.repository.spec.AdminSpec;
import com.security.permission.repository.support.QueryResultConverter;
import com.security.permission.service.AdminService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description 
 * @Author sca
 * @Date 2019-08-19 13:57
 **/
@Service
@Transactional
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RoleAdminRepository roleAdminRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;


	@Override
	public AdminInfo create(AdminInfo adminInfo) {
		
		Admin admin = new Admin();
		BeanUtils.copyProperties(adminInfo, admin);
		admin.setPassword(passwordEncoder.encode("123456"));
		adminRepository.save(admin);
		adminInfo.setId(admin.getId());
		
		createRoleAdmin(adminInfo, admin);
		
		return adminInfo;
	}


	@Override
	public AdminInfo update(AdminInfo adminInfo) {
		
		Admin admin = adminRepository.getOne(adminInfo.getId());
		BeanUtils.copyProperties(adminInfo, admin);
		
		createRoleAdmin(adminInfo, admin);
		
		return adminInfo;
	}

	/**
	 * 创建角色用户关系数据。
	 * @param adminInfo
	 * @param admin
	 */
	private void createRoleAdmin(AdminInfo adminInfo, Admin admin) {
		if(CollectionUtils.isNotEmpty(admin.getRoles())){
			roleAdminRepository.deleteInBatch(admin.getRoles());
		}
		RoleAdmin roleAdmin = new RoleAdmin();
		roleAdmin.setRole(roleRepository.getOne(adminInfo.getRoleId()));
		roleAdmin.setAdmin(admin);
		roleAdminRepository.save(roleAdmin);
	}

	/* (non-Javadoc)
	 * @see com.imooc.security.rbac.service.AdminService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		adminRepository.deleteById(id);
	}

	/* (non-Javadoc)
	 * @see com.imooc.security.rbac.service.AdminService#getInfo(java.lang.Long)
	 */
	@Override
	public AdminInfo getInfo(Long id) {
		Admin admin = adminRepository.getOne(id);
		AdminInfo info = new AdminInfo();
		BeanUtils.copyProperties(admin, info);
		return info;
	}


	@Override
	public Page<AdminInfo> query(AdminCondition condition, Pageable pageable) {
		Page<Admin> admins = adminRepository.findAll(new AdminSpec(condition), pageable);
		return QueryResultConverter.convert(admins, AdminInfo.class, pageable);
	}

}
