package com.fips.huashun.db;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import com.fips.huashun.R;
import com.fips.huashun.db.dao.MemberDao;
import com.fips.huashun.modle.dbbean.MemberEntity;
import com.fips.huashun.ui.adapter.DepMemberAdapter;
import com.fips.huashun.ui.utils.ToastUtil;
import java.util.List;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TestViewActivity extends AppCompatActivity {

  private ListView mLv;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test_view);
    mLv = (ListView) findViewById(R.id.lv);
    initData();
  }

  private void initData() {
    final MemberDao memberDao = new MemberDao(this);
    //异步查询
    Observable.create(new OnSubscribe<List<MemberEntity>>() {
      @Override
      public void call(Subscriber<? super List<MemberEntity>> subscriber) {
        List<MemberEntity> memberEntityList = memberDao.queryAllMembers();
        subscriber.onNext(memberEntityList);
      }
    }).subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<List<MemberEntity>>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {

          }
          @Override
          public void onNext(List<MemberEntity> memberEntities) {
            Log.i("test00","数据查询出来了 ："+memberEntities.size());
            ToastUtil.getInstant().show("成员的个数为 ：" + memberEntities.size());

            DepMemberAdapter depMemberAdapter = new DepMemberAdapter(TestViewActivity.this);
            depMemberAdapter.setData(memberEntities);
            mLv.setAdapter(depMemberAdapter);
//              ToastUtil.getInstant().show("我设置了为什么不出来？ ：" + memberEntities.size());
//              mMemberAdapter.notifyDataSetChanged();
            }
        });


  }
}
