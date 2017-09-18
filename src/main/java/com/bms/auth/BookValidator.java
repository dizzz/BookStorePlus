package com.bms.auth;

import com.bms.model.Book;
import com.bms.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {
    @Autowired
    private BookService bookService;
    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }
    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book)o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty","不能为空");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "author", "NotEmpty","不能为空");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ISBN", "NotEmpty","不能为空");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "NotEmpty","不能为空");
        if (bookService.queryBookByISBN(book.getISBN()) != null) {
            errors.rejectValue("title","error.user","不能添加已有图书");
        }

    }
}
