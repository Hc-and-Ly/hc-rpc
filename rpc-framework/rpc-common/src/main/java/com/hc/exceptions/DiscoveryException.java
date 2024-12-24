package com.hc.exceptions;

/**
 * @author 小盒
 * @verson 1.0
 */
public class DiscoveryException extends RuntimeException{
    public DiscoveryException() {
    }

    public DiscoveryException(Throwable cause) {
        super(cause);
    }
    public DiscoveryException(String message) {
        super(message);
    }


}
