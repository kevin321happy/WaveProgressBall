package com.fips.huashun.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.fips.huashun.R;
import com.fips.huashun.widgets.CircleMenuLayout;
import com.fips.huashun.widgets.CircleMenuLayout.OnMenuItemClickListener;
import com.fips.huashun.widgets.ListView3d;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * 测试企业书房界面
 */
public class EntTesstActivity extends AppCompatActivity {
  private LinearLayout mLeft;
  private CircleMenuLayout mCircleMenuLayout;
  private String[] mItemTexts = new String[]{"企业文化 ", "组织架构", "企业部门", "PK榜",
      "我的课程", "企业活动", "学习地图", "任务", "能力模型"};
  private int[] mItemImgs = new int[]{R.drawable.coggage_one,
      R.drawable.coggage_two,
      R.drawable.coggage_three,
      R.drawable.coggage_four,
      R.drawable.coggage_five,
      R.drawable.coggage_six,
      R.drawable.coggage_eight,
      R.drawable.coggage_nine};
  private CircleMenuLayout mCircleMenuLayout1;
  private int mHeightPixels;
  private int height;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ent_tesst);
    SlidingMenu slidingMenu = (SlidingMenu) findViewById(R.id.SlidingMenu);
    DisplayMetrics metric = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(metric);
    // 屏幕宽度（像素）
    mHeightPixels = metric.heightPixels;
    mCircleMenuLayout1 = (CircleMenuLayout) slidingMenu.findViewById(R.id.main_menu);
    mCircleMenuLayout1.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
    slidingMenu.setMode(SlidingMenu.RIGHT);
    slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
    slidingMenu.setTouchModeBehind(SlidingMenu.SLIDING_WINDOW);
    //slidingMenu.setShadowWidthRes(10);
    slidingMenu.setBehindWidth(300);
    slidingMenu.setAboveOffset(20);
    slidingMenu.setFadeDegree(0.35f);
    ListView3d mlist = (ListView3d) findViewById(R.id.list3d);
    String[] elements = new String[40];
    for (int i = 0; i < 40; i++) {
      elements[i] = String.valueOf(i);
    }
    MyAdapter adapter = new MyAdapter(this, elements);
    mlist.setDivider(null);
    mlist.setAdapter(adapter);

    mCircleMenuLayout1.setOnMenuItemClickListener(new OnMenuItemClickListener() {
      @Override
      public void itemClick(View view, int pos) {
        Toast.makeText(EntTesstActivity.this, mItemTexts[pos], Toast.LENGTH_SHORT);
      }

      @Override
      public void itemCenterClick(View view) {

      }
    });
  }

  public class MyAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;
    private final String[] mItems;

    public MyAdapter(Activity c, String[] objects) {
      mInflater = c.getLayoutInflater();
      mItems = objects;
    }

    public int getCount() {
      return mItems.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      if (convertView == null) {
        convertView = mInflater.inflate(R.layout.listitem, null);
      }
      //((TextView)convertView).setText(mItems[position]);
      return convertView;
    }

    @Override
    public Object getItem(int position) {
      return mItems[position];
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

  }
}

