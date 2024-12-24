package com.hc.exceptions;

/**
 * @author 小盒
 * @verson 1.0
 */
public class NetworkException  extends RuntimeException{
    public NetworkException() {
    }

    public NetworkException(String message) {
        super(message);
    }

    public NetworkException(Throwable cause) {
        super(cause);
    }

}
