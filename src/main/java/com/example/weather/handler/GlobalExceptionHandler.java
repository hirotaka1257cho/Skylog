package com.example.weather.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public String handleEmptyResult(EmptyResultDataAccessException e) {
        log.error("メモが見つかりません", e);
        return "error/notfound"; 
    }

    @ExceptionHandler(Exception.class)
        public String handleException(Exception e){
            log.error("予期しないエラーが発生しました", e);
            return "error";
        }
}
