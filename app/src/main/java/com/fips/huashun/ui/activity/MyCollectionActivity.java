package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.CourseInfo;
import com.fips.huashun.modle.bean.TeacherCourse;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.adapter.AllCourseAdapter;
import com.fips.huashun.ui.adapter.LecturerResultAdapter;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：我的收藏（课程和讲师）
 * Created by Administrator on 2016/8/25.
 * @author 张柳 时间：2016年8月25日09:35:44
 */
public class MyCollectionActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener
{
    private NavigationBar navigationBar;
    private SwipeMenuListView mListView;
    private RadioButton courseRb,lecturerRb;
    private RadioGroup rg;
    private ArrayList<RadioButton> rb;
    private AllCourseAdapter allCourseAdapter;
    private LecturerResultAdapter lecturerResultAdapter;
    int tag =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycollection);
        initView();
        getCourser();
    }


    @Override
    protected void initView() {
        super.initView();
        mListView = (SwipeMenuListView) findViewById(R.id.lv_mycollection);
        mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
//                SwipeMenuItem openItem = new SwipeMenuItem(
//                        getApplicationContext());
//                // set item background
//                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
//                        0xCE)));
//                // set item width
//                openItem.setWidth(dp2px(90));
//                // set item title
//                openItem.setTitle("Open");
//                // set item title fontsize
//                openItem.setTitleSize(18);
//                // set item title font color
//                openItem.setTitleColor(Color.WHITE);
//                // add to menu
//                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
//                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
//                        0x3F, 0x25)));
                deleteItem.setBackground(getResources().getDrawable(R.color.white));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.delete);

                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        mListView.setMenuCreator(creator);
        mListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });
        // step 2. listener item click event
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                switch (index) {
                    case 0:
                        if (tag==1){
                            String courseId = allCourseAdapter.getItem(position).getCourseId();
                            deleteData(courseId,position);
                        }else if (tag==2){
                            int courseId = lecturerResultAdapter.getItem(position).getTeacherId();
                            deleteData(courseId+"",position);
                        }
                    break;
                }
                return false;
            }
        });
        mListView.setOnItemClickListener(this);
        courseRb = (RadioButton) findViewById(R.id.rb_left_collectcourse);
        lecturerRb = (RadioButton) findViewById(R.id.rb_right_collectlecturer);
        navigationBar = (NavigationBar) findViewById(R.id.na_bar);
        navigationBar.setTitle("我的收藏");
        navigationBar.setLeftImage(R.drawable.fanhui);
        navigationBar.setListener(new NavigationBar.NavigationListener() {
            @Override
            public void onButtonClick(int button) {
                if (button==NavigationBar.LEFT_VIEW){
                    finish();
                }
            }
        });
        courseRb.setOnClickListener(this);
        lecturerRb.setOnClickListener(this);
        rg = (RadioGroup) findViewById(R.id.rb);
        rb = new ArrayList<RadioButton>();
        rb.add(courseRb);
        rb.add(lecturerRb);
        rb.get(0).setChecked(true);//设置进入页面第一次显
        allCourseAdapter =new AllCourseAdapter(this);
        lecturerResultAdapter = new LecturerResultAdapter(this);
        mListView.setAdapter(allCourseAdapter);
    }
    @Override
    public boolean isSystemBarTranclucent()
    {
        return false;
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() ==R.id.rb_left_collectcourse)
        {
            tag=1;
            mListView.setAdapter(allCourseAdapter);
           getCourser();
        } else if(v.getId() ==R.id.rb_right_collectlecturer)
        {
            tag=2;
            mListView.setAdapter(lecturerResultAdapter);
           getTeacher();
}
}
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
    private void getCourser() {
        RequestParams requestParams =new RequestParams();
        requestParams.put("userid", PreferenceUtils.getUserId());
        HttpUtil.post(Constants.URL+"/selfCourseCollectList",requestParams,new LoadJsonHttpResponseHandler(this,new LoadDatahandler(){
            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onSuccess(JSONObject data) {
                super.onSuccess(data);
                dimissLoadingDialog();
                try {
                    JSONArray jsonArray =data.getJSONArray("data");
                    if (jsonArray.length()>0){
                        List<CourseInfo> courseInfos = gson.fromJson(jsonArray.toString(), new TypeToken<List<CourseInfo>>() {
                        }.getType());
                        allCourseAdapter.setListItems(courseInfos);
                        allCourseAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error, String message) {
                super.onFailure(error, message);
                dimissLoadingDialog();
            }
        }));
    }

    private void getTeacher() {
        RequestParams requestParams =new RequestParams();
        requestParams.put("userid",PreferenceUtils.getUserId());
        HttpUtil.post(Constants.URL+"/selfTeachCollectList",requestParams,new LoadJsonHttpResponseHandler(this,new LoadDatahandler(){
            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onSuccess(JSONObject data) {
                super.onSuccess(data);
                dimissLoadingDialog();
                try {
                    JSONArray jsonArray =data.getJSONArray("data");
                    if (jsonArray.length()>0){
                        List<TeacherCourse> teacherCourses = gson.fromJson(jsonArray.toString(), new TypeToken<List<TeacherCourse>>() {
                        }.getType());

                        lecturerResultAdapter.setListItems(teacherCourses);
                        lecturerResultAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error, String message) {
                super.onFailure(error, message);
                dimissLoadingDialog();

            }
        }));
    }

    private void deleteData(String collectid, final int a){
        RequestParams requestParams =new RequestParams();
        requestParams.put("type",tag+"");
        requestParams.put("userid", PreferenceUtils.getUserId());
        requestParams.put("collectid",collectid);
        HttpUtil.post(Constants.URL+"/deleteSelfCollectInfo",requestParams,new LoadJsonHttpResponseHandler(this,new LoadDatahandler(){
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(JSONObject data) {
                super.onSuccess(data);
                try {
                    String suc = data.getString("suc");
                    if (suc.equals("y")){
                        ToastUtil.getInstant().show("删除成功");
                        if (tag==1){
                            List<CourseInfo> allListDate = allCourseAdapter.getAllListDate();

                            allListDate.remove(a);
                            allCourseAdapter.notifyDataSetChanged();
                        }else if (tag==2){
                            List<TeacherCourse> teacherCourses = lecturerResultAdapter.getAllListDate();
                            teacherCourses.remove(a);
                            lecturerResultAdapter.notifyDataSetChanged();
                        }
                    }else {
                        ToastUtil.getInstant().show("删除失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error, String message) {
                super.onFailure(error, message);

            }
        }));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (tag==1){
            CourseInfo item = allCourseAdapter.getItem(position);
            Intent intent =new Intent(MyCollectionActivity.this,CourseDetailActivity.class);
            intent.putExtra("courseId",item.getCourseId());
            startActivity(intent);
        }
        if (tag==2){
            TeacherCourse item = lecturerResultAdapter.getItem(position);
            Intent intent =new Intent(MyCollectionActivity.this,LecturerDetailActivity.class);
            intent.putExtra("teacherId",String.valueOf(item.getTeacherId()));
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MyCollectionActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MyCollectionActivity");
    }
}
