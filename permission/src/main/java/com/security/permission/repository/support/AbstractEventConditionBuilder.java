package com.security.permission.repository.support;

import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;


/**
 * @Description 
 * @Author sca
 * @Date 2019-08-19 11:50
 **/
public abstract class AbstractEventConditionBuilder<T, C> extends AbstractConditionBuilder<T> {

	/**
	 * 查询条件
	 */
	private C condition;
	
	/**
	 * @param condition 查询条件
	 */
	public AbstractEventConditionBuilder(C condition){
		this.condition = condition;
	}

	/**
	 * 向查询中添加包含(like)条件
	 * @param queryWrapper
	 * @param field 指出查询条件的值从condition对象的哪个字段里取，并且指出要向哪个字段添加包含(like)条件。(同一个字段名称)
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	protected void addLikeCondition(QueryWrapper<T> queryWrapper, String field){
		addLikeCondition(queryWrapper, field, field);
	}

	/**
	 * 向查询中添加包含(like)条件
	 *
	 * @param queryWrapper
	 * @param field 指出查询条件的值从condition对象的哪个字段里取
	 * @param column 指出要向哪个字段添加包含(like)条件
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	protected void addLikeCondition(QueryWrapper<T> queryWrapper, String field, String column){
		addLikeConditionToColumn(queryWrapper, column, (String)
				getValue(getCondition(), field));
	}


	/**
	 * 向查询中添加包含(like)条件,%放在值后面
	 *
	 * @param queryWrapper
	 * @param field 指出查询条件的值从condition对象的哪个字段里取，并且指出要向哪个字段添加包含(like)条件。(同一个字段名称)
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	protected void addStartsWidthCondition(QueryWrapper<T> queryWrapper, String field){
		addStartsWidthCondition(queryWrapper, field, field);
	}

	/**
	 * 向查询中添加包含(like)条件,%放在值后面
	 *
	 * @param queryWrapper
	 * @param field 指出查询条件的值从condition对象的哪个字段里取
	 * @param column 指出要向哪个字段添加包含(like)条件
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	protected void addStartsWidthCondition(QueryWrapper<T> queryWrapper, String field, String column){
		addStartsWidthConditionToColumn(queryWrapper, column, (String)
				getValue(getCondition(), field));
	}

	/**
	 * 向查询中添加等于(=)条件
	 *
	 * @param queryWrapper
	 * @param field 指出查询条件的值从condition对象的哪个字段里取，并且指出要向哪个字段添加条件。(同一个字段名称)
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	protected void addEqualsCondition(QueryWrapper<T> queryWrapper, String field){
		addEqualsCondition(queryWrapper, field, field);
	}

	/**
	 * 向查询中添加等于(=)条件
	 *
	 * @param queryWrapper
	 * @param field 指出查询条件的值从condition对象的哪个字段里取
	 * @param column 指出要向哪个字段添加条件
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	protected void addEqualsCondition(QueryWrapper<T> queryWrapper, String field, String column){
		addEqualsConditionToColumn(queryWrapper, column,
				getValue(getCondition(), field));
	}

	/**
	 * 向查询中添加不等于(!=)条件
	 *
	 * @param queryWrapper
	 * @param field 指出查询条件的值从condition对象的哪个字段里取，并且指出要向哪个字段添加条件。(同一个字段名称)
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	protected void addNotEqualsCondition(QueryWrapper<T> queryWrapper, String field){
		addNotEqualsCondition(queryWrapper, field, field);
	}

	/**
	 * 向查询中添加等于(=)条件
	 *
	 * @param queryWrapper
	 * @param field 指出查询条件的值从condition对象的哪个字段里取
	 * @param column 指出要向哪个字段添加条件
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	protected void addNotEqualsCondition(QueryWrapper<T> queryWrapper, String field, String column){
		addNotEqualsConditionToColumn(queryWrapper, column, getValue(getCondition(), field));
	}

	/**
	 * <pre>
	 * 向查询中添加in条件
	 * <pre>
	 * @param queryWrapper
	 * @param field
	 */
	protected void addInCondition(QueryWrapper<T> queryWrapper, String field) {
		addInCondition(queryWrapper, field, field);
	}
	/**
	 * <pre>
	 * 向查询中添加in条件
	 * <pre>
	 * @param queryWrapper
	 * @param field
	 * @param column
	 */
	protected void addInCondition(QueryWrapper<T> queryWrapper, String field, String column) {
		addInConditionToColumn(queryWrapper, column,
				getValue(getCondition(), field));
	}

