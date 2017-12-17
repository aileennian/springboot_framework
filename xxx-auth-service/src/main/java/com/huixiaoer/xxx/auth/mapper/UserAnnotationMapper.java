/**
 * Copyright [2016-2017] [yadong.zhang]
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.huixiaoer.xxx.auth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.huixiaoer.xxx.auth.entity.User;


/**
 * 只是例子，实际工作中，琮是以xml为主，作为规范
 * @author admin
 *
 */
public interface UserAnnotationMapper {

    @Select("SELECT USER_NAME FROM user")
    @Results({
            @Result(property = "userName",  column = "USER_NAME")
    })
    List<User> list();
    
  @Insert({
  "insert into user (" +
          "USER_NAME, " +
          "PASSWORD, " +
          "lAST_LOGIN_TIME," +
          "LOGIN_CLIENT_IP, " +
          ")",
  "values (" +
          "#{userName,jdbcType=VARCHAR}, " +
          "#{password,jdbcType=VARCHAR}, " +
          "#{lastLoginTime,jdbcType=DATE}, " +
          "#{loginClientIp,jdbcType=VARCHAR} " +
          ")"
  })    
	public void insertUser(User user);
}
