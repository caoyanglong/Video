package com.day.l.video.model;

import java.util.List;

/**
 * Created by cyl
 * on 2016/9/18.
 * email:670654904@qq.com
 */
public class VideoEntity {

    private List<?> AD;
    /**
     * ID : e862d838560a4e6b8d0e4d3a252e3b4f
     * Name : 封神传奇
     * ImageUrl : /Image/e862d838560a4e6b8d0e4d3a252e3b4f
     * AppType : 1
     * Score : 1
     */

    private List<VideoBean> Video;

    public List<?> getAD() {
        return AD;
    }

    public void setAD(List<?> AD) {
        this.AD = AD;
    }

    public List<VideoBean> getVideo() {
        return Video;
    }

    public void setVideo(List<VideoBean> Video) {
        this.Video = Video;
    }

    public static class VideoBean {
        private String ID;
        private String Name;
        private String ImageUrl;
        private String AppType;
        private String Score;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getImageUrl() {
            return ImageUrl;
        }

        public void setImageUrl(String ImageUrl) {
            this.ImageUrl = ImageUrl;
        }

        public String getAppType() {
            return AppType;
        }

        public void setAppType(String AppType) {
            this.AppType = AppType;
        }

        public String getScore() {
            return Score;
        }

        public void setScore(String Score) {
            this.Score = Score;
        }
    }
}
