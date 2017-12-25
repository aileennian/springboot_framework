package com.nian.xxx.auth.service.impl;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.nian.api.ResultStatus;
import com.nian.api.ResultVo;
import com.nian.auth.UserMode;
import com.nian.auth.jwt.CheckResult;
import com.nian.auth.jwt.JwtConstant;
import com.nian.auth.jwt.Subject;
import com.nian.auth.jwt.TokenMgr;
import com.nian.redis.RedisString;
import com.nian.utils.json.JacksonMapper;
import com.nian.xxx.auth.entity.User;
import com.nian.xxx.auth.service.TokenManagerService;

import io.jsonwebtoken.Claims;

/**
 * 通过Redis存储和验证token的实现类
 * 
 * @author admin
 *
 */
@Service
public class TokenManagerServiceImpl implements TokenManagerService {
	protected Logger logger = LogManager.getLogger(TokenManagerService.class);
	@Autowired
	@Qualifier("redisClient")
	private RedisString redis;

	/**
	 * 生成token，并保存在redis中
	 * 
	 * @param userMode
	 * @return
	 * @throws Exception
	 */
	public String createToken(User user) throws Exception {
		Long userId = user.getUserId();

		Subject tokenMode = new Subject();
		tokenMode.setUserId(user.getUserId());
		tokenMode.setLoginName(user.getUserName());
		tokenMode.setRoleIds(user.getRoleIds());
		tokenMode.setUuid(UUID.randomUUID().toString().replace("-", ""));
		String subject = JacksonMapper.beanToJson(tokenMode);

		// 使用uuid作为源token
		// String token = UUID.randomUUID().toString().replace("-", "");
		String token = TokenMgr.createToken(JwtConstant.JWT_ID, subject, user.getRoleIds(), 0l);

		// redis.setStringSerializer(stringSerializer);
		redis.set(String.valueOf(userId), token, TimeUnit.MILLISECONDS.toSeconds(JwtConstant.REDIS_REFRESH_TTL));

		return token;
	}



	/**
	 * 通过token判断用户
	 */
	public ResultVo<UserMode> checkToken(String token) {
		ResultVo<UserMode> vo = TokenMgr.checkToken(token);
		if (!vo.isSuccess()) return vo;
		
		UserMode userMode = vo.getDate();		
		if (redis.exists(userMode.getUserId().toString())) {
			redis.expire(userMode.getUserId().toString(),
					TimeUnit.MILLISECONDS.toSeconds(JwtConstant.REDIS_REFRESH_TTL));
		} else {
			vo.setErrcode(ResultStatus.TOKEN_REDIS_EXPIRE.getCode());
			vo.setMessage(ResultStatus.TOKEN_REDIS_EXPIRE.getMessage());
		}
		return vo;
	}

	/**
	 * 通过token判断用户
	 */
	public String getTokenFromServer(Long userId) {
		if (userId == null) {
			return null;
		}
		return redis.get(userId.toString());
	}
	
	@Override
	public void deleteToken(long userId) {
		/**
		 * 解析token对象，通过客户端判断，再通过redis判断一次
		 */
	
		String token = redis.get(String.valueOf(userId));
		redis.del(token);		
		
		CheckResult checkResult = TokenMgr.validateJWT(token);
		if (checkResult.isSuccess()) {
			Claims claims = checkResult.getClaims();
			claims.setExpiration(new Date());
		}	

	}

	@Override
	public UserMode getToken(Long userId) {
		if (userId == null) {
			return null;
		}
		String token = redis.get(userId.toString());		
		UserMode userMode = TokenMgr.checkToken(token).getDate();
		
		return userMode;
	}

	
	/**
	 * 驼过jwt得到token 得到token
	 *//*
	public UserMode checkToken(String authtoken) {
		*//**
		 * 解析token对象，通过客户端判断，再通过redis判断一次
		 *//*
		CheckResult checkResult = TokenMgr.validateJWT(authtoken);
		if (checkResult.isSuccess()) {
			Claims claims = checkResult.getClaims();
			Subject subject = JacksonMapper.jsonToBean(claims.getSubject(), Subject.class);
			UserMode userMode = new UserMode();
			userMode.setLoginName(subject.getLoginName());
			userMode.setRoleIds(subject.getRoleIds());
			userMode.setToken(authtoken);
			userMode.setUserId(subject.getUserId());
			return userMode;
		}
		
		switch (checkResult.getErrCode()) {
		// 签名过期，返回过期提示码
		case AWTConstant.JWT_ERRCODE_EXPIRE:
			logger.error(ResultStatus.USER_LOGIN_EXPIRE.getCode(),ResultStatus.USER_LOGIN_EXPIRE.getMessage());
			// 签名验证不通过
			break;
		case AWTConstant.JWT_ERRCODE_FAIL:
			logger.error(ResponseMgr.noAuth());
			break;
		default:
			break;
		}
		return null;
	}*/


 
	
}
