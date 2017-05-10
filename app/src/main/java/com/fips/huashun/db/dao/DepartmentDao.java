package com.fips.huashun.db.dao;


import android.content.Context;
import com.fips.huashun.db.EnterpriseIMChatHelp;
import com.fips.huashun.modle.dbbean.DepartmentEntity;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.util.List;

public class DepartmentDao {

  private Context mContext;
  private EnterpriseIMChatHelp helper;
  private Dao<DepartmentEntity, Integer> mDepartmentDao;

  public DepartmentDao(Context context) {
    mContext = context;
    try {
      helper = EnterpriseIMChatHelp.getHelper(context);
      mDepartmentDao = helper.getDao(DepartmentEntity.class);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  //增加一条数据
  public void addDepartmentEntity(DepartmentEntity departmentEntity) {
    try {
      mDepartmentDao.create(departmentEntity);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  //查询所有的数据
  public List<DepartmentEntity> queryAllDepartments() {
    List<DepartmentEntity> departmentEntityList = null;
    try {
      departmentEntityList = mDepartmentDao.queryForAll();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return departmentEntityList;
  }
  //根据父部门的ID来查询出所有的子部门
  public List<DepartmentEntity> queryDepsByPid(String pid) {
    List<DepartmentEntity> departmentEntities=null;
    try {
      departmentEntities = mDepartmentDao.queryBuilder().where().eq("pid", pid).query();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return departmentEntities;


  }

}
