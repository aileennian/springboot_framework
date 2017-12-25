/*
 * Copyright (C), 2012-2013, 苏宁云商股份有限公司电子商务经营总部北京研发中心
 * FileName: JacksonMapper.java
 * Description: <code>Jackson JSON</code>工具类
 * Author: 念小玲
 * Date: 2013-4-24 - 下午2:10:39     
 * History: 
 * <author>念小玲</author>
 * <time>2013-5-6 - 下午3:20:39</time>
 * <version>1.0.1</version>
 * <desc>增加注释文档</desc>
 */
package com.nian.utils.json;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
 

/**
 * <code>Jackson JSON</code>工具类
 * <p>
 * 使用了单例模式
 * </p>
 * 
 * @author 牛绍刚
 * @link http://http://wangym.iteye.com/blog/738933 两款JSON类库Jackson与JSON-lib的性能对比(新增第三款测试)
 */
public class JacksonMapper {
    /** 记录日志的变量 */
	protected static Logger logger = LogManager.getLogger(JacksonMapper.class);
    /** 静态ObjectMapper */
    private ObjectMapper         mapper;

    private static JacksonMapper jacksonMapper = new JacksonMapper();

    private static final int     ARRAY_MAX     = 1024;

    /**
     * 私有构造函数
     */
    private JacksonMapper() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 获得ObjectMapper实例
     * 
     * @return ObjectMapper
     */
    public static ObjectMapper getInstance() {
        return jacksonMapper.mapper;
    }

    /**
     * JSON对象转换为JavaBean
     * 
     * @param json JSON对象
     * @param valueType Bean类
     * @return 泛型对象
     */
    public static <T> T jsonToBean(String json,
                                   Class<T> valueType) {
        if (json == null || json.length() == 0) {
            return null;
        }
        try {
            return getInstance().readValue(json, valueType);
        } catch (JsonParseException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * JavaBean转换为JSON字符串
     * 
     * @param bean JavaBean对象
     * @return json字符串
     */
    public static String beanToJson(Object bean) {
        StringWriter sw = new StringWriter();
        JsonGenerator gen = null;
        try {
            gen = new JsonFactory().createJsonGenerator(sw);
            getInstance().writeValue(gen, bean);
            gen.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return sw.toString();
    }

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @param <T>
     * 
     * @param json
     * @param type
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> jsonToList(String json,
                                         Class<T> clazz) {
        T[] t = (T[]) Array.newInstance(clazz, ARRAY_MAX);
        try {
            t = (T[]) getInstance().readValue(json, t.getClass());
            return (List<T>) Arrays.asList(t);
        } catch (JsonGenerationException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @param t
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String listToJson(List<?> t) {
        try {
            return getInstance().writeValueAsString(t);
        } catch (JsonGenerationException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

}
