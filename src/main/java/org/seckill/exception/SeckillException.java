package org.seckill.exception;

/**
 * Exceptions related to Seckill
 *
 * @author taohong on 09/09/2018
 */
public class SeckillException extends RuntimeException {
    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
