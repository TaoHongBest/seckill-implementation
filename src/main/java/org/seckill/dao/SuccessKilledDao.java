package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

/**
 * @author taohong on 03/09/2018
 */
public interface SuccessKilledDao {
    /**
     * Insert buying log, able to filter duplicates
     *
     * @param secKilledId
     * @param userPhone
     * @return the number of inserted columns. If 0, insertion failed
     */
    int insertSuccessKilled(@Param("secKilledId") long secKilledId, @Param("userPhone") long userPhone);

    /**
     * Search SuccessKilled entity(containing Seckill entity) by secKillId, and return it
     *
     * @param secKillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("secKilledId") long secKilledId, @Param("userPhone") long userPhone);
}