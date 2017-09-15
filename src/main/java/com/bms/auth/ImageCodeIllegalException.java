package com.bms.auth;

import org.springframework.security.core.AuthenticationException;

public class ImageCodeIllegalException extends AuthenticationException {


    public ImageCodeIllegalException(String msg) {
        super(msg);
    }
}