	/**
	 * <pre>
	 * 向查询中添加between条件
	 * <pre>
	 * @param queryWrapper
	 * @param field
	 */
	protected void addBetweenCondition(QueryWrapper<T> queryWrapper, String field) {
		addBetweenCondition(queryWrapper, field, field+"To", field);
	}
	/**
	 * <pre>
	 * 向查询中添加between条件
	 * <pre>
	 * @param queryWrapper
	 * @param column
	 */
	@SuppressWarnings("rawtypes")
	protected void addBetweenCondition(QueryWrapper<T> queryWrapper, String startField, String endField, String column) {
		addBetweenConditionToColumn(queryWrapper, column,
				(Comparable)getValue(getCondition(), startField),
				(Comparable)getValue(getCondition(), endField));
	}

	/**
	 * <pre>
	 * 向查询中添加大于条件
	 * <pre>
	 * @param queryWrapper
	 * @param field
	 */
	protected void addGreaterThanCondition(QueryWrapper<T> queryWrapper, String field) {
		addGreaterThanCondition(queryWrapper, field, field);
	}
	/**
	 * <pre>
	 * 向查询中添加大于条件
	 * <pre>
	 * @param queryWrapper
	 * @param field
	 * @param column
	 */
	@SuppressWarnings("rawtypes")
	protected void addGreaterThanCondition(QueryWrapper<T> queryWrapper, String field, String column) {
		addGreaterThanConditionToColumn(queryWrapper, column,
				(Comparable)getValue(getCondition(), field));
	}

	/**
	 * <pre>
	 * 向查询中添加大于等于条件
	 * <pre>
	 * @param queryWrapper
	 * @param field
	 */
	protected void addGreaterThanOrEqualCondition(QueryWrapper<T> queryWrapper, String field) {
		addGreaterThanOrEqualCondition(queryWrapper, field, field);
	}

	/**
	 * <pre>
	 * 向查询中添加大于等于条件
	 * <pre>
	 * @param queryWrapper
	 * @param field
	 * @param column
	 */
	@SuppressWarnings("rawtypes")
	protected void addGreaterThanOrEqualCondition(QueryWrapper<T> queryWrapper, String field, String column) {
		addGreaterThanOrEqualConditionToColumn(queryWrapper, column,
				(Comparable)getValue(getCondition(), field));
	}

	/**
	 * <pre>
	 * 向查询中添加小于条件
	 * <pre>
	 * @param queryWrapper
	 * @param field
	 */
	protected void addLessThanCondition(QueryWrapper<T> queryWrapper, String field) {
		addLessThanCondition(queryWrapper, field, field);
	}
	/**
	 * <pre>
	 * 向查询中添加小于条件
	 * <pre>
	 * @param queryWrapper
	 * @param field
	 * @param column
	 */
	protected void addLessThanCondition(QueryWrapper<T> queryWrapper, String field, String column) {
		addLessThanConditionToColumn(queryWrapper, column,
				(Comparable)getValue(getCondition(), field));
	}

	/**
	 * <pre>
	 * 向查询中添加小于等于条件
	 * <pre>
	 * @param queryWrapper
	 * @param field
	 */
	protected void addLessThanOrEqualCondition(QueryWrapper<T> queryWrapper, String field) {
		addLessThanOrEqualCondition(queryWrapper, field, field);
	}

	/**
	 * <pre>
	 * 向查询中添加小于等于条件
	 * <pre>
	 * @param queryWrapper
	 * @param field
	 * @param column
	 */
	protected void addLessThanOrEqualCondition(QueryWrapper<T> queryWrapper, String field, String column) {
		addLessThanOrEqualConditionToColumn(queryWrapper, column,
				(Comparable)getValue(getCondition(), field));
	}

	private Object getValue(C condition, String field) {
		try {
			return PropertyUtils.getProperty(condition, field);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @return the condition
	 */
	public C getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCondition(C condition) {
		this.condition = condition;
	}
	
}
