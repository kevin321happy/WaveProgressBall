package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.common.ACache;
import com.fips.huashun.common.CacheConstans;
import com.fips.huashun.common.Constants;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.adapter.HotSearchAdapter;
import com.fips.huashun.ui.adapter.SearchHistoryAdapter;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;

public class SearchActivity extends BaseActivity implements TextView.OnEditorActionListener, View.OnClickListener {

    private TextView tv_cancle;
    private EditText et_search;
    private ImageView iv_cancle;
    private PopupWindow pop;
    private TextView tv_text;
    int tag = 0;
    private String text;
    private SearchHistoryAdapter madapter;
    private TextView tv_one;
    private TextView tv_two;
    private TextView tv_three;
    private HotSearchAdapter hotSearchAdapter;
    private HotSearchAdapter hotSearchAdapter2;
    private TagGroup mGv_view2;
    private TagGroup mGv_view3;
    private TagGroup mGv_view1;
    private ACache mACache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //初始化缓存对象
        mACache = ACache.get(this);
        initView();
        try {
            JSONObject searcherdata = mACache.getAsJSONObject(CacheConstans.SEARCHER_INFO_JSON);
            if (searcherdata != null) {
                showSearcherInfo(searcherdata);
                Log.i("test", "我使用了缓存");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        getHistory();
    }

    @Override
    public boolean isSystemBarTranclucent() {
        return false;
    }

    @Override
    protected void initView() {
        super.initView();
//        el_listview = (ExpandableListView) findViewById(R.id.el_listview);
        tv_cancle = (TextView) findViewById(R.id.tv_cancle);
        tv_text = (TextView) findViewById(R.id.tv_text);
        text = tv_text.getText().toString();
        tv_text.setOnClickListener(this);
        et_search = (EditText) findViewById(R.id.et_search);
        iv_cancle = (ImageView) findViewById(R.id.iv_cancle);
        tv_one = (TextView) findViewById(R.id.tv_one);
        tv_two = (TextView) findViewById(R.id.tv_two);
        tv_three = (TextView) findViewById(R.id.tv_three);
        iv_cancle.setOnClickListener(this);
        tv_cancle.setOnClickListener(this);
        et_search.setOnEditorActionListener(this);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    iv_cancle.setVisibility(View.VISIBLE);
                } else {
                    iv_cancle.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //TODO
        // GridView gv_view1 = (GridView) findViewById(R.id.gv_view1);
        // GridView gv_view2 = (GridView) findViewById(R.id.gv_view2);
        mGv_view1 = (TagGroup) findViewById(R.id.gv_view1);
        mGv_view2 = (TagGroup) findViewById(R.id.gv_view2);
        mGv_view3 = (TagGroup) findViewById(R.id.gv_view3);

        mGv_view1.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                Intent intent = new Intent(SearchActivity.this, CourseResultActivity.class);
                intent.putExtra("condition", tag);
                startActivity(intent);
            }
        });
        mGv_view2.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                Intent intent = new Intent(SearchActivity.this, CourseResultActivity.class);
                intent.putExtra("condition", tag);
                startActivity(intent);
            }
        });
        mGv_view3.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                Intent intent = new Intent(SearchActivity.this, LecturerResultActivity.class);
                intent.putExtra("condition", tag);
                startActivity(intent);
            }
        });
    }

    //  GridView gv_view3 = (GridView) findViewById(R.id.gv_view3);
