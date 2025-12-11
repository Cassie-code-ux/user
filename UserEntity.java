package com.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import com.baomidou.mybatisplus.enums.FieldFill;


/**
 * 用户表
 * 后端实体类
 * @author
 * @email
 */
@TableName("user") // 对应新创建的user表名
public class UserEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键：用户ID
     */
    @TableId(type = IdType.AUTO)
    @TableField(value = "id")

    private Integer id;


    /**
     * 登录账号
     */
    @TableField(value = "username")

    private String username;

    /**
     * 登录密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 用户名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 联系方式
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 身份证号
     */
    @TableField(value = "id_number")
    private String idNumber;

    /**
     * 头像
     */
    @TableField(value = "photo")
    private String photo;

    /**
     * 性别类型
     */
    @TableField(value = "sex_types")
    private Integer sexTypes;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 账户余额
     */
    @TableField(value = "new_money")
    private Double newMoney;

    /**
     * 商家简介/个人备注
     */
    @TableField(value = "content")
    private String content;

    /**
     * 逻辑删除（1=正常/2=删除）
     */
    @TableField(value = "delete_status")
    private Integer deleteStatus;

    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 设置：统一用户ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取：统一用户ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置：登录账号
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取：登录账号
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置：登录密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取：登录密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置：用户/商家名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：用户/商家名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：联系方式
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取：联系方式
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置：身份证号
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    /**
     * 获取：身份证号
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * 设置：头像
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * 获取：头像
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * 设置：性别类型
     */
    public void setSexTypes(Integer sexTypes) {
        this.sexTypes = sexTypes;
    }

    /**
     * 获取：性别类型
     */
    public Integer getSexTypes() {
        return sexTypes;
    }

    /**
     * 设置：邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取：邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置：账户余额
     */
    public void setNewMoney(Double newMoney) {
        this.newMoney = newMoney;
    }

    /**
     * 获取：账户余额
     */
    public Double getNewMoney() {
        return newMoney;
    }

    /**
     * 设置：商家简介/个人备注
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取：商家简介/个人备注
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置：逻辑删除（1=正常/2=删除）
     */
    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    /**
     * 获取：逻辑删除（1=正常/2=删除）
     */
    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * 设置：创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", photo='" + photo + '\'' +
                ", sexTypes=" + sexTypes +
                ", email='" + email + '\'' +
                ", newMoney=" + newMoney +
                ", content='" + content + '\'' +
                ", deleteStatus=" + deleteStatus +
                ", createTime=" + createTime +
                '}';
    }
}