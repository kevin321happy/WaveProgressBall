package com.fips.huashun.modle.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/6.
 */
public class CoursrdetailInfo implements Serializable{
    private Coursrdetail Coursrdetail;

    private CommentInfo comments;

    private String iscollect;

    private String isriokin;

    private String welltoken;

         //private
    //是否购买（0未购买，1已购买）
    private String buytype;

    public String getBuytype()
    {
        return buytype;
    }

    public void setBuytype(String buytype)
    {
        this.buytype = buytype;
    }

    public void setCoursrdetail(Coursrdetail Coursrdetail){
        this.Coursrdetail = Coursrdetail;
    }
    public Coursrdetail getCoursrdetail(){
        return this.Coursrdetail;
    }
    public void setComments(CommentInfo comments){
        this.comments = comments;
    }
    public CommentInfo getComments(){
        return this.comments;
    }

    public String getIscollect() {
        return iscollect;
    }

    public void setIscollect(String iscollect) {
        this.iscollect = iscollect;
    }

    public String getIsriokin() {
        return isriokin;
    }

    public void setIsriokin(String isriokin) {
        this.isriokin = isriokin;
    }

    public void setWelltoken(String welltoken){
        this.welltoken = welltoken;
    }
    public String getWelltoken(){
        return this.welltoken;
    }
}
