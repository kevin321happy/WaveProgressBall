package com.fips.huashun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.UserInfo;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.widgets.CircleImageView;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 功能：企业职工申请验证
 * Created by Administrator on 2016/9/2.
 */
public class EnterpriseVerificateActivity extends BaseActivity implements View.OnClickListener
{
    // 企业LOGO
    private CircleImageView logoIv;
    // 企业工号，企业部门
    private EditText iobNumberEt, departmentEt;
    // 企业搜索框
    private AutoCompleteTextView enterpriseNameTv;
    // 申请按钮
    private Button applyBtn;
    // 取消
    private TextView cancelTv;
    private Intent intent;
    private String ename,enterpriseid,entlogo;
    // 创建一个ArrayAdapter，封装数组
    private ArrayAdapter<String> arrayAdapter;
    //定义字符串数组，作为提示的文本
    String[] arrayString = new String[]{};
    private Gson gson;
    private ToastUtil toastUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprise_verification_one);
        gson = new Gson();
        toastUtil =ToastUtil.getInstant();
        initView();
    }

    @Override
    protected void initView()
    {
        super.initView();
        intent = getIntent();
        ename = intent.getStringExtra("ename");
        enterpriseid = intent.getStringExtra("enterpriseid");
        entlogo = intent.getStringExtra("entlogo");
        enterpriseNameTv = (AutoCompleteTextView) findViewById(R.id.tv_enterprise_name);
        enterpriseNameTv.setText(ename);
        enterpriseNameTv.setSelection(ename.length());
        enterpriseNameTv.addTextChangedListener(new EditChangedListener());
        logoIv = (CircleImageView) findViewById(R.id.iv_enterprise_head);
        iobNumberEt = (EditText) findViewById(R.id.et_iob_number);
        departmentEt = (EditText) findViewById(R.id.et_department);
        cancelTv = (TextView) findViewById(R.id.tv_cancel);
        applyBtn = (Button) findViewById(R.id.bt_employee_apply);
        applyBtn.setOnClickListener(this);
        cancelTv.setOnClickListener(this);
        enterpriseNameTv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                enterpriseExist();
            }
        });
        if (!TextUtils.isEmpty(entlogo)){
            ImageLoader.getInstance().displayImage(Constants.IMG_URL+entlogo,logoIv);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bt_employee_apply:
                // 申请
                String worknum = iobNumberEt.getText().toString().trim();
                String deptname = departmentEt.getText().toString().trim();
                if (null == worknum || TextUtils.isEmpty(worknum))
                {
                    toastUtil.show("请输入工号");
                    return;
                }
                if(null == deptname || TextUtils.isEmpty(deptname))
                {
                    toastUtil.show("请输入部门");
                    return;
                }
                enterpriseVerification(worknum,deptname);
                break;
            case R.id.tv_cancel:
                // 取消
                finish();
                break;
        }
    }

    /**
     * 功能：文本输入框变化监听事件
     */
    class EditChangedListener implements TextWatcher
    {
        String beforeString;
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
            beforeString = s.toString();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if (!s.toString().equals(beforeString))
            {
                Log.e("onTextChanged", "文本输入框变化");
                ename = s.toString();
                enterpriseList();
            }
        }

        @Override
        public void afterTextChanged(Editable s)
        {

        }
    }
    /**
     * 功能：根据企业名称模糊搜索企业
     */
    private void enterpriseList()
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("ename",ename);
        HttpUtil.post(Constants.SEARCH_ENTERPRISE_URL, requestParams, new LoadJsonHttpResponseHandler(EnterpriseVerificateActivity.this, new LoadDatahandler()
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
                try
                {
                    String suc = data.get("suc").toString();
                    String msg = data.get("msg").toString();
                    Log.e("data", "data=" + data.toString());
                    if ("y".equals(suc))
                    {
                        arrayString = gson.fromJson(data.getString("entname"), String[].class);
                        arrayAdapter = new ArrayAdapter<String>(EnterpriseVerificateActivity.this, android.R.layout.simple_dropdown_item_1line, arrayString);
                        enterpriseNameTv.setAdapter(arrayAdapter);
                        arrayAdapter.notifyDataSetChanged();
                    } else
                    {
                        //ToastUtil.getInstant().show(msg);
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    Log.e("JSONException", "JSONException");
                }
            }

            @Override
            public void onFailure(String error, String message)
            {
                super.onFailure(error, message);
                dimissLoadingDialog();
                Log.e("onFailure", "onFailure");
            }
        }));
    }
    /**
     * 功能：根据企业名称验证企业是否存在
     */
    private void enterpriseExist()
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("ename",ename);
        HttpUtil.post(Constants.VERIFICATION_ENTERPRISE_URL, requestParams, new LoadJsonHttpResponseHandler(EnterpriseVerificateActivity.this, new LoadDatahandler()
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
                try
                {
                    String suc = data.get("suc").toString();
                    String msg = data.get("msg").toString();
                    Log.e("data", "data=" + data.toString());
                    if ("y".equals(suc))
                    {
                        enterpriseid = data.get("enterpriseid").toString();
                        entlogo = data.get("entlogo").toString();
                    } else
                    {
                        //ToastUtil.getInstant().show(msg);
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    Log.e("JSONException", "JSONException");
                }
            }

            @Override
            public void onFailure(String error, String message)
            {
                super.onFailure(error, message);
                dimissLoadingDialog();
                Log.e("onFailure", "onFailure");
            }
        }));
    }
    /**
     * 功能：企业职工验证
     */
    private void enterpriseVerification(String worknum,String deptname)
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("userid", PreferenceUtils.getUserId());
        requestParams.put("enterpriseid",enterpriseid);
        requestParams.put("worknum",worknum);
        requestParams.put("deptname",deptname);
        HttpUtil.post(Constants.EMPLOYEE_ENTERPRISE_ONE_URL, requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
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
                    Log.e("data", "data=" + data.toString());
                    UserInfo userinfo = ApplicationEx.getInstance().getUserInfo();
                    userinfo.setStatus("1");
                    ApplicationEx.getInstance().setUserInfo(userinfo );
                    toastUtil.show(msg);
                    if ("y".equals(suc))
                    {
                        Intent mIntent = new Intent("com.fipx.huashun.enterprise");
                        // 0 游客体验 1 申请验证企业成功
                        mIntent.putExtra("type", "1");
                        //发送广播
                        sendBroadcast(mIntent);
                        finish();
                    } else
                    {
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    Log.e("JSONException", "JSONException");
                }
            }

            @Override
            public void onFailure(String error, String message)
            {
                super.onFailure(error, message);
                dimissLoadingDialog();
                Log.e("onFailure", "onFailure");
            }
        }));
    }
    @Override
    public boolean isSystemBarTranclucent()
    {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("EnterpriseVerificateActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("EnterpriseVerificateActivity");
    }
}
