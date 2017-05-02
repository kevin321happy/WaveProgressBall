package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.modle.bean.HadSignUpModel;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 2017/2/6.
 */
public class HadSignUpAdapter extends BaseAdapter {
    private Context mContext;
    private List<HadSignUpModel.HadSignUpList> mData=new ArrayList<>();
    private ImageLoader mImageLoader;

    public HadSignUpAdapter(Context context) {
        this.mContext = context;
        mImageLoader = ImageLoader.getInstance();
    }
    @Override
    public int getCount() {
        return mData==null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData==null? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.ent_activity_applyed_item, null);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //绑定数据
        HadSignUpModel.HadSignUpList hadSignUpInfo = mData.get(position);
        mImageLoader.displayImage(hadSignUpInfo.getFilepath(), holder.iv_icon);
        holder.tv_name.setText(hadSignUpInfo.getName() + "");
        holder.tv_department.setText(hadSignUpInfo.getDepname()==null?"":hadSignUpInfo.getDepname());
        holder.tv_position.setText(hadSignUpInfo.getJobname()==null?"":hadSignUpInfo.getJobname());
        holder.tv_phone_num.setText(hideTelephone(hadSignUpInfo.getTel()) + "");
        Log.i("test",hadSignUpInfo.getTel());
        return convertView;
    }
    //隐藏电话号码中间
    private String hideTelephone(String num) {
         return num.substring(0, 3) + "****" + num.substring(7, num.length());
    }
    public void setData(List<HadSignUpModel.HadSignUpList> data) {
        mData = data;
    }
    class ViewHolder {
        private View convertView;
        private ImageView iv_icon;
        private TextView tv_name;
        private TextView tv_department;
        private TextView tv_position;
        private TextView tv_phone_num;

        ViewHolder(View convertView) {
            //绑定数据
            this.convertView = convertView;
            iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            tv_department = (TextView) convertView.findViewById(R.id.tv_department);
            tv_position = (TextView) convertView.findViewById(R.id.tv_position);
            tv_phone_num = (TextView) convertView.findViewById(R.id.tv_phone_num);
            convertView.setTag(this);
        }
    }
}
