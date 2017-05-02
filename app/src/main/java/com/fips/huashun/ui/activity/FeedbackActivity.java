package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.adapter.RecyclerViewAdapter;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * 功能：意见反馈
 * Created by Administrator on 2016/8/26.
 *
 * @author 张柳 时间：2016年8月26日15:23:01
 */
public class FeedbackActivity extends BaseActivity implements View.OnClickListener ,RecyclerViewAdapter.Callback
{
    private NavigationBar navigationBar;
    private Button bt_image;
    private ArrayList<String> mSelectPath;
    private static final int REQUEST_IMAGE = 2;
    private static final int REQUEST_TAILOR = 200;
    private RecyclerView rc_view;
    private Button bt_add,bt_submit;
    private RecyclerViewAdapter viewAdapter;
    private EditText et_feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initView();
    }
    @Override
    protected void initView()
    {
        super.initView();
        navigationBar = (NavigationBar) findViewById(R.id.nb_feedback);
        navigationBar.setTitle("意见反馈");
        navigationBar.setLeftImage(R.drawable.fanhui);
        navigationBar.setListener(new NavigationBar.NavigationListener()
        {
            @Override
            public void onButtonClick(int button)
            {
                if (button == NavigationBar.LEFT_VIEW)
                {
                    finish();
                }
            }
        });
        et_feedback = (EditText) findViewById(R.id.et_feedback);
        bt_add = (Button) findViewById(R.id.bt_add);
        bt_add.setOnClickListener(this);
        bt_submit = (Button) findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(this);
        rc_view = (RecyclerView) findViewById(R.id.rc_view);
        rc_view.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        viewAdapter = new RecyclerViewAdapter(this);
        rc_view.setAdapter(viewAdapter);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.bt_add:
                getMultiImageSelector(true);
                break;
            case R.id.bt_submit:
                submitFeedbackImg();
                break;
        }

    }

    @Override
    public boolean isSystemBarTranclucent()
    {
        return false;
    }

    private void getMultiImageSelector(boolean isShowCamera) {
    Intent intent = new Intent(this, MultiImageSelectorActivity.class);
    // 是否显示拍摄图片
    intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, isShowCamera);
    // 最大可选择图片数量
    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 3);
    // 选择模式
    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
    // 默认选择
    if (mSelectPath != null && mSelectPath.size() > 0) {
        intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
    }
    startActivityForResult(intent, REQUEST_IMAGE);
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                StringBuilder sb = new StringBuilder();

                for (String p : mSelectPath) {
//                    Intent intent = new Intent();
//                    intent.setAction("com.android.camera.action.CROP");
//                    intent.setDataAndType(Uri.fromFile(new File(p)), "image/*");// mUri是已经选择的图片Uri
//                    intent.putExtra("crop", "true");
//                    intent.putExtra("aspectX", 1);// 裁剪框比例
//                    intent.putExtra("aspectY", 1);
//                    intent.putExtra("outputX", 150);// 输出图片大小
//                    intent.putExtra("outputY", 150);
//                    intent.putExtra("return-data", true);
//                    this.startActivityForResult(intent, REQUEST_TAILOR);
//                    Bitmap getimage = UploadImageUtil.getimage(p, 150f, 150f);


                }
                if (mSelectPath.size()==3){
                    bt_add.setVisibility(View.GONE);
                }else {
                    bt_add.setVisibility(View.VISIBLE);
                }
                viewAdapter.setListItems(mSelectPath);
                rc_view.setVisibility(View.VISIBLE);
                rc_view.setHasFixedSize(false);
            }
        }
    }


    @Override
    public void click(View v, int position) {
        if (mSelectPath!=null&&mSelectPath.size()>0){
            mSelectPath.remove(position);
            viewAdapter.notifyItemRemoved(position);
            bt_add.setVisibility(View.VISIBLE);

        }
    }

    /**
     * 功能：图片上传
     */
    private void submitFeedbackImg()
    {
        final String content = et_feedback.getText().toString().trim();
        if(TextUtils.isEmpty(content))
        {
            ToastUtil.getInstant().show("请输入反馈内容");
            return;
        }
        RequestParams requestParams = new RequestParams();
        if(null != mSelectPath && mSelectPath.size()>0)
        { // 这是有图片。先进行图片上传
            for (int i = 0;i<mSelectPath.size();i++)
            {
                try
                {
                    File file = new File(mSelectPath.get(i));
                    requestParams.put("file_"+i, file,"application/octet-stream");
                } catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
            HttpUtil.post(Constants.FEEDBACK_IMG_URL, requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
            {
                @Override
                public void onStart()
                {
                    super.onStart();
                    showLoadingDialog();
                }

                @Override
                public void onSuccess(JSONObject data)
                {
                    super.onSuccess(data);
                    dimissLoadingDialog();
                    try
                    {
                        String suc = data.get("suc").toString();
                        String msg = data.get("msg").toString();
                        if ("y".equals(suc))
                        {
                            submitFeedbackContent(content);
                        }
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String error, String message)
                {
                    super.onFailure(error, message);
                    dimissLoadingDialog();
                    ToastUtil.getInstant().show("意见反馈失败，请重试！");
                }
            }));
        }
        else
        {
            showLoadingDialog();
            // 这是没有图片。直接上传反馈内容
            submitFeedbackContent(content);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("FeedbackActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("FeedbackActivity");
    }
    /**
     * 功能：反馈内容
     */
    private void submitFeedbackContent(String content)
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("userid", PreferenceUtils.getUserId());
        requestParams.put("content", content);
        HttpUtil.post(Constants.FEEDBACK_CONTENT_URL, requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
        {
            @Override
            public void onStart()
            {
                super.onStart();
            }

            @Override
            public void onSuccess(JSONObject data)
            {
                super.onSuccess(data);
                dimissLoadingDialog();
                try
                {
                    String suc = data.get("suc").toString();
                    String msg = data.get("msg").toString();
                    if ("y".equals(suc))
                    {
                        ToastUtil.getInstant().show("意见反馈成功");
                        finish();
                    } else
                    {
                        ToastUtil.getInstant().show("意见反馈失败，请重试！");
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(String error, String message)
            {
                super.onFailure(error, message);
                dimissLoadingDialog();
                ToastUtil.getInstant().show("意见反馈失败，请重试！");
            }
        }));

    }
}
