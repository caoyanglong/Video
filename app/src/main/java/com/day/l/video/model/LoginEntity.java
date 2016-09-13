package com.day.l.video.model;

/**
 * Created by CYL on 16-9-10.
 * email:670654904@qq.com
 */
public class LoginEntity {

    /**
     * status : 1
     * msg : 验证成功
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.W3siaWF0IjoxNDczNDgyMDIzLCJqdGkiOiJaV3hwZFRFd01UUT0iLCJuYmYiOjE0NzM0ODIwMzMsImV4cCI6MTQ3MzQ4MjA5MywiZGF0YSI6eyJ1c2VyX2lkIjoiMTAxNCIsInVzZXJuYW1lIjoiY3lsMzQ1In19XQ.6tVffwfbWGIafYm48BXkXvDGExBEqF1PicysPNA0tK4
     * expiration_time : 1474086823
     */

    private int status;
    private String msg;
    private String token;
    private int expiration_time;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpiration_time() {
        return expiration_time;
    }

    public void setExpiration_time(int expiration_time) {
        this.expiration_time = expiration_time;
    }
}
