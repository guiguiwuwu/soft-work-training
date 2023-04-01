package com.rgsx.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpSendFile {

    /**
     * HttpClient调用文件发送接口
     * @param file  上传的文件
     * @return
     */

    public static Map<String, Object> call_sendFile(File file,String name ,String region){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        Map<String, Object> responseMap = new HashMap<>();
        String result="";
        try{
            //服务端地址
            HttpPost httpPost = new HttpPost("https://1.14.98.160:4000/api/file_rec_identify");

            //创建接口需要的参数
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            ContentType fileCType = ContentType.create("application/octet-stream", "UTF-8");
//            entityBuilder.addPart("file", new FileBody(file, fileCType));
//            entityBuilder.addPart("name", new StringBody(name));
//            entityBuilder.addPart("region", new StringBody(region));
            entityBuilder.addPart("pageSize", new StringBody("10"));
            entityBuilder.addPart("pageIndex", new StringBody("1"));
            HttpEntity entity = entityBuilder.build();
            httpPost.setEntity(entity);

            //调用跨网文件发送接口
            HttpResponse response = httpclient.execute(httpPost);
            //获取响应信息
            HttpEntity responseEntity = response.getEntity();
            if(responseEntity != null){
                result = EntityUtils.toString(responseEntity, "UTF-8");
            }
            responseMap.put("code", response.getStatusLine().getStatusCode());
            responseMap.put("result", result);
        } catch (IOException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return responseMap;
    }

}

