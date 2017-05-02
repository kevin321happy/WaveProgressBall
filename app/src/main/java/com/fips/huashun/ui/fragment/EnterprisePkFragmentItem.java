package com.fips.huashun.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.PkInfo;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.adapter.EnterprisepkRangkAdapter;
import com.fips.huashun.ui.utils.PreferenceUtils;
import com.fips.huashun.ui.utils.ToastUtil;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/22.
 */
public class EnterprisePkFragmentItem extends Fragment
{
    private View rootView;
    private ListView mListView;
    private EnterprisepkRangkAdapter enterprisepkRangkAdapter;
    private TextView rankTv,transcendTv,nameTv,jopNameTv,countTv;
    // rankOne(1 个人 2 部门 3 企业)，
    // rankTwo（综合排名 1，积分排名 2，参与项目数 3，考试通过数 4，考试时间 5）
    public int rankOne,rankTwo;
    private Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragent_erprise_pk_viewpager, container, false);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null)
        {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        MobclickAgent.onPageStart("EnterprisePkFragmentItem");
        MobclickAgent.onResume(getActivity());
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("EnterprisePkFragmentItem");
        MobclickAgent.onPause(getActivity());
    }

    private void initView()
    {
        gson =new Gson();
        rankTv = (TextView) rootView.findViewById(R.id.tv_myrank);
        transcendTv = (TextView) rootView.findViewById(R.id.tv_transcend);
        nameTv = (TextView) rootView.findViewById(R.id.tv_show_name);
        jopNameTv = (TextView) rootView.findViewById(R.id.tv_show_jopname);
        countTv= (TextView) rootView.findViewById(R.id.tv_show_counts);
        if(rankOne == 3)
        {
            nameTv.setText("部门");
            jopNameTv.setText("人数");
            if(rankTwo == 1 || rankTwo == 3)
            {
                countTv.setText("均量");
            } else
            {
                countTv.setText("均分");
            }
        } else
        {
            nameTv.setText("姓名");
            jopNameTv.setText("岗位");
            if(rankTwo == 1)
            {
                countTv.setText("完成数");
            } else if(rankTwo == 2)
            {
                countTv.setText("均分");
            } else if(rankTwo == 3)
            {
                countTv.setText("参与数");
            } else if(rankTwo == 4)
            {
                countTv.setText("积分");
            }
        }
        mListView = (ListView) rootView.findViewById(R.id.lv_enterprise_pkpage_item);
        enterprisepkRangkAdapter = new EnterprisepkRangkAdapter(getActivity(),rankOne);
        mListView.setAdapter(enterprisepkRangkAdapter);
    }
    // 获取数据
    private void initData()
    {
        String url = getPkUrl();
        RequestParams requestParams = new RequestParams();
        requestParams.put("userid", PreferenceUtils.getUserId());
        HttpUtil.post(url, requestParams, new LoadJsonHttpResponseHandler(getActivity(), new LoadDatahandler()
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
                    if ("y".equals(suc))
                    {
                        PkInfo pkInfo = gson.fromJson(data.getString("data"),PkInfo.class);
                        if(rankOne == 3)
                        {
                            rankTv.setText("我的部门排名：第"+pkInfo.getRank()+"名");
                            transcendTv.setText("已超越"+pkInfo.getTranscend()+"个部门");
                        } else
                        {
                            rankTv.setText("我的当前排名：第"+pkInfo.getRank()+"名");
                            transcendTv.setText("已超越"+pkInfo.getTranscend()+"名同事");
                        }
                        enterprisepkRangkAdapter.setListItems(pkInfo.getPkList());
                        enterprisepkRangkAdapter.notifyDataSetChanged();
                    } else
                    {
                        ToastUtil.getInstant().show(msg);
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
            }
        }));
    }

    /**
     * 功能：获取企业PKurl
     * @return
     */
    private String getPkUrl()
    {
        String url ="";
        if(rankOne == 1)
        {// 个人
            if (rankTwo == 1)
            {// 学习排名
                url = Constants.PERSONALSTUDYKLIST_URL;
            } else  if (rankTwo == 2)
            {// 成绩排名
                url = Constants.PERSONALPASSEXAMPKLIST_URL;
            }
            else  if (rankTwo == 3)
            {// 活动排名
                url = Constants.PERSONALJOINACTSPKLIST_URL;
            }
            else  if (rankTwo == 4)
            {// 积分排名
                url = Constants.PERSONALCODEPKLIST_URL;
            }
        } else if(rankOne == 2)
        {// 部门
            if (rankTwo == 1)
            {
                url = Constants.PARTMENTSTUDYPKLIST_URL;
            } else  if (rankTwo == 2)
            {
                url = Constants.PARTMENTPASSEXAMPKLIST_URL;
            }
            else  if (rankTwo == 3)
            {
                url = Constants.PARTMENTJOINACTSPKLIST_URL;
            }
            else  if (rankTwo == 4)
            {
                url = Constants.PARTMENTCODEPKLIST_URL;
            }
        } else if(rankOne == 3)
        {// 企业
            if (rankTwo == 1)
            {
                url = Constants.ENSTUDYPKLIST_URL;
            } else  if (rankTwo == 2)
            {
                url = Constants.ENPASSEXAMPKLIST_URL;
            }
            else  if (rankTwo == 3)
            {
                url = Constants.ENJOINACTSPKLIST_URL;
            }
            else  if (rankTwo == 4)
            {
                url = Constants.ENTERPRICECODELIST_URL;
            }
        }
        return url;
    }


}
