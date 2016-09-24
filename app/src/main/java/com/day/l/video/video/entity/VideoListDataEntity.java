package com.day.l.video.video.entity;

import java.util.List;

/**
 * Created by cyl
 * on 2016/9/19.
 * email:670654904@qq.com
 */
public class VideoListDataEntity {


    /**
     * counts : 3712
     * pageCount : 3712
     * pageSize : 1
     * list : [{"ID":"e862d838560a4e6b8d0e4d3a252e3b4f","AppName":"封神传奇","AppType":"1","ImageUrl":"/UpLoadFiles/Image/20160908/ZW_20160908131821493_5341.jpg","AppPath":"/v/e862d838560a4e6b8d0e4d3a252e3b4f/","Score":"1","Star":"7","DownCount":"0","AppSize":"1","AppTime":"0","Author":" 李连杰 范冰冰 黄晓明 Angelababy 古天乐","AppArea":"华语","AppYear":"2016-09-08","Content":"三千年前","AddDate":"2016-09-08 13:19:04"}]
     */

    private String counts;
    private String pageCount;
    private String pageSize;
    private String pageIndex;
    private String AppType;
    private String k;
    /**
     * ID : e862d838560a4e6b8d0e4d3a252e3b4f
     * AppName : 封神传奇
     * AppType : 1
     * ImageUrl : /UpLoadFiles/Image/20160908/ZW_20160908131821493_5341.jpg
     * AppPath : /v/e862d838560a4e6b8d0e4d3a252e3b4f/
     * Score : 1
     * Star : 7
     * DownCount : 0
     * AppSize : 1
     * AppTime : 0
     * Author :  李连杰 范冰冰 黄晓明 Angelababy 古天乐
     * AppArea : 华语
     * AppYear : 2016-09-08
     * Content : 三千年前
     * AddDate : 2016-09-08 13:19:04
     */

    private List<ListBean> list;

    public String getCounts() {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public List<ListBean> getList() {
        return list;
    }

    public String getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getAppType() {
        return AppType;
    }

    public void setAppType(String appType) {
        AppType = appType;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String ID;
        private String AppName;
        private String AppType;
        private String ImageUrl;
        private String AppPath;
        private String Score;
        private String Star;
        private String DownCount;
        private String AppSize;
        private String AppTime;
        private String Author;
        private String AppArea;
        private String AppYear;
        private String Content;
        private String AddDate;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getAppName() {
            return AppName;
        }

        public void setAppName(String AppName) {
            this.AppName = AppName;
        }

        public String getAppType() {
            return AppType;
        }

        public void setAppType(String AppType) {
            this.AppType = AppType;
        }

        public String getImageUrl() {
            return ImageUrl;
        }

        public void setImageUrl(String ImageUrl) {
            this.ImageUrl = ImageUrl;
        }

        public String getAppPath() {
            return AppPath;
        }

        public void setAppPath(String AppPath) {
            this.AppPath = AppPath;
        }

        public String getScore() {
            return Score;
        }

        public void setScore(String Score) {
            this.Score = Score;
        }

        public String getStar() {
            return Star;
        }

        public void setStar(String Star) {
            this.Star = Star;
        }

        public String getDownCount() {
            return DownCount;
        }

        public void setDownCount(String DownCount) {
            this.DownCount = DownCount;
        }

        public String getAppSize() {
            return AppSize;
        }

        public void setAppSize(String AppSize) {
            this.AppSize = AppSize;
        }

        public String getAppTime() {
            return AppTime;
        }

        public void setAppTime(String AppTime) {
            this.AppTime = AppTime;
        }

        public String getAuthor() {
            return Author;
        }

        public void setAuthor(String Author) {
            this.Author = Author;
        }

        public String getAppArea() {
            return AppArea;
        }

        public void setAppArea(String AppArea) {
            this.AppArea = AppArea;
        }

        public String getAppYear() {
            return AppYear;
        }

        public void setAppYear(String AppYear) {
            this.AppYear = AppYear;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public String getAddDate() {
            return AddDate;
        }

        public void setAddDate(String AddDate) {
            this.AddDate = AddDate;
        }
    }
}
