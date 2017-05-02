package com.fips.huashun.modle.bean;

/**
 * Created by Administrator on 2016/9/9.
 */
public class FilterData {
    private String coursetypename;

    private String type_id;

    public void setCoursetypename(String coursetypename){
        this.coursetypename = coursetypename;
    }
    public String getCoursetypename(){
        return this.coursetypename;
    }
    public void setType_id(String type_id){
        this.type_id = type_id;
    }
    public String getType_id(){
        return this.type_id;
    }
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
