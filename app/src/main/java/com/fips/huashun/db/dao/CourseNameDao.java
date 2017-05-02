package com.fips.huashun.db.dao;


import android.content.Context;
import android.util.Log;
import com.fips.huashun.db.DataDownloadHelp;
import com.fips.huashun.modle.dbbean.CourseEntity;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.util.List;

/**
 * description: 操作存储课程名的表
 * autour: Kevin
 * company:锦绣氘(武汉)科技有限公司
 * date: 2017/4/26 13:51
 * update: 2017/4/26
 * version: 1.21
 * 站在峰顶 看世界
 * 落在谷底 思人生
 */
public class CourseNameDao {

  private Context mContext;
  private DataDownloadHelp helper;
  private Dao<CourseEntity, Integer> mCourseNameDao;

  public CourseNameDao(Context context) {
    mContext = context;
    try {
       helper = DataDownloadHelp.getHelper(context);
       mCourseNameDao = helper.getDao(CourseEntity.class);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  //新增一条数据
  public void addCourse(CourseEntity CourseEntity) {
    try {
      mCourseNameDao.create(CourseEntity);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  //删除一条数据
  public void deletedCourse(CourseEntity CourseEntity) {
    try {
      mCourseNameDao.delete(CourseEntity);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  //根据课程Id来查询一条数据
  public CourseEntity queryById(String courseId) {
    CourseEntity entity = null;
    try {
      entity = mCourseNameDao.queryBuilder().where().eq("courseid", courseId).queryForFirst();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return entity;
  }
  //查询所有的课程的数据
  public List<CourseEntity> queryAll(){
    List<CourseEntity> entityList=null;
    try {
      entityList= mCourseNameDao.queryForAll();
    } catch (SQLException e) {
      e.printStackTrace();
      Log.i("test","查询失败了");
    }
    return entityList;
  }
}
