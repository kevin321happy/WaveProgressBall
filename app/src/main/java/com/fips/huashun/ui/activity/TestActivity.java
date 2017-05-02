package com.fips.huashun.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.fips.huashun.R;
import com.fips.huashun.widgets.HeadTagIamge;

public class TestActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test);
    HeadTagIamge headtag = (HeadTagIamge) findViewById(R.id.headTag);
    headtag.setLableSize(50);
   headtag.setBorderColor(R.color.title_red);
   headtag.setBorderWidth(10);
    headtag.setLableText("傻逼啊");
    headtag.setLableVisible(false);
    headtag.loadHeadIamge(this, "http://v1.52qmct.com/pic/upload/head/20_20161226035250.jpg");
  }
}
