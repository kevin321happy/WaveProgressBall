package com.fips.huashun.modle.bean;

/**
 * 功能：课程评价列表类
 * Created by Administrator on 2016/9/6.
 */
public class CommentInfo {
    // 评价内容
    private String context;
    // 评论日期
    private String create_date;
    // 打分
    private String score;
    // 学习进度
    private String sturate;
    // 用户照片
    private String userimg;
    // 用户名
    private String username;

    public void setContext(String context){
        this.context = context;
    }
    public String getContext(){
        return this.context;
    }
    public void setCreate_date(String create_date){
        this.create_date = create_date;
    }
    public String getCreate_date(){
        return this.create_date;
    }
    public void setScore(String score){
        this.score = score;
    }
    public String getScore(){
        return this.score;
    }
    public void setSturate(String sturate){
        this.sturate = sturate;
    }
    public String getSturate(){
        return this.sturate;
    }
    public void setUserimg(String userimg){
        this.userimg = userimg;
    }
    public String getUserimg(){
        return this.userimg;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }
}
