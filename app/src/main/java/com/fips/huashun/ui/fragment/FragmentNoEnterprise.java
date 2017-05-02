package com.fips.huashun.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.activity.EnterpriseApplyActivity;
import com.fips.huashun.ui.activity.EnterpriseVerificateActivity;
import com.fips.huashun.ui.utils.ToastUtil;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 功能：用户没有绑定企业的展示界面
 *
 * @author hulin
 */
public class FragmentNoEnterprise extends Fragment implements OnClickListener
{
    private View rootView;
    // 企业名称
    private AutoCompleteTextView nameEt;
    // 验证按钮
    private Button verificationBtn;
    // 游客登录,企业申请
    private TextView touristTv, applyTv;
    // 创建一个ArrayAdapter，封装数组
    private ArrayAdapter<String> arrayAdapter;
    //定义字符串数组，作为提示的文本
    String[] arrayString = new String[]{};
    private Gson gson;
    private ToastUtil toastUtil;
    private static String ename = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_enterprise_verification, container, false);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null)
        {
            parent.removeView(rootView);
        }
        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initActivity();
        initListener();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }
    /**
     * 功能：初始化
     */
    private void initView()
    {
        gson = new Gson();
        toastUtil = ToastUtil.getInstant();
        nameEt = (AutoCompleteTextView) rootView.findViewById(R.id.et_noenterprise_name);
        if (!TextUtils.isEmpty(ename))
        {
            nameEt.setText(ename);
            nameEt.setSelection(ename.length());
        }
        nameEt.addTextChangedListener(new EditChangedListener());
        verificationBtn = (Button) rootView.findViewById(R.id.bt_enterprise_verification);
        touristTv = (TextView) rootView.findViewById(R.id.tv_tourist);
        applyTv = (TextView) rootView.findViewById(R.id.tv_enterprise_apply);
        verificationBtn.setOnClickListener(this);
        touristTv.setOnClickListener(this);
        applyTv.setOnClickListener(this);
    }

    private void initListener()
    {
    }

    private void initActivity()
    {
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_tourist:
                // 游客体验
                Intent mIntent = new Intent("com.fipx.huashun.enterprise");
                // 0 游客体验 1 申请验证企业成功
                mIntent.putExtra("type", "0");
                //发送广播
                getActivity().sendBroadcast(mIntent);
                break;
            case R.id.bt_enterprise_verification:
                // 验证按钮
                if (null == ename || TextUtils.isEmpty(ename))
                {
                    toastUtil.show("请输入企业名称");
                } else
                {
                    enterpriseExist();
                }
                break;
            case R.id.tv_enterprise_apply:
                Intent intentApply = new Intent(getActivity(),EnterpriseApplyActivity.class);
                startActivity(intentApply);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        //nameEt.setText(ename);
        MobclickAgent.onPageStart("FragmentNoEnterprise");
        MobclickAgent.onResume(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("FragmentNoEnterprise");
        MobclickAgent.onPause(getActivity());
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
        requestParams.put("ename", ename);
        HttpUtil.post(Constants.SEARCH_ENTERPRISE_URL, requestParams, new LoadJsonHttpResponseHandler(getActivity(), new LoadDatahandler()
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
                        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, arrayString);
                        nameEt.setAdapter(arrayAdapter);
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
        requestParams.put("ename", ename);
        HttpUtil.post(Constants.VERIFICATION_ENTERPRISE_URL, requestParams, new LoadJsonHttpResponseHandler(getActivity(), new LoadDatahandler()
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
                        Log.e("333","y");
                        Intent intent = new Intent(getActivity(), EnterpriseVerificateActivity.class);
                        intent.putExtra("ename", ename);
                        intent.putExtra("enterpriseid", data.get("enterpriseid").toString());
//                        if (!TextUtils.isEmpty(data.getString("entlogo"))){
//                            intent.putExtra("entlogo", data.get("entlogo").toString());
//                        }
                        startActivity(intent);
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
                Log.e("onFailure", "onFailure");
            }
        }));
    }

}
