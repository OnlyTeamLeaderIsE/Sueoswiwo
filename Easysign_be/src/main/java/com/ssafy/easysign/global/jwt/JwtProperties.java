package com.ssafy.easysign.global.jwt;

public interface JwtProperties {
    String SECRET = "EasySign";
    int EXPIRATION_TIME = 1000*60*60*24; // 토큰 만료 기간 1일 [ms]
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
