package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.entity.UserEntity;
import com.utils.PageUtils;
import java.util.Map;

/**
 * 统一用户服务接口
 * 提供统一用户表的基础业务操作
 */
public interface UserService extends IService<UserEntity> {

    /**
     * 分页查询用户数据
     * @param params 包含分页参数和查询条件的Map
     * @return 封装有分页信息和用户数据的PageUtils对象
     */
    PageUtils queryPage(Map<String, Object> params);
}