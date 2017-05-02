package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fips.huashun.R;
import com.fips.huashun.common.ConstantsCode;
import com.fips.huashun.modle.bean.TopRightMenuitem;
import com.fips.huashun.modle.event.FeedTopMenuEvent;
import com.fips.huashun.modle.event.LeaveRefreshEvent;
import com.fips.huashun.modle.event.LeaveTopMenuEvent;
import com.fips.huashun.ui.adapter.InteractRoomPagerAdapter;
import com.fips.huashun.ui.fragment.FeedBackRoomFragment;
import com.fips.huashun.ui.fragment.LeaveWallFragment;
import com.fips.huashun.ui.utils.SystemBarTintManager;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.widgets.TopRightMenu;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 2017/1/16.
 * 互动空间
 */
public class InteractiveSpaceActivity extends BaseActivity {
    //pop窗体默认的高度,默认包裹内容
    private static final int DEFAULT_POP_HEIGHT = -1;
    @Bind(R.id.iv_fanhui)
    ImageView mIvFanhui;
    @Bind(R.id.tv_leave_wall)
    TextView mTvLeaveWall;
    @Bind(R.id.cursor1)
    TextView mCursor1;
    @Bind(R.id.tv_feedback_room)
    TextView mTvFeedbackRoom;
    @Bind(R.id.cursor2)
    TextView mCursor2;
    @Bind(R.id.iv_more)
    ImageView mIvMore;
    @Bind(R.id.viewpager)
    ViewPager mViewpager;
    @Bind(R.id.et_course_evaluate_dialog)
    EditText mEtCourseEvaluateDialog;
    @Bind(R.id.bt_reply)
    Button mBtReply;
    @Bind(R.id.ll_button_dialog)
    LinearLayout mLlButtonDialog;
    private ArrayList<Fragment> mArrayList;
    private TopRightMenu mTopRightMenu;
    private int mIndex = 0;//viewpager的页面位置
    private String activityid;
    //右上popwindow弹窗的宽高
    private int popwidth = ConstantsCode.TOP_RIGHT_POPWIDTH;
    private EventBus mEventBus;
    private boolean LEAVEISOPEN;//留言墙开通
    private boolean FEEDBACKOPEN;//反馈室开通
    private ToastUtil mToastUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactive_room);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.line_bg);//通知栏所需颜色
        }
        ButterKnife.bind(this);
        Intent intent = getIntent();
        activityid = intent.getStringExtra("activityid");
        LEAVEISOPEN = intent.getStringExtra("leavewall").equals("1");
        FEEDBACKOPEN = intent.getStringExtra("feedback").equals("1");
