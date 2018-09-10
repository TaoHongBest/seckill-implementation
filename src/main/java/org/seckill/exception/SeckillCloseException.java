package org.seckill.exception;

/**
 * Seckill closure exception
 *
 * @author taohong on 05/09/2018
 */
public class SeckillCloseException extends SeckillException {
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
