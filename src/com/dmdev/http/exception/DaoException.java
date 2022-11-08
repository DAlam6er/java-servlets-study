package com.dmdev.http.exception;

public class DaoException extends RuntimeException
{
    public DaoException(Throwable throwable)
    {
        super(throwable);
    }
}
