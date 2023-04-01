package com.rgsx.utils;

import com.alibaba.fastjson.JSONObject;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @Description : java原生客户端忽略证书
 * @Version : V1.0.0
 * @Date : 2023/1/3 11:38
 */
public class JavaHttpUtil {

    /**
     * 发送https请求
     *
     * @param url           url
     * @param requestMethod 请求方式
     * @param param         请求参数
     * @return 返回值
     */
    public String sendHttpsRequest(String url, String requestMethod, String param) {
        StringBuilder result = new StringBuilder();
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            }, new SecureRandom());
            URL console = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
            // GET/POST
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            if (null != param) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(param.getBytes("UTF-8"));
                outputStream.close();
            }

            // 设置证书忽略相关操作
            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            });
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String ret = "";
            while ((ret = br.readLine()) != null) {
                if (ret != null && !ret.trim().equals("")) {
                    result.append(new String(ret.getBytes("utf-8"), "utf-8"));
                }
            }
            conn.disconnect();
            br.close();
        } catch (NoSuchAlgorithmException | KeyManagementException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return result.toString();
    }

    // http协议访问方法
    public String sendHttpRequest(String url, String requestMethod, String param) {
        StringBuilder result = new StringBuilder();
        try {
            URL console = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) console.openConnection();
            // GET/POST
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            if (null != param) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(param.getBytes("UTF-8"));
                outputStream.close();
            }

            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String ret = "";
            while ((ret = br.readLine()) != null) {
                if (ret != null && !ret.trim().equals("")) {
                    result.append(new String(ret.getBytes("utf-8"), "utf-8"));
                }
            }
            conn.disconnect();
            br.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return result.toString();
    }

    public static void main(String[] args) {
        final JavaHttpUtil javaHttpUtil = new JavaHttpUtil();
        JSONObject js = new JSONObject();
        js.put("user", "xioamin");
        js.put("ip", "127.0.0.1");
        final String result = javaHttpUtil.sendHttpRequest("https://abc.test:5808/getToken", "POST", js.toString());
        System.out.println(result);

        final String get = javaHttpUtil.sendHttpsRequest("https://www.baidu.com/", "GET", null);
        System.out.println(get);
    }
}
