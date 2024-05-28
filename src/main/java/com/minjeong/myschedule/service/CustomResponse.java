package com.minjeong.myschedule.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class CustomResponse {
    private String message;
    private String code;

}
