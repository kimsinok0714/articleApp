package com.example.boardapp.controller.formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.springframework.format.Formatter;

import lombok.extern.slf4j.Slf4j;


// LocalDateTime을  문자열로 변환하거나 문자열을 LocalDateTime 로 변환한다.
@Slf4j
public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

    public LocalDateTimeFormatter() {

    }


    // jakson libray에는 적용되지 않는다.
    // 컨트롤러에서 반환하는 LocalDateTime 객체를 지정된 형식(yyyy-MM-dd HH:mm:ss)의 문자열로 변환한다.   
    @Override
    public String print(LocalDateTime object, Locale locale) {
       log.info("{}", "LocalDateTimeFormatter print -----------------------");  
       return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(object);
    
    }


    // 사용자로부터 yyyy-MM-dd HH:mm:ss 형식의 문자열 입력을 받을 경우, 이를 LocalDateTime 객체로 변환한다.
    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {       

        return LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    }
}