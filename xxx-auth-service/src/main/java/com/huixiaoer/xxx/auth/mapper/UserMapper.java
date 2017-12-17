package com.huixiaoer.xxx.auth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;
import com.huixiaoer.mybatis.base.BaseMapper;
import com.huixiaoer.xxx.auth.entity.User;


/**
 * @Mapper注解标记这个接口作为一个映射接口。真正实现映射的方法（XML文件）需要源对象作为参数，并返回目标对象。 
 * @author admin
 *
 */

@Mapper
public interface UserMapper extends BaseMapper<User>{
	/**
	 * 发现用户
	 * @param userId
	 */
	public User findByUsername(String userName);
	
	public User findByUserId(Long userId);
	
	public Page<User> findAllByPage();
	
	public List<User> findAll();

	public void insertUser(User user);

}
