package com.miraiyashi.libs;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class util {

    public static String getIp(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");
        System.out.println("IP======>"+ip);
        if(ip==null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip==null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip==null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if(ip==null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if(ip==null) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    public static String hash(String data){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashKey = encoder.encode(data);
        if(hashKey ==null){
            throw new Error("해시키 생성 실패.");
        }
        return hashKey;
    }
}