//        madapter = new SearchHistoryAdapter(this);
//
//        hotSearchAdapter = new HotSearchAdapter(this);
//        hotSearchAdapter2 = new HotSearchAdapter(this);
    //TODO
      /* gv_view1.setAdapter(madapter);
         gv_view2.setAdapter(hotSearchAdapter);
        gv_view3.setAdapter(hotSearchAdapter2);
        gv_view1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = madapter.getItem(position);
                et_search.setText(name);
            }
        });
         gv_view2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = hotSearchAdapter.getItem(position);
                Intent intent = new Intent(SearchActivity.this, CourseResultActivity.class);
                intent.putExtra("condition", item);
                startActivity(intent);
            }
        });
        gv_view3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = hotSearchAdapter2.getItem(position);
                Intent intent = new Intent(SearchActivity.this, LecturerResultActivity.class);
                intent.putExtra("condition", item);
                startActivity(intent);
            }
        });
        */


    private void getHistory() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("userid", PreferenceUtils.getUserId());
        HttpUtil.post(Constants.HISTORY_URL, requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler() {
            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onSuccess(JSONObject data) {
                super.onSuccess(data);
                dimissLoadingDialog();
                //缓存json数据
                mACache.put(CacheConstans.SEARCHER_INFO_JSON, data);
                showSearcherInfo(data);
                Log.i("test", "保存了搜索界面的信息");
            }

            @Override
            public void onFailure(String error, String message) {
                super.onFailure(error, message);
                dimissLoadingDialog();
            }
        }));
    }

    /**
     * 显示搜索界面信息
     * @param data
     */
    private void showSearcherInfo(JSONObject data) {
        try {
           // showSearcherInfo(data);
            String dataString = data.getString("data");
            JSONObject jsonObject = new JSONObject(dataString);
            JSONArray hotCourse = jsonObject.getJSONArray("hotCourse");
            JSONArray historyList = jsonObject.getJSONArray("historyList");
            Log.i("test", historyList + "搜索记录");
            JSONArray hotTeacher = jsonObject.getJSONArray("hotTeacher");
//                  SearchInfo searchInfo = gson.fromJson(data.getString("data").toString(), SearchInfo.class);
            if (historyList.length() > 0) {
                List<String> hotCourseList = gson.fromJson(historyList.toString(), new TypeToken<List<String>>() {
                }.getType());
                //  Log.i("test","搜索历史"+hotCourseList.toString());
                mGv_view1.setTags(hotCourseList);
                tv_one.setVisibility(View.VISIBLE);
//                        madapter.setListItems(hotCourseList);
//                        madapter.notifyDataSetChanged();
            }
            if (hotCourse.length() > 0) {
                List<String> hotCourseList = gson.fromJson(hotCourse.toString(), new TypeToken<List<String>>() {
                }.getType());
                mGv_view2.setTags(hotCourseList);
                tv_two.setVisibility(View.VISIBLE);
                //TODO
//                        hotSearchAdapter.setListItems(hotCourseList);
//                        hotSearchAdapter.notifyDataSetChanged();
            }
            if (hotTeacher.length() > 0) {
                List<String> hotTeacherList = gson.fromJson(hotTeacher.toString(), new TypeToken<List<String>>() {
                }.getType());
                mGv_view3.setTags(hotTeacherList);
                tv_three.setVisibility(View.VISIBLE);
//                        hotSearchAdapter2.setListItems(hotTeacherList);
//                        hotSearchAdapter2.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            dimissLoadingDialog();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH && !et_search.getText().toString().equals("")) {
// 先隐藏键盘
            ((InputMethodManager) et_search.getContext().getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(this
                                    .getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
//跳转activity
            if (tv_text.getText().toString().equals("课程")) {
                Intent intent = new Intent(this, CourseResultActivity.class);
                intent.putExtra("condition", et_search.getText().toString());
                startActivity(intent);
            } else if (tv_text.getText().toString().equals("讲师")) {
                Intent intent = new Intent(this, LecturerResultActivity.class);
                intent.putExtra("condition", et_search.getText().toString());
                startActivity(intent);
            }

            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_cancle:
                et_search.setText("");
                break;
            case R.id.tv_cancle:
                finish();
                break;
            case R.id.tv_text:
                InitPopwindow();
                break;
        }
    }

    private void InitPopwindow() {
        pop = new PopupWindow(this);
        View view = getLayoutInflater().inflate(
                R.layout.layout_course_pop, null);
        pop.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pop.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);

        pop.showAsDropDown(tv_text);
        final TextView tv_text1 = (TextView) view.findViewById(R.id.tv_text1);
        if (tag == 0) {
            tv_text1.setText("讲师");
        } else {
            tv_text1.setText("课程");
        }
        tv_text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag == 0) {
                    tag = 1;
                } else {
                    tag = 0;
                }
                tv_text.setText(tv_text1.getText().toString());
                pop.dismiss();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("SearchActivity");
        getHistory();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("SearchActivity");
    }
}

