package com.security.social;

import com.security.core.service.SuperUserDetailsService;
import com.security.exception.UserExpection;
import com.security.web.dao.DemoUserDetailsDao;
import com.security.web.entity.User;
import com.security.web.enums.EnabledStatus;
import com.security.web.enums.LockStatus;
import com.security.web.enums.ResultEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 表单用户信息获取 UserDetailsService
 * 社交用户信息获取 SocialUserDetailsService
 * @author sca
 *
 */
@Slf4j
@Component
@Transactional(rollbackFor = Exception.class)
public class MyUserDetailsServiceImpl implements SuperUserDetailsService,SocialUserDetailsService {

	@Autowired
	private DemoUserDetailsDao demoUserDetailsDao;
	
	/**
	 * 用户信息校验 UserDetails
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//根据用户名查找用户信息 
		log.info("表单登录用户名： {}" , username);
		return buildUser(username);
	}

	/**
	 * 用户名查询用户信息
	 * @param userId
	 * @return
	 * @throws UsernameNotFoundException
	 */
	@Override
	public SocialUserDetails loadUserByUserId(String userId ) throws UsernameNotFoundException {
		log.info("社交登录用户ID：{}" , userId);
		return buildUser(userId);
	}

	/**
	 * 手机号查询用户信息
	 * @param tel
	 * @return
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByTel(String tel) throws UsernameNotFoundException {
		log.info("社交登录用户手机号码：{}" , tel);
		return buildUserByMobile(tel);
	}

	/**
	 * 查询参数必须保证唯一性  既可以是userId,也可以是userName 但必须唯一
	 * @return  SocialUserDetails extends UserDetails
	 * @throws UsernameNotFoundException
	 */
	public SocialUserDetails buildUser(String username) throws UsernameNotFoundException {
		//第三个参数 GrantedAuthority 集合  指明当前登录用户权限
		//根据查找的用户信息判断用户是否被冻结，是否被锁定
		User user = demoUserDetailsDao.loadUserDetailsById(username);
		if (user == null){
			throw new UserExpection(ResultEnums.USER_NOT_EXISTS.getCode(),ResultEnums.USER_NOT_EXISTS.getMsg());
		} else {
			boolean enabled = EnabledStatus.getValue(user.getEnabled());
			boolean lock = LockStatus.getValue(user.getLockStatus());
			return new SocialUser(user.getUserName(), user.getPassword(), enabled,true,true,lock,AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities()));
		}
		// return new SocialUser(userId, pwd, true,true,true,true,AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN,ROLE_USER"));
	}


	public SocialUserDetails buildUserByMobile(String tel) throws UsernameNotFoundException {
		User user = demoUserDetailsDao.loadUserDetailsByTel(tel);
		if (user == null){
			throw new UserExpection(ResultEnums.USER_NOT_EXISTS.getCode(),ResultEnums.USER_NOT_EXISTS.getMsg());
		} else {
			boolean enabled = EnabledStatus.getValue(user.getEnabled());
			boolean lock = LockStatus.getValue(user.getLockStatus());
			return new SocialUser(user.getUserName(), user.getPassword(), enabled,true,true,lock,AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities()));
		}
	}

}
