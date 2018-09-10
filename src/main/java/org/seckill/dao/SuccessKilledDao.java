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
     * @param seckillId
     * @param userPhone
     * @return the number of inserted columns. If 0, insertion failed
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * Search SuccessKilled entity(containing Seckill entity) by secKillId, and return it
     *
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}