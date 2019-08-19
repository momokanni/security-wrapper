package com.security.permission.domain;

import com.security.permission.dto.ResourceInfo;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Description  需要控制权限的资源，以业务人员能看懂的name呈现.实际关联到一个或多个url上.【树形结构】。
 * @Author sca
 * @Date 2019-08-19 11:30
 **/
@Data
@Entity
public class Resource {

	/**
	 * 数据库表主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 审计日志，记录条目创建时间，自动赋值，不需要程序员手工赋值
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdTime;
	/**
	 * 资源名称，如xx菜单，xx按钮
	 */
	private String name;
	/**
	 * 资源链接
	 */
	private String link;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 资源类型
	 */
	@Enumerated(EnumType.STRING)
	private ResourceType type;
	/**
	 * 序号
	 */
	private int sort;
	/**
	 * 实际需要控制权限的url
	 */
	@ElementCollection
	private Set<String> urls;
	/**
	 * 父资源
	 */
	@ManyToOne
	private Resource parent;
	/**
	 * 子资源
	 */
	@OneToMany(mappedBy = "parent")
	@OrderBy("sort ASC")
	private List<Resource> childs = new ArrayList<>();

	public ResourceInfo toTree(Admin admin) {
		ResourceInfo result = new ResourceInfo();
		BeanUtils.copyProperties(this, result);
		Set<Long> resourceIds = admin.getAllResourceIds();
		
		List<ResourceInfo> children = new ArrayList<ResourceInfo>();
		for (Resource child : getChilds()) {
			if(StringUtils.equals(admin.getUsername(), "admin") || 
					resourceIds.contains(child.getId())){
				children.add(child.toTree(admin));
			}
		}
		result.setChildren(children);
		return result;
	}
	
	public void addChild(Resource child) {
		childs.add(child);
		child.setParent(this);
	}

}
