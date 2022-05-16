package com.springSecurity.utils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Map;

@Component
public class LocationUtils {

    /**日志对象*/
    private static final Logger logger = LoggerFactory.getLogger(LocationUtils.class);


    private String KEY="f1f696f884d2aaa7483bbc7588b477c0";

    /**
     * 地理编码的url
     */
    public final String LOCATION_URL = "https://restapi.amap.com/v3/geocode/geo?parameters";

    /**
     * 逆地理编码的url
     */
    public final String COUNTER_LOCATION_URL = "https://restapi.amap.com/v3/geocode/regeo?parameters";

    /**
     * 发送get请求
     * @return
     */
    public JSONObject getLocation(Map<String, String> params){

        JSONObject jsonObject = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 创建URI对象，并且设置请求参数
        try {
            URI uri = getBuilderLocation(LOCATION_URL, params);
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            CloseableHttpResponse response = httpclient.execute(httpGet);

            // 判断返回状态是否为200
            jsonObject = getLocation(response);
            httpclient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    /**
     * 根据地址获取到经纬度
     * @param response
     * @return
     */
    private static JSONObject getLocation(CloseableHttpResponse response) throws Exception{
        JSONObject geocode = null;
        // 判断返回状态是否为200
        if (response.getStatusLine().getStatusCode() == 200) {

            System.out.println(response.getEntity());
            String content = EntityUtils.toString(response.getEntity(), "UTF-8");
            logger.info("调用高德地图接口返回的结果为:{}",content);
            JSONObject jsonObject = (JSONObject) JSONObject.parse(content);
            JSONArray geocodes = (JSONArray) jsonObject.get("geocodes");
            geocode = (JSONObject) geocodes.get(0);

            logger.info("返回的结果为:{}",JSONObject.toJSONString(geocode));
        }
        return geocode;
    }

    /**
     * 地理编码封装URI
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    private URI getBuilderLocation(String url, Map<String, String> params) throws Exception{
        // 详细地址
        String address = params.get("address");
        String city = params.get("city");

        URIBuilder uriBuilder = new URIBuilder(url);
        // 公共参数
        uriBuilder.setParameter("key", KEY);
        uriBuilder.setParameter("address", address);
        uriBuilder.setParameter("city", city);

        logger.info("请求的参数为:{}", JSONObject.toJSONString(uriBuilder));
        URI uri = uriBuilder.build();
        return uri;
    }

    /**
     * 逆地理编码
     * @return
     */
    public JSONObject getCounterLocation(Map<String, String> params){

        JSONObject jsonObject = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 创建URI对象，并且设置请求参数
        try {
            URI uri = getBuilderCounterLocation(COUNTER_LOCATION_URL, params);
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            CloseableHttpResponse response = httpclient.execute(httpGet);

            // 判断返回状态是否为200
            jsonObject = getCounterLocation(response);
            httpclient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    /**
     * 逆地理编码封装URI
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    private URI getBuilderCounterLocation(String url, Map<String, String> params) throws Exception{
        // 详细地址
        String location = params.get("location");

        URIBuilder uriBuilder = new URIBuilder(url);
        // 公共参数
        uriBuilder.setParameter("key", KEY);
        uriBuilder.setParameter("location", location);

        logger.info("请求的参数为:{}", JSONObject.toJSONString(uriBuilder));
        URI uri = uriBuilder.build();
        return uri;
    }

    /**
     * 根据地址获取到经纬度
     * @param response
     * @return
     */
    private static JSONObject getCounterLocation(CloseableHttpResponse response) throws Exception{
        JSONObject regeocode = null;
        // 判断返回状态是否为200
        if (response.getStatusLine().getStatusCode() == 200) {
            String content = EntityUtils.toString(response.getEntity(), "UTF-8");
            logger.info("调用高德地图接口返回的结果为:{}",content);
            JSONObject jsonObject = (JSONObject) JSONObject.parse(content);
            regeocode =  (JSONObject)jsonObject.get("regeocode");

            logger.info("返回的结果为:{}",JSONObject.toJSONString(regeocode));
        }
        return regeocode;
    }
}


