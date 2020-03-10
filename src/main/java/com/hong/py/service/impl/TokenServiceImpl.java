package com.hong.py.service.impl;

import com.hong.py.redis.impl.JedisDaoImpl1;
import com.hong.py.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    private final static String TOKEN_NAME = "token";

    @Autowired
    private JedisDaoImpl1 jedisDaoImpl1;

    @Override
    public String createToken() {
        UUID uuid = new UUID(0,1000);
        String token = TOKEN_NAME+"_"+uuid.toString();
        jedisDaoImpl1.set(token,token);
        return token;
    }

    @Override
    public boolean checkToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            //bu cun zai
            return true;
        }
        else {
            if (!jedisDaoImpl1.exists(token)) {
                //bu cun zai
                return false;
            }
        }
        Boolean del = jedisDaoImpl1.del(token);
        if (del) {
            return true;
        } else {
            return false;
        }

    }


}
