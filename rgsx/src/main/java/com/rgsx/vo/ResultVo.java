package com.rgsx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultVo {
    private String msg;//信息
    private String code;//代码
    private Object data;//数据

    public ResultVo success(String msg,Object data){
        return new ResultVo(msg,"0",data);
    }

    public ResultVo fail(Exception e){
        return new ResultVo(e.getMessage(),"1",null);
    }

    public ResultVo fail(String msg , String code){
        return new ResultVo(msg,code,null);
    }

    @Override
    public String toString() {
        return "ResultVo{" +
                "msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }
}
