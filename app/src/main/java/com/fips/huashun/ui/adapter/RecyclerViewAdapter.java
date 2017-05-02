package com.fips.huashun.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fips.huashun.R;
import com.fips.huashun.ui.utils.UploadImageUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/9/22.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    public List<String> items = null;
    public RecyclerViewAdapter(List<String> items) {
        this.items = items;

    }
    private Callback mCallback;
    public RecyclerViewAdapter(Callback mCallback) {

       this.mCallback = mCallback;
    }
    public void setListItems(List<String> lit) {
        items = lit;
        notifyDataSetChanged();
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feedback_item,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder,  int position) {
//        Bitmap getimage = UploadImageUtil.getimage(items.get(position), 150f, 150f);
//        ImageLoader.getInstance().displayImage(items.get(position),viewHolder.iv_feedback);
        Log.e("333",position+"");
        viewHolder.iv_feedback.setImageBitmap(UploadImageUtil.getimage(items.get(position), 150f, 150f));
        viewHolder.iv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("333",viewHolder.getLayoutPosition()+"");
                mCallback.click(viewHolder.iv_cancle,viewHolder.getAdapterPosition());
            }
        });
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return items.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_feedback;
        public ImageView iv_cancle;
        public ViewHolder(View view){
            super(view);
            iv_feedback = (ImageView) view.findViewById(R.id.iv_feedback);
            iv_cancle = (ImageView) view.findViewById(R.id.iv_cancle);
        }
    }
    public interface Callback {
        void click(View v, int posstion);
    }
}