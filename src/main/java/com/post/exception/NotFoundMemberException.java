package com.post.exception;

public class NotFoundMemberException extends RuntimeException{
    public NotFoundMemberException(String message) {
        super(message);
    }
}
