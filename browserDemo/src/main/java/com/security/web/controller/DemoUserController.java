package com.security.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.security.core.properties.SecurityProperties;
import com.security.exception.UserExpection;
import com.security.web.dto.DemoUser;
import com.security.web.dto.UserQueryCondition;
import com.security.web.enums.ResultEnums;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 用户处理器
 * @author: sca
 * @create: 2019-07-19 14:12
 **/
@Slf4j
@RestController
@RequestMapping(value = "/user")
public class DemoUserController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private SecurityProperties securityProperties;

    @GetMapping
    @JsonView(value = DemoUser.UserSimpleView.class)
    public List<DemoUser> getUserCount(UserQueryCondition userQueryCondition,
                                       @PageableDefault(page = 1,size = 11,sort = "age",direction = Sort.Direction.DESC) Pageable page){
        log.info("mockMvc test 反射查看传入参数: {}" , ReflectionToStringBuilder.toString(userQueryCondition , ToStringStyle.MULTI_LINE_STYLE));
        log.info("pageable: page={}, size={}, sort={}" , page.getPageNumber(),page.getPageSize(),page.getSort());
        List<DemoUser> demoUsers = new ArrayList<>();
        demoUsers.add(new DemoUser());
        demoUsers.add(new DemoUser());
        demoUsers.add(new DemoUser());
        return demoUsers;
    }

    /**
     * 用户
      * @param userId
     * @return
     */
    @GetMapping(value = "/{id}")
    @JsonView(value = DemoUser.UserDetailView.class)
    @ApiOperation(value = "查询用户详情")
    public DemoUser getInfo(@ApiParam(value = "用户ID") @PathVariable(value = "id") String userId) {
        DemoUser demoUser = new DemoUser();
        demoUser.setUsername("tom");
        demoUser.setPwd("123456");
        return demoUser;
    }

    /**
     * 通过正则表达式限制所传参数ID数据类型只能为数字
     * @param userId
     * @return
     */
    @GetMapping(value = "/detail/{id:\\d+}")
    public DemoUser getDetailByUserId(@PathVariable(value = "id") String userId) {
        DemoUser demoUser = new DemoUser();
        demoUser.setUsername("regex");
        return demoUser;
    }

    /**
     * 创建用户
     * @param demoUser
     * @param errors
     * @return
     */
    @PostMapping
    public DemoUser createUser(@Valid @RequestBody DemoUser demoUser, BindingResult errors) {
        if(errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(error -> log.error("异常: {}" , error.getDefaultMessage()));
            throw new UserExpection(ResultEnums.USER_CREATE_ERROR.getCode(),ResultEnums.USER_CREATE_ERROR.getMsg());
        }
        log.info("创建用户参数：用户ID={}, 用户名称={}, 用户密码={}, 用户生日={}", demoUser.getId(), demoUser.getUsername(), demoUser.getPwd(), demoUser.getBirthday());
        demoUser.setId(1);
        return demoUser;
    }

    /**
     * 修改用户
     * @param demoUser
     * @param errors
     * @return
     */
    @PutMapping("/{id:\\d+}")
    public DemoUser updateUser(@Valid @RequestBody DemoUser demoUser, BindingResult errors) {
        if(errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(error -> {
                log.error("修改异常：{}" , error.getDefaultMessage());
            });
        }
        log.info("创建用户参数：用户ID={}, 用户名称={}, 用户密码={}, 用户生日={}", demoUser.getId(), demoUser.getUsername(), demoUser.getPwd(), demoUser.getBirthday());
        demoUser.setId(1);
        return demoUser;
    }

    /**
     * 删除
     * @param id
     */
    @DeleteMapping("/{id:\\d+}")
    public void deleteUser(@PathVariable String id) {
        log.info("删除用户: {}" , id);
    }

    @PostMapping(value = "/regist")
    public void regist(DemoUser user, HttpServletRequest request) {
        //不管注册还是绑定，都会拿到用户的唯一标识
        String userId = user.getUsername();
        // 将userId 和 session中的Social信息 绑定并插入到数据库DB
        providerSignInUtils.doPostSignUp(userId, new ServletWebRequest(request));

        //app 注册调用方法
        //appSignUpUtils.doPostSignUp(new ServletWebRequest(request), userId);
    }

    /**
     * 从SecurityContextHolder.getContext()中获取用户认证信息
     * @return
     */
    @ApiOperation(value="浏览器获取认证用户信息")
    @GetMapping(value = "/authUser")
    public Object getAuthUser(@AuthenticationPrincipal UserDetails user) {
        /**
         * 第一种方式： 无参数情况下： return SecurityContextHolder.getContext().getAuthentication();
         *
         * 第二种 方法参数：public Object getAuthUser（ Authentication authentication ）， springMVC会自主的去SecurityContext中去找authentication
         * return authentication;
         * 一二两种方法返回的都是全部认证信息
         *
         * 第三种： 只返回最终放入securityContext中的userDetails信息
         */
        return user;
    }

}
