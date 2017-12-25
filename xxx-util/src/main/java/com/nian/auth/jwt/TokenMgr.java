package com.nian.auth.jwt;

import java.security.SignatureException;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nian.api.ResultStatus;
import com.nian.api.ResultVo;
import com.nian.auth.UserMode;
import com.nian.utils.json.JacksonMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * json web token认证工具包
 * https://github.com/jwtk/jjwt
 * 
 * @author admin
 *
 */
public class TokenMgr {
	protected final static Logger logger = LogManager.getLogger(TokenMgr.class);
	/**
	 * 由字符串生成加密key
	 * 
	 * @return
	 */
	public static SecretKey generalKey() {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		
		String stringKey = JwtConstant.JWT_SECRET;
		byte[] encodedKey = Base64.decodeBase64(stringKey);
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, signatureAlgorithm.getJcaName());		
		//SecretKey signingKey = new SecretKeySpec(encodedKey, signatureAlgorithm.getJcaName());
		return key;
	}

	/**
		public static String createJWT() {
		        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		        SecretKey secretKey = generalKey();
		        JwtBuilder builder = Jwts.builder()
		                .setId(id)                                      // JWT_ID
		                .setAudience("")                                // 接受者
		                .setClaims(null)                                // 自定义属性
		                .setSubject("")                                 // 主题
		                .setIssuer("")                                  // 签发者
		                .setIssuedAt(new Date())                        // 签发时间
		                .setNotBefore(new Date())                       // 失效时间
		                .setExpiration(long)                                // 过期时间
		                .signWith(signatureAlgorithm, secretKey);           // 签名算法以及密匙
		        return builder.compact();
		}
	 * 
	 * @param id：
	 * @param subject:guid等属笥主题
	 * @param ttlMillis:期期时间
	 * @return
	 * @throws Exception
	 */
	public static String createToken(String id, String userId,String roleIds, long ttlMillis) throws Exception {
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		
		//TODO:key的生成?
		SecretKey secretKey = generalKey();
		
		JwtBuilder builder = 
				Jwts.builder()
				.setId(id)
				.setIssuedAt(now)
				.setSubject(userId)
				.claim("roleIds", roleIds)
				.signWith(SignatureAlgorithm.HS256,secretKey);
		
		
		if (ttlMillis<=0) ttlMillis =JwtConstant.JWT_REFRESH_TTL;
		//TODO：设置过期时间,需要设置么？
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
		
		return builder.compact();
	}
	
	
//	public static String  setExpiration(String token){
//		
//	}
	
    /**
     * 验证JWT
     * @param jwtStr
     * @return
     */
    public static CheckResult validateJWT(String tokens) {
        CheckResult checkResult = new CheckResult();
        Claims claims = null;
        try {
            claims = parseJWT(tokens);
            checkResult.setSuccess(true);
            checkResult.setClaims(claims);
        } catch (ExpiredJwtException e) {
            checkResult.setErrCode(String.valueOf(ResultStatus.JWT_LOGIN_EXPIRE.getCode()));
            checkResult.setSuccess(false);
        } catch (SignatureException e) {
            checkResult.setErrCode(String.valueOf(ResultStatus.JWT_AUTH_FAIL.getMessage()));
            checkResult.setSuccess(false);
        } catch (Exception e) {
            checkResult.setErrCode(String.valueOf(ResultStatus.JWT_FAIL.getMessage()));
            checkResult.setSuccess(false);
        }
        return checkResult;
    }
    
    
    
    
    public static ResultVo<UserMode> checkToken(String token) {
    	/**
		 * 解析token对象，通过客户端判断，再通过redis判断一次
		 */
    	ResultVo<UserMode> vo = null;
    	
    	try{
    		Claims claims = parseJWT(token);    		
    		setExpireDate(claims , 0l);    		
    		Subject subject = JacksonMapper.jsonToBean(claims.getSubject(), Subject.class);
 			UserMode userMode = new UserMode();
 			userMode.setLoginName(subject.getLoginName());
 			userMode.setRoleIds(subject.getRoleIds());
 			userMode.setToken(token);
 			userMode.setUserId(subject.getUserId());
 			vo = new ResultVo<UserMode>(userMode);
 			return vo;
        } catch (ExpiredJwtException e) {
        	vo = new ResultVo<UserMode>(ResultStatus.JWT_LOGIN_EXPIRE.getCode(),ResultStatus.JWT_LOGIN_EXPIRE.getMessage());
        } catch (SignatureException e) {
        	vo = new ResultVo<UserMode>(ResultStatus.JWT_AUTH_FAIL.getCode(),ResultStatus.JWT_AUTH_FAIL.getMessage());
        } catch (Exception e) {
        	vo = new ResultVo<UserMode>(ResultStatus.JWT_FAIL.getCode(),ResultStatus.JWT_FAIL.getMessage());
        }
    	return vo;
    }
    
    

	/**
	 * 
	 * 一般我们把验证操作作为中间件或者拦截器就行了
	 * 解密jwt
	 * 
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public static Claims parseJWT(String jwt) throws Exception {
		SecretKey key = generalKey();//签名
		Claims claims = Jwts.parser().
				setSigningKey(key).
				parseClaimsJws(jwt).
				getBody();
		return claims;
	}
	
	/**
	 * 重置过期时间
	 * @param token
	 * @param ttlMillis
	 */
	private static void setExpireDate(Claims claims,long ttlMillis ){
		try{
			if (ttlMillis<=0){
				ttlMillis = JwtConstant.JWT_REFRESH_TTL;
			}
			//Claims claims = parseJWT(token);
			long nowMillis = System.currentTimeMillis();
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			claims.setExpiration(exp);	
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	
	
//	/**
//	 * 
//	 * 一般我们把验证操作作为中间件或者拦截器就行了
//	 * 解密jwt
//	 * 
//	 * @param jwt
//	 * @return
//	 * @throws Exception
//	 */
//	public static Claims parseJWT1(String jwt) throws Exception {
//		SecretKey key = generalKey();//签名
//		Claims claims = Jwts.parser().
//				setSigningKey(key).
//				parseClaimsJws(jwt).
//				getBody();
//		
//		Jwts.parser().setSigningKey(key).setSigningKey(key).parse(jwt)
//		
//		return null;
//	}
//	

	
    /**
     * 测试
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {  
        Subject user = new Subject();  
        user.setLoginName("111");  
        user.setUserId(333L);  
        
        
		Subject tokenMode = new Subject();
		tokenMode.setUserId(11l);
		tokenMode.setLoginName("AA");
		tokenMode.setRoleIds(null);
		tokenMode.setUuid(UUID.randomUUID().toString().replace("-", ""));
		String subject = JacksonMapper.beanToJson(tokenMode);		// 使用uuid作为源token
		String token = TokenMgr.createToken(JwtConstant.JWT_ID, subject, user.getRoleIds(), JwtConstant.JWT_REFRESH_TTL);

		ResultVo vo = TokenMgr.checkToken(token);
		
		
        System.out.println(vo.getDate());  
    }  

}
