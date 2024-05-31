package com.minjeong.myschedule.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minjeong.myschedule.dto.LoginRequestDto;
import com.minjeong.myschedule.dto.SignupRequestDto;
import com.minjeong.myschedule.entity.User;
import com.minjeong.myschedule.service.CustomResponse;
import com.minjeong.myschedule.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/user/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        String errMsg="";

        if (fieldErrors.size() > 0) {
            for(FieldError fieldError : fieldErrors) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
                errMsg += (fieldError.getDefaultMessage()+"\n");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errMsg);
        }
        try {
            userService.signup(requestDto);

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
        return ResponseEntity.ok("회원가입이 성공적으로 수행되었습니다.");

    }
}