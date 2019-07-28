package com.security.web.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.security.validator.MyConstraint;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * @description: 用户
 * @author: sca
 * @create: 2019-07-19 14:29
 **/
public class DemoUser {

    public interface userSimpleView {};

    public interface userDetailView extends userSimpleView {};

    private int id;
    // 用户名
    @NotNull
    @MyConstraint(message = "自定义注解测试")
    @ApiModelProperty(value = "用户名称")
    private String username;
    // 密码
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String pwd;
    // 生日
    @Past
    @ApiModelProperty(value = "生日")
    private Date birthday;

    @JsonView(value = userSimpleView.class)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonView(value = userSimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonView(value = userDetailView.class)
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @JsonView(value = userSimpleView.class)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
