package com.bms.auth;

import com.bms.service.UserService;
import com.bms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "loginId", "NotEmpty","用户名不能为空");
        if (user.getLoginId().length() < 6 || user.getLoginId().length() > 32) {
            errors.rejectValue("loginId","error.user","请使用长度至少为6的用户名");
        }
        if (userService.queryUserByLoginId(user.getLoginId()) != null) {
            errors.rejectValue("loginId","error.user","该用户名已被注册");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "loginPwd", "NotEmpty","密码不能为空");
        if (user.getLoginPwd().length() < 6 || user.getLoginPwd().length() > 32) {
            errors.rejectValue("loginPwd","error.user","请使用长度至少为6的密码");

        }
        if (user.getPwdComfirm() == null || !user.getPwdComfirm().equals(user.getLoginPwd())) {
            errors.rejectValue("pwdComfirm", "error.user","上下密码不一致");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty","地址不能为空");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mail", "Email","请填写正确的邮箱");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty","姓名不能为空");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "NotEmpty","电话不能为空");

    }
}
