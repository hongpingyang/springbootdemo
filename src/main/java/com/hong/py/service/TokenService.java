package com.hong.py.service;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {

    public String createToken();

    public boolean checkToken(HttpServletRequest request);

}
