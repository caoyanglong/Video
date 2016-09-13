package com.day.l.video.model;

import net.tsz.afinal.annotation.sqlite.Id;

/**
 * Created by cyl
 * on 2016/9/12.
 * email:670654904@qq.com
 */
public class AppBeanEntitiy {
    @Id(column="id")
    private String id;
    private String name;
    private String src;
    private String describe;
    private String link;
    private int progres;
    private long downloadID;
    private String path;
    private int status;

    public int getProgres() {
        return progres;
    }

    public void setProgres(int progres) {
        this.progres = progres;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public long getDownloadID() {
        return downloadID;
    }

    public void setDownloadID(long downloadID) {
        this.downloadID = downloadID;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
