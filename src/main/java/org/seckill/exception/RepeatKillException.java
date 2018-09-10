package org.seckill.exception;

/**
 * Repeated seckill exception （RuntimeException)
 * Spring only accepts RuntimeException. It will not roll-back when receiving a Checked Exception
 *
 * @author taohong on 05/09/2018
 */
public class RepeatKillException extends RuntimeException {
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
