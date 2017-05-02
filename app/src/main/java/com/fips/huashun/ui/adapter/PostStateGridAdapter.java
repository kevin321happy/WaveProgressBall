package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fips.huashun.R;
import com.fips.huashun.ui.utils.UploadImageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 2017/1/18.
 */
public class PostStateGridAdapter extends BaseAdapter {

    private LayoutInflater inflater; // 视图容器
    private int selectedPosition = -1;// 选中的位置
    private boolean shape;
    public List<String> items = new ArrayList<>();

    public PostStateGridAdapter.Callback mCallback;
    LayoutInflater layoutInflater;
    private Context mContext;

    public PostStateGridAdapter(Context context, ArrayList<String> selectPath) {
        this.mContext = context;
        this.items = selectPath;
        layoutInflater = LayoutInflater.from(context);
    }


    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public PostStateGridAdapter(PostStateGridAdapter.Callback callback) {
        this.mCallback = callback;
    }

    public void setListItems(ArrayList<String> listItems) {
        this.items.clear();
        this.items = listItems;

    }

    @Override
    public int getCount() {
        return items.size() == 0 ? 1 : items.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return items.size() == 0 ? null : items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int coord = position;
        convertView = View.inflate(mContext, R.layout.release_leave_item, null);
        ImageView iv_add = (ImageView) convertView.findViewById(R.id.iv_add);
        ImageView iv_cancle = (ImageView) convertView.findViewById(R.id.iv_cancle);
        RelativeLayout rt_layout= (RelativeLayout) convertView.findViewById(R.id.relativeLayout);
        //设置图片的宽度永远为屏幕的五分之一的宽度
        int widthPixels = mContext.getResources().getDisplayMetrics().widthPixels;//屏幕宽度
        ViewGroup.LayoutParams params = rt_layout.getLayoutParams();
        params.width=widthPixels/5;
        params.height=widthPixels/5;
        rt_layout.setLayoutParams(params);
        if (position < items.size()) {
            iv_add.setImageBitmap(UploadImageUtil.getimage(items.get(position), 150f, 150f));
            iv_cancle.setVisibility(View.VISIBLE);
        } else {
            iv_add.setBackgroundResource(R.drawable.xinjian_);//最后一个显示加号图片
            iv_cancle.setVisibility(View.GONE);
        }
        //点击去除按钮
        iv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.click(v, position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }


    public class ViewHolder {
        public ImageView image;
        public ImageView iv_cancle;
    }

    public interface Callback {
        void click(View v, int posstion);
    }
}
