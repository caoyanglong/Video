package com.day.l.video.model;

/**
 * Created by CYL on 16-9-10.
 * email:670654904@qq.com
 */
public class RegisterEntity {

    /**
     * status : 5004
     * msg : user_login已经存在！
     */

    private int status;
    private String msg;

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
}
