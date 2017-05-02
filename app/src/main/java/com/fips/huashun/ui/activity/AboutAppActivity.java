package com.fips.huashun.ui.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.ui.utils.NavigationBar;
import com.umeng.analytics.MobclickAgent;


/**
 * 功能：关于我们
 * Created by Administrator on 2016/8/26.
 * @author 张柳 时间：2016年8月26日11:38:48
 */
public class AboutAppActivity extends BaseActivity implements View.OnClickListener
{
    private NavigationBar navigationBar;
    private TextView  agreementTv;
    private WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutapp);
        initView();
    }

    @Override
    protected void initView()
    {
        super.initView();
        navigationBar = (NavigationBar) findViewById(R.id.nb_aboutapp);
        navigationBar.setTitle("关于我们");
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
//        agreementTv = (TextView) findViewById(R.id.tv_agreement);
//        agreementTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG ); //下划线
//        agreementTv.getPaint().setAntiAlias(true);//抗锯齿
        webview = (WebView) findViewById(R.id.webview);

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setSupportZoom(true); //
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webview.loadUrl("javascript:App.resize(document.body.getBoundingClientRect().height)");
                super.onPageFinished(view, url);
                navigationBar.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });

        webview.loadUrl(Constants.H5_URL+"aboutUs.html");
    }

    @Override
    public void onClick(View v)
    {
    }
    @Override
    public Resources getResources() {
        return super.getResources();
    }
    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("AboutAppActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("AboutAppActivity");
    }


    @Override
    public boolean isSystemBarTranclucent()
    {
        return false;
    }
}
