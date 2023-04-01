package com.rgsx.controller;

import com.rgsx.UserService.UserService;
import com.rgsx.utils.FileUtil;
import com.rgsx.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@ResponseBody
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResultVo login(@RequestParam(value = "file") MultipartFile multipartFile){
        File file = FileUtil.MultipartFileToFile(multipartFile);
        return userService.login(file);

    }
}
