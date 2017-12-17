package com.huixiaoer.utils.exception;

/**
 * Created by zhanglong on 16-7-11.
 */
public class StorageException extends RuntimeException {
    public StorageException(Throwable cause) {
        super(cause);
    }

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public StorageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}