package com.huixiaoer.xxx.auth.service;

import com.huixiaoer.api.ResultVo;
import com.huixiaoer.auth.UserMode;
import com.huixiaoer.xxx.auth.entity.User;

/**
 * 对token进行操作的接口
 * @author admin
 *
 */
public interface  TokenManagerService {
	/**
	 * 创建一个token关联上指定用户
	 * 
	 * @param userId
	 *            指定用户的id
	 * @return 生成的token
	 */
	public String createToken(User user) throws Exception;


	

	/**
	 * 清除token
	 * 
	 * @param userId
	 *            登录用户的id
	 */
	public void deleteToken(long userId);
	
	public UserMode getToken(Long userId);
	
	public ResultVo<UserMode> checkToken(String token);
}
