package com.huixiaoer.xxx.auth.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huixiaoer.api.ResultVo;

@ControllerAdvice
@ResponseBody
public class ExceptionHandle {
	protected Logger logger = LogManager.getLogger(ExceptionHandle.class);
	
//	//NullPointerException
//    @ExceptionHandler(Throwable.class)
//    public ResultVo<String> handleException(Throwable e) {
//    	//e.printStackTrace();
//    	logger.error(e);
//    	ResultVo<String> vo = new ResultVo<String>();
//    	return vo.toError(e.getMessage());
//    }
//    	
	
    @ExceptionHandler(Exception.class)
    public ResultVo<String> handleException(Exception e) {
    	e.printStackTrace();
    	//logger.trace(e);
    	//logger.error(e.getMessage());
    	//logger.error(e);
    	String msg = e.getMessage();
    	if (msg==null){
    		msg = e.toString();
    	}
    	ResultVo<String> vo = new ResultVo<String>(1,msg);
//    	
//    	if (httpStatus!=null){
//    		vo.setErrcode(httpStatus.value());
//    	}
    	return vo;
    }

}
