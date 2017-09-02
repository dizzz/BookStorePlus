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
        System.out.print("vsb");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username","error.user","Please use between 6 and 32 characters.");
        }
        if (userService.quaryWithUserName(user.getUsername()) != null) {
            errors.rejectValue("username","error.user","Someone already has that username.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password","error.user","Try one with at least 8 characters.");

        }
        if (!user.getPasswordComfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordComfirm", "error.user","These passwords don't match.");
        }
    }
}
