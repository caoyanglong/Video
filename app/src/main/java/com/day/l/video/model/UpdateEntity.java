package com.day.l.video.model;

/**
 * Created by cyl
 * on 2016/9/11.
 * email:670654904@qq.com
 */
public class UpdateEntity {


    /**
     * status : 1
     * data : {"version":"16_09_11_1846","link":"","remark":"9月11日版本 添加xxx"}
     * msg : 这是最新版本
     */

    private int status;
    /**
     * version : 16_09_11_1846
     * link :
     * remark : 9月11日版本 添加xxx
     */

    private DataBean data;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        private String version;
        private String link;
        private String remark;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
