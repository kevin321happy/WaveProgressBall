package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.modle.bean.TopRightMenuitem;
import com.fips.huashun.widgets.TopRightMenu;

import java.util.List;

/**
 * Created by kevin on 2017/1/8.
 */
public class TRMenuAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<TopRightMenuitem> menuItemList;
    private boolean showIcon;
   private TopRightMenu mTopRightMenu;
    private TopRightMenu.OnMenuItemClickListener onMenuItemClickListener;
    //private onMenuItemClickListener


    public TRMenuAdapter(Context context, TopRightMenu topRightMenu, List<TopRightMenuitem> itemList, boolean showIcon) {
        this.mContext = context;
        this.mTopRightMenu = topRightMenu;
        this.menuItemList = itemList;
        this.showIcon = showIcon;
    }

    public void setData(List<TopRightMenuitem> list) {
        this.menuItemList = list;
        notifyDataSetChanged();//刷新适配器
    }

    public void setShowIcon(boolean showIcon) {
        this.showIcon = showIcon;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.trm_item_popup_menu_list, parent, false);

        return new TopRightHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TopRightMenuitem menuItem = menuItemList.get(position);
        TopRightHolder topRightHolder = (TopRightHolder) holder;
        if (showIcon) {
            topRightHolder.mIcon.setVisibility(View.VISIBLE);
            int resId = menuItem.getIcon();
            topRightHolder.mIcon.setImageResource(resId < 0 ? 0 : resId);
        } else {
            topRightHolder.mIcon.setVisibility(View.GONE);
        }
        //设置上中下不同的背景
        if (position == 0) {
            topRightHolder.mContainer.setBackgroundDrawable(addStateDrawable(mContext, -1, R.drawable.trm_popup_top_pressed));
        } else if (position == menuItemList.size() - 1) {
            topRightHolder.mContainer.setBackgroundDrawable(addStateDrawable(mContext, -1, R.drawable.trm_popup_bottom_pressed));
        } else {
            topRightHolder.mContainer.setBackgroundDrawable(addStateDrawable(mContext, -1, R.drawable.trm_popup_middle_pressed));
        }
        final int adapterPosition = topRightHolder.getAdapterPosition();
        topRightHolder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMenuItemClickListener != null) {
                    onMenuItemClickListener.onMenuItemClick(adapterPosition);
                }
            }
        });
        topRightHolder.mText.setText(menuItem.getText() + "");

    }

    @Override
    public int getItemCount() {
        return menuItemList.size() == 0 ? 0 : menuItemList.size();
    }

    //选择器背景
    private StateListDrawable addStateDrawable(Context context, int normalId, int pressedId) {
        StateListDrawable sd = new StateListDrawable();
        Drawable normal = normalId == -1 ? null : context.getResources().getDrawable(normalId);
        Drawable pressed = pressedId == -1 ? null : context.getResources().getDrawable(pressedId);
        sd.addState(new int[]{android.R.attr.state_pressed}, pressed);
        sd.addState(new int[]{}, normal);
        return sd;
    }

    //菜单点击监听
    public void setOnMenuItemClickListener(TopRightMenu.OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    public class TopRightHolder extends RecyclerView.ViewHolder {
        private ViewGroup mContainer;
        private ImageView mIcon;
        private TextView mText;

        public TopRightHolder(View itemView) {
            super(itemView);
            mContainer = (ViewGroup) itemView;
            mIcon = (ImageView) mContainer.findViewById(R.id.trm_menu_item_icon);
            mText = (TextView) mContainer.findViewById(R.id.trm_menu_item_text);
        }
    }
}
