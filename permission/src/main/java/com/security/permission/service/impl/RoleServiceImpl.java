package com.security.permission.service.impl;

import com.security.permission.domain.Role;
import com.security.permission.domain.RoleResource;
import com.security.permission.dto.RoleInfo;
import com.security.permission.repository.ResourceRepository;
import com.security.permission.repository.RoleRepository;
import com.security.permission.repository.RoleResourceRepository;
import com.security.permission.repository.support.QueryResultConverter;
import com.security.permission.service.RoleService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description 
 * @Author sca
 * @Date 2019-08-19 13:54
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ResourceRepository resourceRepository;
	
	@Autowired
	private RoleResourceRepository roleResourceRepository;


	@Override
	public RoleInfo create(RoleInfo info) {
		Role role = new Role();
		BeanUtils.copyProperties(info, role);
		info.setId(roleRepository.save(role).getId());
		return info;
	}


	@Override
	public RoleInfo update(RoleInfo info) {
		Role role = roleRepository.getOne(info.getId());
		BeanUtils.copyProperties(info, role);
		return info;
	}


	@Override
	public void delete(Long id) {
		Role role = roleRepository.getOne(id);
		if(CollectionUtils.isNotEmpty(role.getAdmins())){
			throw new RuntimeException("不能删除有下挂用户的角色");
		}
		roleRepository.deleteById(id);
	}
//
//	@Override
//	public String[] getRoleMenus(Long id) {
//		return StringUtils.split(roleRepository.findOne(id).getMenus(), ",");
//	}
//
//	/**
//	 * (non-Javadoc)
//	 * @see com.idea.ams.service.RoleService#setRoleMenu(java.lang.Long, java.lang.String)
//	 */
//	@Override
//	public void setRoleMenu(Long roleId, String menuIds) {
//		Role role = roleRepository.findOne(roleId);
//		role.setMenus(menuIds);
//	}

	@Override
	public RoleInfo getInfo(Long id) {
		Role role = roleRepository.getOne(id);
		RoleInfo info = new RoleInfo();
		BeanUtils.copyProperties(role, info);
		return info;
	}

	@Override
	public List<RoleInfo> findAll() {
		return QueryResultConverter.convert(roleRepository.findAll(), RoleInfo.class);
	}

	@Override
	public String[] getRoleResources(Long id) {
		Role role = roleRepository.getOne(id);
		Set<String> resourceIds = new HashSet<>();
		for (RoleResource resource : role.getResources()) {
			resourceIds.add(resource.getResource().getId().toString());
		}
		return resourceIds.toArray(new String[resourceIds.size()]);
	}

	@Override
	public void setRoleResources(Long roleId, String resourceIds) {
		resourceIds = StringUtils.removeEnd(resourceIds, ",");
		Role role = roleRepository.getOne(roleId);
		roleResourceRepository.deleteInBatch(role.getResources());
		String[] resourceIdArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(resourceIds, ",");
		for (String resourceId : resourceIdArray) {
			RoleResource roleResource = new RoleResource();
			roleResource.setRole(role);
			roleResource.setResource(resourceRepository.getOne(new Long(resourceId)));
			roleResourceRepository.save(roleResource);
		}
	}

}
