package com.fips.huashun.ui.activity;

import android.os.Bundle;
import com.fips.huashun.R;

/**
 * description: 企业文化界面
 * autour: Kevin
 * company:锦绣氘(武汉)科技有限公司
 * date: 2017/4/20 12:09 
 * update: 2017/4/20
 * version: 1.21
 * 站在峰顶 看世界
 * 落在谷底 思人生
*/

public class EnterpriseCulture extends BaseActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_enterprise_culture);



  }


  @Override
  public boolean isSystemBarTranclucent() {
    return false;
  }
}
