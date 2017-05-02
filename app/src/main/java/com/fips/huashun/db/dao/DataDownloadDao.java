package com.fips.huashun.db.dao;

import android.content.Context;
import com.fips.huashun.db.DataDownloadHelp;
import com.fips.huashun.modle.dbbean.DataDownloadInfo;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by kevin on 2017/2/23. 文件下载的Dao
 */
public class DataDownloadDao {

  private Context context;
  private Dao<DataDownloadInfo, Integer> mDataDownloadDao;
  private DataDownloadHelp helper;

  public DataDownloadDao(Context context) {
    this.context = context;
    try {
      helper = DataDownloadHelp.getHelper(context);
      mDataDownloadDao = helper.getDao(DataDownloadInfo.class);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * 增加一条数据
   */
  public void add(DataDownloadInfo info) {
    try {
      mDataDownloadDao.create(info);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * 根据活动id查取活动下面的课程资料
   */
  public List<DataDownloadInfo> queryDataByactId(String activityid) {
    List<DataDownloadInfo> dataDownloadInfos = null;
    try {
      dataDownloadInfos = mDataDownloadDao.queryBuilder().where()
          .eq("activityid", activityid).query();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return dataDownloadInfos;
  }

  /**
   * 根据资料的pid查取单个资料
   */
  public DataDownloadInfo queryDataByPid(String pid) {
    DataDownloadInfo dataDownloadInfo = null;
    try {
      dataDownloadInfo = mDataDownloadDao.queryBuilder().where().eq("pid", pid)
          .queryForFirst();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return dataDownloadInfo;
  }

  //根据pid来查询状态
  public int queryStateByPid(String pid) {
    DataDownloadInfo dataDownloadInfo = null;
    try {
      dataDownloadInfo = mDataDownloadDao.queryBuilder().where().eq("pid", pid)
          .queryForFirst();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return dataDownloadInfo.getState();
  }
  //如果文件在本地不存在,根据pid删除数据的数据
  public void deleteDataByPid(String pid) {
    try {
      DataDownloadInfo dataDownloadInfo = mDataDownloadDao.queryBuilder().where().eq("pid", pid)
          .queryForFirst();
      if (dataDownloadInfo != null) {
        mDataDownloadDao.delete(dataDownloadInfo);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  /**
   * 清除所有数据
   */
  public void deleteAll() {
    try {
      List<DataDownloadInfo> downloadInfos = mDataDownloadDao.queryForAll();
      for (DataDownloadInfo downloadInfo : downloadInfos) {
        mDataDownloadDao.delete(downloadInfo);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * 更新数据
   */
  public void upDataInfo(DataDownloadInfo dataDownloadInfo) {
    try {
      mDataDownloadDao.update(dataDownloadInfo);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  //根据pid来修改数据状态
  public void upDataInfoByPid(String pid, int state) {
    try {
      DataDownloadInfo downloadInfo = mDataDownloadDao.queryBuilder().where().eq("pid", pid)
          .queryForFirst();
      downloadInfo.setState(state);
      mDataDownloadDao.update(downloadInfo);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
