package com.entity.vo;

import com.entity.UserEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * 手机端接口返回实体辅助类
 * （主要作用去除一些不必要的字段）
 */
@TableName("user")
public class UserVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableField(value = "id")
    private Integer id;

    /**
     * 账户
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 用户姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 用户手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 用户身份证号
     */
    @TableField(value = "id_number")
    private String idNumber;

    /**
     * 用户头像
     */
    @TableField(value = "photo")
    private String photo;

    /**
     * 性别
     */
    @TableField(value = "sex_types")
    private Integer sexTypes;

    /**
     * 电子邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 余额
     */
    @TableField(value = "new_money")
    private Double newMoney;

    /**
     * 简介
     */
    @TableField(value = "content")
    private String content;

    /**
     * 逻辑删除
     */
    @TableField(value = "delete_status")
    private Integer deleteStatus;

    /**
     * 创建时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 获取：主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置：主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取：账户
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置：账户
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取：密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置：密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取：用户姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：用户姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：用户手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置：用户手机号
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取：用户身份证号
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * 设置：用户身份证号
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    /**
     * 获取：用户头像
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * 设置：用户头像
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * 获取：性别
     */
    public Integer getSexTypes() {
        return sexTypes;
    }

    /**
     * 设置：性别
     */
    public void setSexTypes(Integer sexTypes) {
        this.sexTypes = sexTypes;
    }

    /**
     * 获取：电子邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置：电子邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取：余额
     */
    public Double getNewMoney() {
        return newMoney;
    }

    /**
     * 设置：余额
     */
    public void setNewMoney(Double newMoney) {
        this.newMoney = newMoney;
    }

    /**
     * 获取：简介
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置：简介
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取：逻辑删除
     */
    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * 设置：逻辑删除
     */
    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    /**
     * 获取：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置：创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}