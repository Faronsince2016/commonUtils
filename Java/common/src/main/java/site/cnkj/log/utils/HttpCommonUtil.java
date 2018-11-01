package cn.migu.log.utils;

import cn.migu.log.utils.domain.BaseResult;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/*
 * @version 1.0 created by LXW on 2018/10/25 10:04
 */
public class HttpCommonUtil {

    /**
     * 获取客户端请求的真实IP地址
     * @param request 客户端请求信息
     * @return 客户端地址
     */
    public static String getCustomsAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if(ip.equals("127.0.0.1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip= inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ip != null && ip.length() > 15){
            if(ip.indexOf(",")>0){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }
        return ip;
    }

    /**
     * 获取本地ip
     * @return
     */
    private static  String getLocalIP(){
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String ip=addr.getHostAddress().toString();
        return ip;
    }

    /**
     * 获取本地机器名
     * @return
     */
    private static  String getLocalHostName(){
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String hostName=addr.getHostName().toString();
        return hostName;
    }

    /**
     * 表单发送HTTP请求
     * @param restTemplate
     * @param req_payload
     * @param url
     */
    public  static void postVieForm(RestTemplate restTemplate,MultiValueMap<String, Object> req_payload,String url){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> requestEntity  = new HttpEntity<MultiValueMap<String, Object>>(req_payload, headers);
        restTemplate.postForEntity(url, requestEntity, String.class);
    }

    /**
     * 普通JSON方式发送HTTP请求
     * @param restTemplate
     * @param object
     * @param url
     */
    public static void postVieObject(RestTemplate restTemplate,Object object,String url){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<Object> requestEntity  = new HttpEntity<Object>(object, headers);
        restTemplate.postForEntity(url, requestEntity, String.class);
    }


    /**
     * 普通JSON方式发送HTTP请求
     * @param restTemplate
     * @param object
     * @param url
     * @return {"code":"","desc":""} Basereult对象
     */
    public static BaseResult postVieObjectReturn(RestTemplate restTemplate, Object object, String url){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<Object> requestEntity  = new HttpEntity<Object>(object, headers);
        return restTemplate.postForEntity(url, requestEntity, BaseResult.class).getBody();
    }


    /**
     * 表单发送HTTP请求
     * @param restTemplate
     * @param req_payload
     * @param url
     * @return response的主体
     */
    public  static String postVieFormReturn(RestTemplate restTemplate, MultiValueMap<String, Object> req_payload, String url){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> requestEntity  = new HttpEntity<MultiValueMap<String, Object>>(req_payload, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
        return responseEntity.getBody();
    }

}
