package com.dao;

import com.entity.UserEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import com.entity.view.UserView;

/**
 * 统一用户 Dao 接口
 *
 * @author
 */
public interface UserDao extends BaseMapper<UserEntity> {

	/**
	 * 分页查询统一用户视图列表
	 * @param page 分页对象
	 * @param params 查询参数
	 * @return 分页后的用户视图列表
	 */
	List<UserView> selectListView(Pagination page, @Param("params") Map<String, Object> params);

	/**
	 * 查询统一用户视图列表（不分页）
	 * @param params 查询参数
	 * @return 用户视图列表
	 */
	List<UserView> selectListView(@Param("params") Map<String, Object> params);
}