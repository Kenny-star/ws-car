package com.kenny.Utils.exceptions;

public class NotManufacturedException extends RuntimeException{

    public NotManufacturedException() { }

    public NotManufacturedException(String message) {
        super(message);
    }

    public NotManufacturedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotManufacturedException(Throwable cause) {
        super(cause);
    }


}