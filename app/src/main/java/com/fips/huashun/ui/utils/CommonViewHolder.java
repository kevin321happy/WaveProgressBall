package com.fips.huashun.ui.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;


/**
 * ViewHolder的作用： 减少FindViewById的次数
 * 如何实现的： 把找好的View放在成员变量中
 * <p/>
 * 通用的解决： 使用集合代替多个成员变量
 *
 */
public class CommonViewHolder {
    public final View convertView;
    private Map<Integer, View> views = new HashMap<>();

    // 构造方法中传入了 convertView 就不用put了，就可以在get的时候put，减少代码
    public CommonViewHolder(View convertView) {
        this.convertView = convertView;
    }

//        public void putView(int id, View v) {
//            views.put(id, v);
//        }

    public View getView(int id) {
//            return views.get(id);
        if (views.get(id) == null) {
            views.put(id, convertView.findViewById(id));
//                Log.d("getView" ,"第一次找");
        }
//            Log.d("getView" ,"直接复用");
        return views.get(id);
    }

    public TextView getTv(int id) {
        return (TextView) getView(id);
    }

    public ImageView getIv(int id) {
        return (ImageView) getView(id);
    }

    public static CommonViewHolder createCVH(View convertView, int layout, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
            CommonViewHolder commonViewHolder = new CommonViewHolder(convertView);
            convertView.setTag(commonViewHolder);
        }
        return (CommonViewHolder) convertView.getTag();

    }
}
