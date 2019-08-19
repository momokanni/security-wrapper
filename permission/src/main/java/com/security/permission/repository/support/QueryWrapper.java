package com.security.permission.repository.support;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @Description 包装用于构建JPA动态查询时所需的对象
 * @Author sca
 * @Date 2019-08-19 13:33
 **/
public class QueryWrapper<T> {

	/**
	 * @param root
	 *            JPA Root
	 * @param cb
	 *            JPA CriteriaBuilder
	 * @param predicates
	 *            JPA Predicate 集合
	 */
	public QueryWrapper(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb, List<Predicate> predicates) {
		this.root = root;
		this.query = query;
		this.cb = cb;
		this.predicates = predicates;
	}

	/**
	 * JPA Root
	 */
	@Getter
	@Setter
	private Root<T> root;
	/**
	 * JPA CriteriaBuilder
	 */
	@Getter
	@Setter
	private CriteriaBuilder cb;
	/**
	 * JPA Predicate 集合
	 */
	@Getter
	private List<Predicate> predicates;
	/**
	 * JPA 查询对象
	 */
	@Getter
	@Setter
	private CriteriaQuery<?> query;

	/**
	 * <pre>
	 *
	 * <pre>
	 * @param predicate
	 * @author jojo 2014-8-12 下午3:12:36
	 */
	public void addPredicate(Predicate predicate) {
		this.predicates.add(predicate);
	}
}
