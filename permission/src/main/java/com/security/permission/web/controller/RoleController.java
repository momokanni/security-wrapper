package com.security.permission.web.controller;

import com.security.permission.dto.RoleInfo;
import com.security.permission.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 
 * @Author sca
 * @Date 2019-08-19 14:02
 **/
@RestController
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;

	/**
	 * 创建角色
	 * @param roleInfo
	 * @return
	 */
	@PostMapping
	public RoleInfo create(@RequestBody RoleInfo roleInfo) {
		return roleService.create(roleInfo);
	}
	
	/**
	 * 修改角色信息
	 * @param roleInfo
	 * @return
	 */
	@PutMapping("/{id}")
	public RoleInfo update(@RequestBody RoleInfo roleInfo) {
		return roleService.update(roleInfo);
	}
	
	/**
	 * 删除角色
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		roleService.delete(id);
	}

	/**
	 * 获取角色详情
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public RoleInfo getInfo(@PathVariable Long id) {
		return roleService.getInfo(id);
	}

	/**
	 * 获取所有角色
	 * @return
	 */
	@GetMapping
	public List<RoleInfo> findAll() {
		return roleService.findAll();
	}
	
	/**
	 * 获取角色的所有资源
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}/resource")
	public String[] getRoleResources(@PathVariable Long id){
		return roleService.getRoleResources(id);
	}
	
	/**
	 * 创建用户的资源
	 * @param id
	 * @param ids
	 */
	@PostMapping("/{id}/resource")
	public void createRoleResource(@PathVariable Long id, String ids){
		roleService.setRoleResources(id, ids);
	}

}
