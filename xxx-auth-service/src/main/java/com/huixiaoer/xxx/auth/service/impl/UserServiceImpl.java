package com.huixiaoer.xxx.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.huixiaoer.mybatis.base.DynamicDataSource;
import com.huixiaoer.mybatis.multi.DynamicDataSourceType;
import com.huixiaoer.xxx.auth.entity.User;
import com.huixiaoer.xxx.auth.mapper.UserMapper;
import com.huixiaoer.xxx.auth.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserMapper userMapper;
	
	@Override
	@DynamicDataSource(DynamicDataSourceType.write)
	public User findByLoginName(String userName) {
		return userMapper.findByUsername(userName);
	}

	@Override
	public User findByUserId(Long userId) {		
		User user = userMapper.findByUserId(userId);
		return user;
	}

	@Override
	public Page<User> findAllByPage(int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		return userMapper.findAllByPage();
	}
	
   
   
	/**
	 * 1.首先确认数据库支持事务。即为InnoDB。
		方案一：手动回滚。给注解加上参数如：@Transactional(rollbackFor=Exception.class)
		方案二：如上述分析。MyException改为继承RuntimeException的异常。并且在service上层要继续捕获这个异常并处理
		方案三：在service层方法的catch语句中增加：TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();语句，手动回滚，这样上层就无需去处理异常
	 * @throws Exception 
	 */
	//@Override
	@Transactional(rollbackFor=Exception.class,readOnly=true)
    public void insertUser(User u) throws Exception {
    	userMapper.insertUser(u);
    	//throw new Exception();
    }
	
	
}
