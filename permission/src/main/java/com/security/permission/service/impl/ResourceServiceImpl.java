package com.security.permission.service.impl;

import com.security.permission.domain.Admin;
import com.security.permission.domain.Resource;
import com.security.permission.dto.ResourceInfo;
import com.security.permission.repository.AdminRepository;
import com.security.permission.repository.ResourceRepository;
import com.security.permission.service.ResourceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @Description 
 * @Author sca
 * @Date 2019-08-19 14:00
 **/
@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {
	
	@Autowired
	private ResourceRepository resourceRepository;
	@Autowired
	private AdminRepository adminRepository;

	@Override
	public ResourceInfo getTree(Long adminId) {
		Admin admin = adminRepository.getOne(adminId);
		return resourceRepository.findByName("根节点").toTree(admin);
	}

	@Override
	public ResourceInfo getInfo(Long id) {
		Resource resource = resourceRepository.getOne(id);
		ResourceInfo resourceInfo = new ResourceInfo();
		BeanUtils.copyProperties(resource, resourceInfo);
		return resourceInfo;
	}

	@Override
	public ResourceInfo create(ResourceInfo info) {
		Resource parent = resourceRepository.getOne(info.getParentId());
		if(parent == null){
			parent = resourceRepository.findByName("根节点");
		}
		Resource resource = new Resource();
		BeanUtils.copyProperties(info, resource);
		parent.addChild(resource);
		info.setId(resourceRepository.save(resource).getId());
		return info;		
	}

	@Override
	public ResourceInfo update(ResourceInfo info) {
		Resource resource = resourceRepository.getOne(info.getId());
		BeanUtils.copyProperties(info, resource);
		return info;
	}

	@Override
	public void delete(Long id) {
		resourceRepository.deleteById(id);
	}

	@Override
	public Long move(Long id, boolean up) {
		Resource resource = resourceRepository.getOne(id);
		int index = resource.getSort();
		List<Resource> childs = resource.getParent().getChilds();
		for (int i = 0; i < childs.size(); i++) {
			Resource current = childs.get(i);
			if(current.getId().equals(id)) {
				if(up){
					if(i != 0) {
						Resource pre = childs.get(i - 1);
						resource.setSort(pre.getSort());
						pre.setSort(index);
						resourceRepository.save(pre);
					}
				}else{
					if(i != childs.size()-1) {
						Resource next = childs.get(i + 1);
						resource.setSort(next.getSort());
						next.setSort(index);
						resourceRepository.save(next);
					}
				}
			}
		}
		resourceRepository.save(resource);
		return resource.getParent().getId();
	}

}
