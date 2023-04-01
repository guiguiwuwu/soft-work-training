package com.rgsx.controller;

import com.rgsx.utils.HttpSendFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.UnsupportedEncodingException;

@RestController
@ResponseBody
public class UserController {
    @GetMapping("/test")
    public String test() throws UnsupportedEncodingException {
        File file = new File("D\\1.wav");
        HttpSendFile.call_sendFile(file,"","");
        return "测试成功";
    }
}
