package com.example.toolinventorysystem.exception;

import com.example.toolinventorysystem.dto.OutputDto.ErrorOutputDto;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalException {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorOutputDto handleException(Exception exception){
        return new ErrorOutputDto(exception.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorOutputDto handleCustom(CustomException customException){
        return new ErrorOutputDto((customException.getMessage()));
    }

    @ExceptionHandler(ElementNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorOutputDto handleElementNotFound(ElementNotFound elementNotFound) {
        return new ErrorOutputDto(elementNotFound.getMessage());
    }

}
