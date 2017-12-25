package com.nian.xxx.auth.service;

import com.github.pagehelper.Page;
import com.nian.xxx.auth.entity.User;

public interface UserService {
	/**
	 * 发现用户
	 * @param userId
	 */
	public User findByLoginName(String loginName);
	 
	public User findByUserId(Long currentUserId);
	

	public Page<User> findAllByPage(int pageNo, int pageSize);
	
	
	 public void insertUser(User u) throws Exception ;
}