//        Log.i("test", "留言 ：" + LEAVEISOPEN + "反馈 ：" + FEEDBACKOPEN);
        mToastUtil = ToastUtil.getInstant();
        initView();
    }

    //初始化界面
    @Override
    protected void initView() {
        initViewPager();
        initNavigationView();
    }

    //初始化导航
    private void initNavigationView() {
        mTvLeaveWall.setOnClickListener(new TvOnClickListener(0));
        mTvFeedbackRoom.setOnClickListener(new TvOnClickListener(1));
    }

    @OnClick({R.id.iv_fanhui, R.id.iv_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_fanhui:
                finish();
                break;
            case R.id.iv_more:
                if (!FEEDBACKOPEN && mIndex == 1) {
                    mToastUtil.show("本活动未开放反馈室功能");
                    return;
                }
                if (!LEAVEISOPEN && mIndex == 0) {
                    mToastUtil.show("本活动未开放留言墙功能");
                    return;
                }
                showMoreMenu(mIndex);
                break;
        }
    }

    private void showMoreMenu(int i) {
        mTopRightMenu = new TopRightMenu(this);
        List<TopRightMenuitem> menuList = new ArrayList<>();
        menuList.add(new TopRightMenuitem(R.drawable.add_, "创建留言"));
        menuList.add(new TopRightMenuitem(R.drawable.eye_, "只看我的"));
        if (i == 1) {
            menuList.add(new TopRightMenuitem(R.drawable.gou_, "讲师已评分"));
            menuList.add(new TopRightMenuitem(R.drawable.cha_, "讲师未评分"));
        }
        mTopRightMenu.setAnimationStyle(R.style.TRM_ANIM_STYLE)
                .setHeight(DEFAULT_POP_HEIGHT)
                .addMenuList(menuList)
                .setOnMenuItemClickListener(mOnMenuItemClickListener)
                .showAsDropDown(mIvMore, popwidth, 20);
    }

    //当右上popwindow条目被点
    private TopRightMenu.OnMenuItemClickListener mOnMenuItemClickListener = new TopRightMenu.OnMenuItemClickListener() {
        @Override
        public void onMenuItemClick(int position) {
            mEventBus = EventBus.getDefault();
            Intent intent = null;
            switch (position) {
                case 0:
                    if (mIndex == 0) {
                        //发布留言
                        intent = new Intent(InteractiveSpaceActivity.this, PostStatusActivity.class);
                        intent.putExtra("activityid", activityid);
                        intent.putExtra("type", "0");
                        startActivityForResult(intent, ConstantsCode.LEAVE_WALL_POST);
                    } else {
                        //发布反馈
                        intent = new Intent(InteractiveSpaceActivity.this, PostStatusActivity.class);
                        intent.putExtra("activityid", activityid);
                        intent.putExtra("type", "1");
                        startActivityForResult(intent, ConstantsCode.FEED_BACK_POST);
                    }
                    break;
                case 1:
                    if (mIndex == 0) {
                        //只看我的
                        //发送EvenBus
                        mEventBus.post(new LeaveTopMenuEvent("1"));
                    } else {
                        //只看我的
                        //发送EvenBus
                        FeedTopMenuEvent event1 = new FeedTopMenuEvent();
                        event1.setType("1");
                        event1.setSeei("1");
                        mEventBus.post(event1);
                    }
                    break;
                case 2:
                    //讲师已评分
                    FeedTopMenuEvent event2 = new FeedTopMenuEvent();
                    event2.setType("2");
                    event2.setLecturer(true);
                    mEventBus.post(event2);
                    break;
                case 3:
                    //讲师未评分
                    FeedTopMenuEvent event3 = new FeedTopMenuEvent();
                    event3.setType("2");
                    event3.setLecturer(false);
                    mEventBus.post(event3);
                    break;
            }
            //隐藏popwindow
            mTopRightMenu.setPopWindowDiss();
        }
    };

    class TvOnClickListener implements View.OnClickListener {
        private int position;

        public TvOnClickListener(int i) {

            this.position = i;
        }

        @Override
        public void onClick(View v) {
            mViewpager.setCurrentItem(position);
        }
    }

    //初始化viewpager
    private void initViewPager() {
        mArrayList = new ArrayList<>();
        mArrayList.add(new LeaveWallFragment(activityid, LEAVEISOPEN));
        mArrayList.add(new FeedBackRoomFragment(activityid, FEEDBACKOPEN));
        mViewpager.setAdapter(new InteractRoomPagerAdapter(getSupportFragmentManager(), mArrayList));
        //设置当前的页面为0
        mViewpager.setCurrentItem(0);
        mViewpager.setOnPageChangeListener(mOnPageChangeListener);
    }

    //viewpager滑动监听
    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        @Override
        public void onPageSelected(int position) {
            //位置等于当前的viewpager的位置
            mIndex = position;
            if (position == 0) {
                mTvLeaveWall.setTextColor(getResources().getColor(R.color.enterprise_act__text));
                mTvFeedbackRoom.setTextColor(getResources().getColor(R.color.enterprise_act__defaulttext));
                mCursor1.setVisibility(View.VISIBLE);
                mCursor2.setVisibility(View.INVISIBLE);
            }
            if (position == 1) {
                mTvLeaveWall.setTextColor(getResources().getColor(R.color.enterprise_act__defaulttext));
                mTvFeedbackRoom.setTextColor(getResources().getColor(R.color.enterprise_act__text));
                mCursor1.setVisibility(View.INVISIBLE);
                mCursor2.setVisibility(View.VISIBLE);
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    @Override
    public boolean isSystemBarTranclucent() {
        return false;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mEventBus==null){
            return;
        }
        switch (requestCode) {
            case ConstantsCode.LEAVE_WALL_POST:
                mEventBus.post(new LeaveRefreshEvent(ConstantsCode.LEAVE_WALL_POST));
                break;
            case ConstantsCode.FEED_BACK_POST:
                mEventBus.post(new LeaveRefreshEvent(ConstantsCode.FEED_BACK_POST));
                break;
        }
    }
}
