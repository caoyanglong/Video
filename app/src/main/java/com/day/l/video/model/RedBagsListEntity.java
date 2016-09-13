package com.day.l.video.model;

import java.util.List;

/**
 * Created by CYL on 16-9-10.
 * email:670654904@qq.com
 */
public class RedBagsListEntity {

    /**
     * src : http://115.28.9.92/eliu/data/upload/20160908/57d12af22a1f1.jpg
     * describe : 新年抢红包
     * link : https://www.baidu.com/
     * number : 20000
     * stock : 20000
     * worth : 0.1
     * redbag_id : 17
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String src;
        private String describe;
        private String link;
        private String number;
        private String stock;
        private double worth;
        private String redbag_id;

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

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public double getWorth() {
            return worth;
        }

        public void setWorth(double worth) {
            this.worth = worth;
        }

        public String getRedbag_id() {
            return redbag_id;
        }

        public void setRedbag_id(String redbag_id) {
            this.redbag_id = redbag_id;
        }
    }
}
