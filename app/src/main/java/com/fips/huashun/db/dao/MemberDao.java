package com.fips.huashun.db.dao;


import android.content.Context;
import com.fips.huashun.db.EnterpriseIMChatHelp;
import com.fips.huashun.modle.dbbean.MemberEntity;
import com.fips.huashun.ui.utils.ToastUtil;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
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

  //获取选中的人员的个数
  public long getChoosedCounts(String state) {
    long counts = 0;
    try {
      QueryBuilder<MemberEntity, Integer> queryBuilder = mMemberDao.queryBuilder();
      queryBuilder.setCountOf(true);
      queryBuilder.setWhere(queryBuilder.where().eq("ischeck", state));
      counts = mMemberDao.countOf(queryBuilder.prepare());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return counts;
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
  //根据pid来查询该部门下面的所有的子部门的人数的总和
  public List<MemberEntity> queryAllMemebersByPid(String pid){
    List<MemberEntity> memberEntityList = null;
    try {
      memberEntityList = mMemberDao.queryBuilder().where()
          .like("all_parent_id", "%," + pid + ",%").query();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return memberEntityList;
  }
  //更加部P门的Id更新部门下面的所有的数据
  public void upDataDepartmentsStatesByPid(String pid, boolean add) {
    try {
      UpdateBuilder<MemberEntity, Integer> updateBuilder = mMemberDao
          .updateBuilder();
      if (add) {
        updateBuilder.updateColumnValue("ischeck", "1")
            .where().like("all_parent_id", "%," + pid + ",%");
        updateBuilder.update();
      } else {
        updateBuilder.updateColumnValue("ischeck", 0).where()
            .like("all_parent_id", "%," + pid + ",%");
        updateBuilder.update();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  //更新所有的人员信息为未勾选
  public void setAllMemberUnChoosed(String company_id) {
    try {
      UpdateBuilder<MemberEntity, Integer> memberUpdataBuild = mMemberDao
          .updateBuilder();
      memberUpdataBuild.updateColumnValue("ischeck", 0).where().eq("company_id", company_id);
      memberUpdataBuild.update();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  //更新成员信息
  public void upDataMember(MemberEntity memberEntity) {
    try {
      mMemberDao.update(memberEntity);
    } catch (SQLException e) {
      e.printStackTrace();
    }
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

  //根据部门的ID来查询部门下已经勾选的人数
  public int queryChooseCounts(String dep_id) {
    int count = 0;
    try {
      List<MemberEntity> entityList = mMemberDao.queryBuilder().where().eq("department_id", dep_id)
          .and()
          .eq("ischeck", "1").query();
      count = entityList.size();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return count;
  }

  public List<MemberEntity> queryAllChooseMembers() {
    List<MemberEntity> list = null;
    try {
      list = mMemberDao.queryBuilder().where().eq("ischeck", "1").query();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  //查询所有的已勾选的人数
  public int queryAllChooseCount() {
    int counts = 0;
    try {
      List<MemberEntity> memberEntities = mMemberDao.queryBuilder().where().eq("ischeck", "1")
          .query();
      if (memberEntities != null) {
        counts = memberEntities.size();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return counts;
  }

  //当点击全选时设置当前的选中人数
  public void setCourrentChoosed(String pid, boolean checkAll) {
    setAllMemberUnChoosed("8");
    try {
      if (checkAll){
        UpdateBuilder<MemberEntity, Integer> updateBuilder = mMemberDao
            .updateBuilder();
        updateBuilder.updateColumnValue("ischeck", 1).where()
            .like("all_parent_id", "%," + pid + ",%");
        updateBuilder.update();
      }else {
        UpdateBuilder<MemberEntity, Integer> updateBuilder = mMemberDao
            .updateBuilder();
        updateBuilder.updateColumnValue("ischeck", 0).where()
            .like("all_parent_id", "%," + pid + ",%");
        updateBuilder.update();
      }
      ToastUtil.getInstant().show("当前选中人数 ："+getChoosedCounts("1"));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


}
