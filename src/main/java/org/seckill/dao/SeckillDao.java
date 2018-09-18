package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author taohong on 03/09/2018
 */
public interface SeckillDao {
    /**
     * Stock reduction when an time get seckilled
     *
     * @param seckillId
     * @param killTime, corresponds to create_time in database
     * @return the number of updated columns. It should >=1, if 0, insertion failed
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * Query to seckill item by id
     *
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * Query to seckill item list by offset
     *
     * @param offset
     * @param limit
     * @return
     */
    // Java does not store the name of formal parameter. It's like arg0, arg1, etc.
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * Use data-storing procedure to execute seckill
     *
     * @param paramMap
     */
    void killByProcedure(Map<String, Object> paramMap);
}