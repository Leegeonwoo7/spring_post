package com.post.exception;

public class ExistMemberLoginIdException extends RuntimeException {
    public ExistMemberLoginIdException(String message) {
        super(message);
    }

    public ExistMemberLoginIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistMemberLoginIdException(Throwable cause) {
        super(cause);
    }

    public ExistMemberLoginIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
