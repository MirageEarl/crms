package com.vifu.crms.utils;

import com.vifu.crms.model.AcctUser;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取登录用户
 *
 * @author TangBo
 * @create 2017-02-24 11:52
 **/
public class UserContext {


    private  static HttpServletRequest httpServletRequest;

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public static final String USER_IN_SESSION = "user_in_session";

    public static void setUser(AcctUser user){
        if(user!=null){
            httpServletRequest.getSession().setAttribute(USER_IN_SESSION, user);
        }else{
            httpServletRequest.getSession().removeAttribute(USER_IN_SESSION);
        }
    }

    public static AcctUser get(){
        return (AcctUser) httpServletRequest.getSession().getAttribute(USER_IN_SESSION);
    }
}
