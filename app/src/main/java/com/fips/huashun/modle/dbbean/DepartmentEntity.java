package com.fips.huashun.modle.dbbean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * description: 存放企业部门信息的表
 * autour: Kevin
 * company:锦绣氘(武汉)科技有限公司
 * date: 2017/5/9 11:53
 * update: 2017/5/9
 * version: 1.21
 * 站在峰顶 看世界
 * 落在谷底 思人生
 */
@DatabaseTable(tableName = "tb_ent_department")
public class DepartmentEntity {

  @DatabaseField(generatedId = true)
  private long id;
  @DatabaseField(canBeNull = false)
  private String company_id;
  @DatabaseField(unique = true)//部门Id唯一字段
  private String dep_id;
  @DatabaseField
  private String dep_name;//部门名称
  @DatabaseField
  private String pid;//上级部门的id
  @DatabaseField
  private String num;//当前部门的人数
  @DatabaseField
  private String all_parent_id;//所有的上级部门的id
  @DatabaseField
  private String people_num;//下级部门的总人数
  @DatabaseField
  private String introduce;//介绍(预留)

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getCompany_id() {
    return company_id;
  }

  public void setCompany_id(String company_id) {
    this.company_id = company_id;
  }

  public String getDep_id() {
    return dep_id;
  }

  public void setDep_id(String dep_id) {
    this.dep_id = dep_id;
  }

  public String getDep_name() {
    return dep_name;
  }

  public void setDep_name(String dep_name) {
    this.dep_name = dep_name;
  }

  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }

  public String getNum() {
    return num;
  }

  public void setNum(String num) {
    this.num = num;
  }

  public String getAll_parent_id() {
    return all_parent_id;
  }

  public void setAll_parent_id(String all_parent_id) {
    this.all_parent_id = all_parent_id;
  }

  public String getPeople_num() {
    return people_num;
  }

  public void setPeople_num(String people_num) {
    this.people_num = people_num;
  }

  public String getIntroduce() {
    return introduce;
  }

  public void setIntroduce(String introduce) {
    this.introduce = introduce;
  }

  public DepartmentEntity() {
  }

  @Override
  public String toString() {
    return "DepartmentEntity{" +
        "id=" + id +
        ", company_id='" + company_id + '\'' +
        ", dep_id='" + dep_id + '\'' +
        ", dep_name='" + dep_name + '\'' +
        ", pid='" + pid + '\'' +
        ", num='" + num + '\'' +
        ", all_parent_id='" + all_parent_id + '\'' +
        ", people_num='" + people_num + '\'' +
        ", introduce='" + introduce + '\'' +
        '}';
  }
}
