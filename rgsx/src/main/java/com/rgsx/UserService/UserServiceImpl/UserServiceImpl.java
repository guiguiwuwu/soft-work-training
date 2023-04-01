package com.rgsx.UserService.UserServiceImpl;

import com.alibaba.fastjson.JSONObject;
import com.rgsx.UserService.UserService;
import com.rgsx.utils.HttpUtil;
import com.rgsx.vo.ResultVo;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public ResultVo login(File file) {
        try {
            //放入参数
            HashMap<String, String> map = new HashMap<>();
            map.put("name","");
            map.put("region","");

            JSONObject jsonObject = HttpUtil.doPost(
                    "https://1.14.98.160:4000/api/file_rec_identify",
                    file,map);

            HashMap<String, Object> result = new HashMap<>();
            result.put("identify",jsonObject.get("identify"));
            result.put("name",jsonObject.get("name"));
            return ResultVo.success("成功",result);

        } catch (Exception e) {
            return ResultVo.fail(e);
        }
    }

    @Override
    public ResultVo register(File file) {
        return null;
    }
}
