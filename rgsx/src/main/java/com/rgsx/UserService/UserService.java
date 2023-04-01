package com.rgsx.UserService;

import com.rgsx.vo.ResultVo;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public interface UserService {
    public ResultVo login(File file);
    public ResultVo register(File file);
}
