package com.fips.huashun.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.fips.huashun.modle.dbbean.CacheStudyTimeEntity;
import com.fips.huashun.modle.dbbean.CourseEntity;
import com.fips.huashun.modle.dbbean.CourseSectionEntity;
import com.fips.huashun.modle.dbbean.DataDownloadInfo;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kevin on 2017/2/23. 文件下载数据库
 */

public class DataDownloadHelp extends OrmLiteSqliteOpenHelper {

  private static final String DB_NAMW = "datadowload.db";
  private static final int DB_VERSION = 3;
  private static DataDownloadHelp instance;
  private Map<String, Dao> daos = new HashMap<String, Dao>();

  public DataDownloadHelp(Context context) {
    super(context, DB_NAMW, null, DB_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
    //创建表
    try {
      TableUtils.createTable(connectionSource, DataDownloadInfo.class);
      TableUtils.createTable(connectionSource, CourseEntity.class);
      TableUtils.createTable(connectionSource, CourseSectionEntity.class);
      TableUtils.createTable(connectionSource, CacheStudyTimeEntity.class);
    } catch (SQLException e) {
    }
  }

  @Override
  public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion,
      int newVersion) {
//    onUpgrade
    //更新表
    try {
      TableUtils.dropTable(connectionSource, DataDownloadInfo.class, true);
      TableUtils.dropTable(connectionSource, CourseEntity.class, true);
      TableUtils.dropTable(connectionSource, CourseSectionEntity.class, true);
      TableUtils.dropTable(connectionSource, CacheStudyTimeEntity.class, true);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  //单例获取该实例
  public static synchronized DataDownloadHelp getHelper(Context context) {
    context = context.getApplicationContext();
    if (instance == null) {
      synchronized (DataDownloadHelp.class) {
        if (instance == null) {
          instance = new DataDownloadHelp(context);
        }
      }
    }
    return instance;
  }

  public synchronized Dao getDao(Class clazz) throws SQLException {
    Dao dao = null;
    String className = clazz.getSimpleName();
    if (daos.containsKey(className)) {
      dao = daos.get(className);
    }
    if (dao == null) {
      dao = super.getDao(clazz);
      daos.put(className, dao);
    }
    return dao;
  }

  /**
   * 释放资源
   */
  @Override
  public void close() {
    super.close();
    for (String key : daos.keySet()) {
      Dao dao = daos.get(key);
      dao = null;
    }
  }
}
