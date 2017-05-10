package com.fips.huashun.db.dao;


import android.content.Context;
import com.fips.huashun.db.EnterpriseIMChatHelp;
import com.fips.huashun.modle.dbbean.MemberEntity;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.util.List;

public class MemberDao {

  private Context mContext;
  private EnterpriseIMChatHelp helper;
  private Dao<MemberEntity, Integer> mMemberDao;

  //获得dao对象
  public MemberDao(Context context) {
    mContext = context;
    try {
      helper = EnterpriseIMChatHelp.getHelper(context);
      mMemberDao = helper.getDao(MemberEntity.class);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  //添加一条数据
  public void addMemberEntity(MemberEntity memberEntity) {
    try {
      mMemberDao.create(memberEntity);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  //查询所有的数据
  public List<MemberEntity> queryAllMembers() {
    List<MemberEntity> memberEntityList = null;
    try {
      memberEntityList = mMemberDao.queryForAll();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return memberEntityList;
  }

  //根据上级部门的id,来查询改部门下面挂的所有的成员
  public List<MemberEntity> queryMembersByPid(String pid) {
    List<MemberEntity> memberEntityList = null;
    try {
      memberEntityList = mMemberDao.queryBuilder().where().eq("department_id", pid).query();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return memberEntityList;
  }

  //根据用户ID来查询该成员
  public MemberEntity querMemmberByUid(String s) {
    MemberEntity memberEntity = null;
    try {
      memberEntity = mMemberDao.queryBuilder().where().eq("uid", s).queryForFirst();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return memberEntity;
  }
}
