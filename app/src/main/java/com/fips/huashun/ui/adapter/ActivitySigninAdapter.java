package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.modle.bean.SignInfo;
import com.fips.huashun.modle.bean.SignInfoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 2017/1/12.
 * 活动签到的adapter
 */
public class ActivitySigninAdapter extends BaseAdapter {
    private static final int TYPE_LAST = 0x0002;
    private Context mContext;
    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_NORMAL = 0x0001;
    private List<SignInfoModel.SignInfo> mData = new ArrayList<>();
    private OnSignInClickListener mOnSignInClickListener;
    private List<SignInfo> signList = new ArrayList<>();//用于保存签到信息的集合
    private SignInfo mInfo;

    public void setOnSignInClickListener(OnSignInClickListener onSignInClickListener) {
        mOnSignInClickListener = onSignInClickListener;
    }

    public ActivitySigninAdapter(Context context, List<SignInfoModel.SignInfo> singInfoList) {
        this.mData = singInfoList;
        this.mContext = context;
    }

    public ActivitySigninAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return signList == null ? 0 : signList.size();
    }

    @Override
    public Object getItem(int position) {
        return signList == null ? null : signList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int id) {
        if (id == 0) {
            return TYPE_TOP;
        }
        if (id == signList.size() - 1) {
            return TYPE_LAST;
        }
        return TYPE_NORMAL;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SignInfo signinfo = signList.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.enterprise_act_signin_item, null);
            holder = new ViewHolder();
            holder.tv_singup = (TextView) convertView.findViewById(R.id.iv_singup);
            holder.tvTopLine = (TextView) convertView.findViewById(R.id.tvTopLine);
            holder.tvBelowLine = (TextView) convertView.findViewById(R.id.tv_Below_Line);
            holder.sign_title = (TextView) convertView.findViewById(R.id.tv_signup_title);
            holder.tv_singup_startime = (TextView) convertView.findViewById(R.id.tv_singup_startime);
            holder.tv_singup_endtime = (TextView) convertView.findViewById(R.id.tv_singup_endtime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (getItemViewType(position) == TYPE_TOP) {
            //第一行头的竖线不显示
            holder.tvTopLine.setVisibility(View.INVISIBLE);
            holder.tvBelowLine.setVisibility(View.VISIBLE);
        } else if (getItemViewType(position) == TYPE_LAST) {
            holder.tvBelowLine.setVisibility(View.INVISIBLE);
            holder.tvTopLine.setVisibility(View.VISIBLE);
        } else if (getItemViewType(position) == TYPE_NORMAL) {
            holder.tvTopLine.setVisibility(View.VISIBLE);
            holder.tvBelowLine.setVisibility(View.VISIBLE);
        }
        //绑定数据
        holder.tv_singup_startime.setText("" + signinfo.getSigntime());
        //签到状态 0 sstatus  1签到成功 2迟到 3早退 4偏差
        //签退状态  estatus 0签到失败 1签到成功  3早退 4偏差
        String estatus = signinfo.getEstatus();//签退状态
        String sstatus = signinfo.getSstatus();//签到状态
        //要求签到、签退时间
        final String requiredTime;

        final int signType = signinfo.getType();//1签到0签退
        final String pid = signinfo.getPid();
        //签到
        if (signType == 1) {
            requiredTime = signinfo.getSigntime();//要求签到时间
            holder.tv_singup_startime.setText(signinfo.getSigntime() + "前完成签到哦！");
            if (sstatus == null) {
                holder.tv_singup_endtime.setText("亲,请记得准时签到哦~");
                holder.tv_singup.setEnabled(true);
                holder.tv_singup.setText("签到");
                holder.tv_singup.setBackgroundResource(R.drawable.qiandao);
            }
            if (sstatus != null && sstatus.equals("0")) {//未签到
                holder.tv_singup.setText("签到");
                holder.tv_singup.setEnabled(true);
                holder.tv_singup_endtime.setText("亲,请记得准时签到哦~");
                holder.tv_singup.setBackgroundResource(R.drawable.qiandao);
            }
            if (sstatus != null && sstatus.equals("1")) {
                holder.tv_singup.setText("准时");
                holder.tv_singup.setEnabled(true);
                holder.tv_singup_endtime.setText("完成签到时间：" + signinfo.getFactsigntime());
                holder.tv_singup.setBackgroundResource(R.drawable.chidao);
            }
            if (sstatus != null && sstatus.equals("2")) {
                holder.tv_singup.setText("迟到");
                holder.tv_singup.setEnabled(false);
                holder.tv_singup_endtime.setText("完成签到时间：" + signinfo.getFactsigntime());
                holder.tv_singup.setBackgroundResource(R.drawable.chidao);
            }
            if (sstatus != null && sstatus.equals("4")) {
                holder.tv_singup.setText("偏差");
                holder.tv_singup_endtime.setText("完成签到时间：" + signinfo.getFactsigntime());
                holder.tv_singup.setEnabled(true);
                holder.tv_singup.setBackgroundResource(R.drawable.chidao);
            }
        } else {//签退
            requiredTime = signinfo.getSignendtime();//要求签退时间
            holder.tv_singup_startime.setText(signinfo.getSignendtime() + "后完成签退哦！");
            if (estatus == null) {
                holder.tv_singup_endtime.setText("亲,请记得准时签退哦~");
                holder.tv_singup.setEnabled(true);
                holder.tv_singup.setText("签退");
                holder.tv_singup.setBackgroundResource(R.drawable.qiandao);
            }
            if (estatus != null && estatus.equals("0")) {//未签到
                holder.tv_singup_endtime.setText("亲,请记得签退哦~");
                holder.tv_singup.setText("签退");
                holder.tv_singup.setEnabled(true);
                holder.tv_singup.setBackgroundResource(R.drawable.qiandao);
            }
            if (estatus != null && estatus.equals("1")) {
                holder.tv_singup.setText("准时");
                holder.tv_singup_endtime.setText("完成签退时间：" + signinfo.getFactsignendtime());
                holder.tv_singup.setEnabled(true);
                holder.tv_singup.setBackgroundResource(R.drawable.chidao);
            }
            if (estatus != null && estatus.equals("3")) {
                holder.tv_singup.setText("早退");
                holder.tv_singup.setEnabled(false);
                holder.tv_singup_endtime.setText("完成签退时间：" + signinfo.getFactsignendtime());
                holder.tv_singup.setBackgroundResource(R.drawable.chidao);
            }
            if (estatus != null && estatus.equals("4")) {
                holder.tv_singup.setText("偏差");
                holder.tv_singup.setEnabled(true);
                holder.tv_singup_endtime.setText("完成签退时间：" + signinfo.getFactsignendtime());
                holder.tv_singup.setBackgroundResource(R.drawable.chidao);
            }
        }
        holder.sign_title.setText("第" + signinfo.getPosition() + "签");
        holder.tv_singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSignInClickListener != null) {
                    mOnSignInClickListener.onSignInClick(signType, pid,requiredTime);
                }
            }
        });
        return convertView;
    }

    //设置数据
    public void setData(List<SignInfoModel.SignInfo> data) {
        int currentposition = 1;
        signList.clear();
        for (int i = 0; i < data.size(); i++) {
            currentposition = i + 1;
            SignInfoModel.SignInfo signInfo = data.get(i);
            if (signInfo.getSigntime() != null) {
                signList.add(getSigninstance(signInfo, 1, currentposition));
            }
            if (signInfo.getSignendtime() != null) {
                signList.add(getSigninstance(signInfo, 2, currentposition));
            }
        }
    }

    //获取签到信息实例
    private SignInfo getSigninstance(SignInfoModel.SignInfo signInfo, int type, int currentposition) {
        SignInfo info = new SignInfo();
        info.setSigntime(signInfo.getSigntime());
        info.setSignendtime(signInfo.getSignendtime());
        info.setActivityid(signInfo.getActivityid());
        info.setEstatus(signInfo.getEstatus());
        info.setSstatus(signInfo.getSstatus());
        info.setFactsignendtime(signInfo.getFactsignendtime());
        info.setFactsigntime(signInfo.getFactsigntime());
        info.setIsfixed(signInfo.getIsfixed());
        info.setPid(signInfo.getPid());

        info.setType(type);
        info.setPosition(currentposition);
        return info;
    }

    private class ViewHolder {
        TextView sign_title, tv_singup_startime, tv_singup_endtime, tvTopLine, tvBelowLine;
        TextView tv_singup;
    }

    public interface OnSignInClickListener {
        void onSignInClick(int position, String pid, String requiredtime);
    }
}
