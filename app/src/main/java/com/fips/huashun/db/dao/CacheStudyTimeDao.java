package com.fips.huashun.db.dao;

import android.content.Context;
import com.fips.huashun.db.DataDownloadHelp;
import com.fips.huashun.modle.dbbean.CacheStudyTimeEntity;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.util.List;

/**
 * description: 缓存学习时间
 * autour: Kevin
 * company:锦绣氘(武汉)科技有限公司
 * date: 2017/4/28 11:59
 * update: 2017/4/28
 * version: 1.21
 * 站在峰顶 看世界
 * 落在谷底 思人生
 */
public class CacheStudyTimeDao {

  private Context mContext;
  private DataDownloadHelp helper;
  private Dao<CacheStudyTimeEntity, Integer> mCacheStudyTimeDao;

  public CacheStudyTimeDao(Context context) {
    mContext = context;
    try {
      helper = DataDownloadHelp.getHelper(context);
      mCacheStudyTimeDao = helper.getDao(CacheStudyTimeEntity.class);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  //增加一条数据
  public void addRedord(CacheStudyTimeEntity cachestudyentity) {
    try {
      mCacheStudyTimeDao.create(cachestudyentity);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  //更新一条数据
  public void updataRecord(CacheStudyTimeEntity cachestudyentity) {
    try {
      mCacheStudyTimeDao.update(cachestudyentity);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  //查询所有数据
  public List<CacheStudyTimeEntity> queryAllRecord() {
    List<CacheStudyTimeEntity> list = null;
    try {
      list = mCacheStudyTimeDao.queryForAll();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  //删除数据
  public void deleteRecord(CacheStudyTimeEntity cachestudyentity) {
    try {
      mCacheStudyTimeDao.delete(cachestudyentity);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
