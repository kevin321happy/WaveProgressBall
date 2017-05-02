package com.fips.huashun.modle.bean;

/**
 * Created by Administrator on 2016/12/13.
 */
public class QuestionInfo {
    private String id;

    private String title;

    private String start_time;

    private String end_time;

    private String description;

    private int is_anwser;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setStart_time(String start_time){
        this.start_time = start_time;
    }
    public String getStart_time(){
        return this.start_time;
    }
    public void setEnd_time(String end_time){
        this.end_time = end_time;
    }
    public String getEnd_time(){
        return this.end_time;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setIs_anwser(int is_anwser){
        this.is_anwser = is_anwser;
    }
    public int getIs_anwser(){
        return this.is_anwser;
    }

}
