package com.fips.huashun.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.fips.huashun.modle.dbbean.DepartmentEntity;
import com.fips.huashun.modle.dbbean.MemberEntity;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * description:企业及时通讯的的数据库
 * autour: Kevin
 * company:锦绣氘(武汉)科技有限公司
 * date: 2017/5/9 13:32
 * update: 2017/5/9
 * version: 1.21
 * 站在峰顶 看世界
 * 落在谷底 思人生
*/
public class EnterpriseIMChatHelp extends OrmLiteSqliteOpenHelper {
  private static final String DB_NAMW = "enterprise_imchat";
  private static final int DB_VERSION = 1;
  private static EnterpriseIMChatHelp instance;
  private Map<String, Dao> daos = new HashMap<String, Dao>();

  public EnterpriseIMChatHelp(Context context) {
    super(context, DB_NAMW, null, DB_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
    //创建表
    try {
     TableUtils.createTable(connectionSource, DepartmentEntity.class);
      TableUtils.createTable(connectionSource, MemberEntity.class);
    } catch (SQLException e) {
    }
  }

  @Override
  public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion,
      int newVersion) {
    //更新表
    try {
      TableUtils.createTable(connectionSource, DepartmentEntity.class);
      TableUtils.createTable(connectionSource, MemberEntity.class);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  //单例获取该实例
  public static synchronized EnterpriseIMChatHelp getHelper(Context context) {
    context = context.getApplicationContext();
    if (instance == null) {
      synchronized (EnterpriseIMChatHelp.class) {
        if (instance == null) {
          instance = new EnterpriseIMChatHelp(context);
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
