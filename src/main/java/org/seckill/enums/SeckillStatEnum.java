package org.seckill.enums;

/**
 * Use enum to indict constant type
 *
 * @author taohong on 10/09/2018
 */
public enum SeckillStatEnum {
    SUCCESS(1, "Seckill Success"),
    END(0, "Seckill End"),
    REPEAT_KILL(-1, "Repeated Seckill"),
    INNER_ERROR(-2, "System Error"),
    DATA_REWRITE(-3, "Data Tampering");

    private int state;
    private String stateInfo;

    SeckillStatEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static SeckillStatEnum stateOf(int index) {
        for (SeckillStatEnum stateEnum : values()) {
            if (stateEnum.getState() == index) {
                return stateEnum;
            }
        }
        return null;
    }
}

