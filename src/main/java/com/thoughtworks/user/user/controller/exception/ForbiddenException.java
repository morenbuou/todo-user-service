package com.thoughtworks.user.user.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "authentication forbidden")
public class ForbiddenException extends Exception {

}