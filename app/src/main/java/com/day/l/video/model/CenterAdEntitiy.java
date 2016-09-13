package com.day.l.video.model;

/**
 * Created by CYL on 16-9-10.
 * email:670654904@qq.com
 */
public class CenterAdEntitiy {

    /**
     * src : http://115.28.9.92/eliu/data/upload/20160908/57d120a63c534.jpg
     * describe : 及家居
     * link : https://hao.360.cn/
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String src;
        private String describe;
        private String link;

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
