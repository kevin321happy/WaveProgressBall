package com.fips.huashun.db.dao;


import android.content.Context;
import android.util.Log;
import com.fips.huashun.db.DataDownloadHelp;
import com.fips.huashun.modle.dbbean.CourseSectionEntity;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.util.List;

public class SectionDownloadDao {

  private Context mContext;
  private DataDownloadHelp helper;
  private Dao<CourseSectionEntity, Integer> mCourseDownloadDao;

  public SectionDownloadDao(Context context) {
    mContext = context;
    try {
      helper = DataDownloadHelp.getHelper(mContext);
      mCourseDownloadDao = helper.getDao(CourseSectionEntity.class);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * 增加一条数据
   */
  public void add(CourseSectionEntity info) {
//    mCourseDownloadDao.cre
    try {
      mCourseDownloadDao.create(info);
      Log.i("test", "添加成功了呀");
    } catch (SQLException e) {
      e.printStackTrace();
      Log.i("test", "添加失败,错误信息：" + e.toString());
    }
  }

  /**
   * 查询所有的数据
   */
  public List<CourseSectionEntity> queryAll() {
    List<CourseSectionEntity> courseSectionEntities = null;
    try {
      courseSectionEntities = mCourseDownloadDao.queryForAll();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return courseSectionEntities;
  }


  /**
   * 根据课程章节的ID来查询该章节的下载的状态
   */
  public int queryStateBysectionId(String Id) {
    int state = -1;
    try {
      CourseSectionEntity CourseSectionEntity = mCourseDownloadDao.queryBuilder().where()
          .eq("sectionId", Id)
          .queryForFirst();
      if (CourseSectionEntity == null) {
        return -1;
      }
      state = CourseSectionEntity.getState();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return state;
  }

  /**
   * 更新表里面的下载信息
   */
  public void upDataInfo(CourseSectionEntity courseSectionEntity) {
    try {
      mCourseDownloadDao.update(courseSectionEntity);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  //查询正在下载中的课程
  public List<CourseSectionEntity> querySectionOnDownload() {
    List<CourseSectionEntity> mlist = null;
    //查询所有的下载中的课程
    try {
      mlist = mCourseDownloadDao.queryBuilder().where().eq("state", 1).or().eq("state", 3).or()
          .eq("state", -1).or().eq("state", -2).query();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return mlist;
  }

  //根据章节Id来查询章节内容
  public CourseSectionEntity querySectionBySectionId(String id) {
    CourseSectionEntity courseSectionEntity = null;
    try {
      courseSectionEntity = mCourseDownloadDao.queryBuilder().where().eq("sectionId", id)
          .queryForFirst();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return courseSectionEntity;
  }

  //根据课程ID来查询课程
  public List<CourseSectionEntity> querySectionByCourseId(String courseid) {
    List<CourseSectionEntity> sectionEntities = null;
    try {
      sectionEntities = mCourseDownloadDao.queryBuilder().where().eq("courseid", courseid)
          .and().eq("state", 2 + "")
          .query();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return sectionEntities;

  }

  //查询所有已下载的课程
  public List<CourseSectionEntity> queryAllHaveDownload() {
    List<CourseSectionEntity> entityList = null;
    try {
      entityList = mCourseDownloadDao.queryBuilder().where().eq("state", 2)
          .query();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return entityList;
  }

  //根据章节的Id删除章节
  public void deleteSectionById(String sectionId) {
    try {
      CourseSectionEntity courseSectionEntity = querySectionBySectionId(sectionId);
      if (courseSectionEntity != null) {
        mCourseDownloadDao.delete(courseSectionEntity);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
