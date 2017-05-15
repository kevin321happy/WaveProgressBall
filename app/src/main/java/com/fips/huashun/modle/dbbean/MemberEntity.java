package com.fips.huashun.modle.dbbean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * description: 存放企业成员的表
 * autour: Kevin
 * company:锦绣氘(武汉)科技有限公司
 * date: 2017/5/9 11:55
 * update: 2017/5/9
 * version: 1.21
 * 站在峰顶 看世界
 * 落在谷底 思人生
 */
@DatabaseTable(tableName = "tb_ent_member")
public class MemberEntity {

  @DatabaseField(generatedId = true)
  private long id;//自增ID
  @DatabaseField(canBeNull = false)//非空
  private String company_id;//企业ID
  @DatabaseField(unique = true)
  private String uid;//用户ID
  @DatabaseField
  private String member_name;//用户名
  @DatabaseField
  private String name_py;//名字的拼音
  @DatabaseField
  private String head_image;//头像地址
  @DatabaseField
  private String department_id;//部门ID
  @DatabaseField
  private String department_name;//部门名称
  @DatabaseField
  private String ry_token;//融云的token
  @DatabaseField
  private String job_id;//岗位ID
  @DatabaseField
  private String job_name;//岗位名称
  @DatabaseField
  private String all_parent_id;//所有部门的ID
  @DatabaseField
  private String introduce;//介绍(预留字段)
  @DatabaseField
  private String ischeck;//是否被勾选

  public String getIscheck() {
    return ischeck;
  }

  public void setIscheck(String ischeck) {
    this.ischeck = ischeck;
  }

  public MemberEntity() {
  }

  public String getName_py() {
    return name_py;
  }

  public void setName_py(String name_py) {
    this.name_py = name_py;
  }

  @Override
  public String toString() {
    return "MemberEntity{" +
        "id=" + id +
        ", company_id='" + company_id + '\'' +
        ", uid='" + uid + '\'' +
        ", member_name='" + member_name + '\'' +
        ", head_image='" + head_image + '\'' +
        ", department_id='" + department_id + '\'' +
        ", department_name='" + department_name + '\'' +
        ", ry_token='" + ry_token + '\'' +
        ", job_id='" + job_id + '\'' +
        ", job_name='" + job_name + '\'' +
        ", all_parent_id='" + all_parent_id + '\'' +
        ", introduce='" + introduce + '\'' +
        '}';
  }

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

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getMember_name() {
    return member_name;
  }

  public void setMember_name(String member_name) {
    this.member_name = member_name;
  }

  public String getHead_image() {
    return head_image;
  }

  public void setHead_image(String head_image) {
    this.head_image = head_image;
  }

  public String getDepartment_id() {
    return department_id;
  }

  public void setDepartment_id(String department_id) {
    this.department_id = department_id;
  }

  public String getDepartment_name() {
    return department_name;
  }

  public void setDepartment_name(String department_name) {
    this.department_name = department_name;
  }

  public String getRy_token() {
    return ry_token;
  }

  public void setRy_token(String ry_token) {
    this.ry_token = ry_token;
  }

  public String getJob_id() {
    return job_id;
  }

  public void setJob_id(String job_id) {
    this.job_id = job_id;
  }

  public String getJob_name() {
    return job_name;
  }

  public void setJob_name(String job_name) {
    this.job_name = job_name;
  }

  public String getAll_parent_id() {
    return all_parent_id;
  }

  public void setAll_parent_id(String all_parent_id) {
    this.all_parent_id = all_parent_id;
  }

  public String getIntroduce() {
    return introduce;
  }

  public void setIntroduce(String introduce) {
    this.introduce = introduce;
  }
}
