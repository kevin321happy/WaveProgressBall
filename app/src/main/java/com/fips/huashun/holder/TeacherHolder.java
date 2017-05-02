package com.fips.huashun.holder;

import android.animation.AnimatorSet;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.modle.bean.TeacherInfo;
import com.fips.huashun.common.Constants;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shuyu.common.RecyclerBaseHolder;
import com.shuyu.common.model.RecyclerBaseModel;

import butterknife.Bind;

import butterknife.ButterKnife;

/**
 * Created by kevin on 2017/1/5.
 */
public class TeacherHolder extends RecyclerBaseHolder {
    public final static int ID = R.layout.lecturer_result_item;
    @Bind(R.id.iv_lecturer_head)
    RoundedImageView mIvLecturerHead;
    @Bind(R.id.tv_lecturer_name)
    TextView mTvLecturerName;
    @Bind(R.id.tv_lecturer_riokin)
    TextView mTvLecturerRiokin;
    @Bind(R.id.tv_lecturer_field)
    TextView mTvLecturerField;
    @Bind(R.id.tv_lecturer_content)
    TextView mTvLecturerContent;
    @Bind(R.id.tv_lecturer_course)

    TextView mTvLecturerCourse;
    private ImageLoader mImageLoader=ImageLoader.getInstance();

    public TeacherHolder(Context context, View v) {
        super(context, v);
    }

    @Override
    public void createView(View v) {
        ButterKnife.bind(this, v);
        

    }

    //根据数据处理逻辑
    @Override
    public void onBind(RecyclerBaseModel model, int position) {
        TeacherInfo.TeacherListBean TeacherListBean = (TeacherInfo.TeacherListBean) model;
        mImageLoader.displayImage(Constants.IMG_URL+TeacherListBean.getTeacherPhoto(), mIvLecturerHead, ApplicationEx.home_teacher_options);
        mTvLecturerName.setText(TeacherListBean.getTeacherName());
        mTvLecturerRiokin.setText(TeacherListBean.getRiokin()+"人赞过");
        mTvLecturerField.setText("擅长领域："+TeacherListBean.getField()+"");
        mTvLecturerContent.setText("服务内容："+TeacherListBean.getContent());
        mTvLecturerCourse.setText("主讲："+TeacherListBean.getCourses()+"");
    }
    //不需要可以不写
    @Override
    public AnimatorSet getAnimator(View view) {
        //实现你的动画
        return null;
    }
}
