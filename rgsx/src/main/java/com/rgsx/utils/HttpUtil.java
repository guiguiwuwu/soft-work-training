package com.rgsx.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class HttpUtil {
    public static JSONObject doPost(String url,File file , HashMap<String,String> map) throws NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        //CloseableHttpClient忽略证书模式
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustAllStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        }).build();

        CloseableHttpClient aDefault = HttpClients.custom().setSSLContext(sslContext).
                setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        HttpPost httpPost = new HttpPost(url);

        //设置form-data格式数据
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        //builder.setCharset(Charset.forName("uft-8"));//设置请求的编码格式

        //遍历map
        Set<String> set = map.keySet();
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            String value = map.get(key);
            builder.addTextBody(key,value, ContentType.TEXT_PLAIN);
        }
        builder.addBinaryBody("file",file);


        // 把数据加到HTTP的post请求中
        HttpEntity multipart = builder.build();
        httpPost.setEntity(multipart);
        //获取响应
        CloseableHttpResponse response = aDefault.execute(httpPost);
        //获取响应体
        HttpEntity responseEntity = response.getEntity();
        //将响应体转化为字符串
        String sResponse= EntityUtils.toString(responseEntity, "UTF-8");
        JSONObject jsonObject = JSONObject.parseObject(sResponse);

        //打印 + 返回
        System.out.println(jsonObject.toJSONString());
        return jsonObject;

    }
}
