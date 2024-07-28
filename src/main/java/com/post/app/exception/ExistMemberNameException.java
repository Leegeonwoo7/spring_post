package com.post.app.exception;

public class ExistMemberNameException extends RuntimeException {
    public ExistMemberNameException(String message) {
        super(message);
    }

    public ExistMemberNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistMemberNameException(Throwable cause) {
        super(cause);
    }

    public ExistMemberNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
