package com.post.exception;

public class ExistMemberEmailException extends RuntimeException {
    public ExistMemberEmailException(String message) {
        super(message);
    }

    public ExistMemberEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistMemberEmailException(Throwable cause) {
        super(cause);
    }

    public ExistMemberEmailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
