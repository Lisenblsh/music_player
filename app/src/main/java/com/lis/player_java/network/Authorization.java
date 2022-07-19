package com.lis.player_java.network;

import java.util.HashMap;
import java.util.Map;

public class Authorization {
    private static final String token = "token";
    private static final String userAgent = "user agent";
    public Map<String, String> getToken(String login, String password){
        Map<String,String> authInfo = new HashMap<>();
        String token = checkToken(Authorization.token);
        String userAgent = checkUserAgent(Authorization.userAgent);
        if(token == null || userAgent == null){
            authInfo.put("ERROR", "error");
            return authInfo;
        }
        authInfo.put("TOKEN_KEY", token);
        authInfo.put("USER_AGENT", userAgent);
        return authInfo;
    }

    private String checkUserAgent(String userAgent) {
        //if was problem with this, recive null or problem, i guess
        return userAgent;
    }

    private String checkToken(String token) {
        //if was problem with this, recive null or problem, i guess
        return token;
    }
}

