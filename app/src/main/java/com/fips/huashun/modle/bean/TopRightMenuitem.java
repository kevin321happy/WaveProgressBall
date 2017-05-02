package com.fips.huashun.modle.bean;

/**
 * Created by kevin on 2017/1/16.
 */
public class TopRightMenuitem {
    private int icon;
    private String text;

    public TopRightMenuitem() {
    }

    public TopRightMenuitem(int icon, String text) {
        this.icon = icon;
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public String getText() {
        return text;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "icon=" + icon +
                ", text='" + text + '\'' +
                '}';
    }
}
