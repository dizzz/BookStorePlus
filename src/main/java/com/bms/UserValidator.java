package com.bms;

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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "loginId", "NotEmpty");
        if (user.getLoginId().length() < 6 || user.getLoginId().length() > 32) {
            errors.rejectValue("loginId","error.user","Please use between 6 and 32 characters.");
        }
        if (userService.quaryWithUserName(user.getLoginId()) != null) {
            errors.rejectValue("loginId","error.user","Someone already has that username.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "loginPwd", "NotEmpty");
        if (user.getLoginPwd().length() < 8 || user.getLoginPwd().length() > 32) {
            errors.rejectValue("loginPwd","error.user","Try one with at least 8 characters.");

        }
        if (!user.getPwdComfirm().equals(user.getLoginPwd())) {
            errors.rejectValue("pwdComfirm", "error.user","These passwords don't match.");
        }
        //TODO
    }
}
