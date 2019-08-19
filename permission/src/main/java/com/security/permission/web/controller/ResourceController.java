package com.security.permission.web.controller;

import com.security.permission.domain.Admin;
import com.security.permission.dto.ResourceInfo;
import com.security.permission.service.ResourceService;
import com.security.permission.web.controller.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 
 * @Author sca
 * @Date 2019-08-19 13:42
 **/
@Slf4j
@RestController
@RequestMapping("/resource")
public class ResourceController {
	
	@Autowired
	private ResourceService resourceService;
	
	/**
	 * 获取资源树
	 * @param admin
	 * @return
	 */
	@GetMapping
	public ResourceInfo getTree(@AuthenticationPrincipal UserDetails admin){
		log.info("获取用户：{} 的菜单列表" , admin.getUsername());
		return resourceService.getTree(Long.valueOf(6));
	}
	/**
	 * 获取资源信息
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResourceInfo getInfo(@PathVariable Long id){
		return resourceService.getInfo(id);
	}
	/**
	 * 创建资源
	 * @param info
	 * @return
	 */
	@PostMapping
	public ResourceInfo create(@RequestBody ResourceInfo info){
		if(info.getParentId() == null) {
			info.setParentId(0L);
		}
		return resourceService.create(info);
	}
	/**
	 * 修改资源
	 * @param info
	 * @return
	 */
	@PutMapping("/{id}")
	public ResourceInfo update(@RequestBody ResourceInfo info){
		return resourceService.update(info);
	}
	/**
	 * 删除
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id){
		resourceService.delete(id);
	}
	
	/**
	 * 资源上移
	 * @param id
	 */
	@PostMapping("/{id}/up")
	public SimpleResponse moveUp(@PathVariable Long id){
		return new SimpleResponse(resourceService.move(id, true));
	}
	/**
	 * 资源下移
	 * @param id
	 */
	@PostMapping("/{id}/down")
	public SimpleResponse moveDown(@PathVariable Long id){
		return new SimpleResponse(resourceService.move(id, false));
	}

}
