package com.entity.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * 接收传参的实体类
 *（实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了）
 * 取自ModelAndView 的model名称
 */
public class UserModel implements Serializable {
    private static final long serialVersionUID = 1L;




    /**
     * 主键
     */
    private Integer id;


    /**
     * 账户
     */
    private String username;


    /**
     * 密码
     */
    private String password;


    /**
     * 用户姓名
     */
    private String name;


    /**
     * 用户手机号
     */
    private String phone;


    /**
     * 用户身份证号
     */
    private String idNumber;


    /**
     * 用户头像
     */
    private String photo;


    /**
     * 性别
     */
    private Integer sexTypes;


    /**
     * 电子邮箱
     */
    private String email;


    /**
     * 余额
     */
    private Double newMoney;


    /**
     * 简介
     */
    private String content;


    /**
     * 逻辑删除
     */
    private Integer deleteStatus;


    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
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