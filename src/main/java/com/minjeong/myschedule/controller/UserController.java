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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    private final ObjectMapper objectMapper;

    public UserController(UserService userService) {
        this.userService = userService;
        this.objectMapper = new ObjectMapper();
    }


    @GetMapping("/user/login-page")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/user/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/user/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto requestDto) {
        try {
            userService.signup(requestDto);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
        return ResponseEntity.ok("회원가입이 성공적으로 수행되었습니다.");

    }

    @PostMapping("/user/login")
    public void login(@RequestBody LoginRequestDto requestDto, HttpServletResponse res) {
        try {
            userService.login(requestDto, res);

            CustomResponse customResponse  = new CustomResponse();
            customResponse.setMessage("로그인이 성공적으로 수행되었습니다.");
            customResponse.setCode(HttpStatus.OK.toString());

            String objToJson=objectMapper.writeValueAsString(customResponse);

            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(objToJson);

        } catch (Exception e) {
            e.printStackTrace();
        }


//HttpHeaders headers = new HttpHeaders();
//        headers.add("message", "로그인이 성공적으로 수행되었습니다.");
//        headers.add("statusCode", HttpStatus.OK.toString());
//        return ResponseEntity.ok(headers);
//        res.setStatus(HttpStatus.OK.value());
//        res.setHeader("message", "로그인이 성공적으로 수행되었습니다.");

    }
}