package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * Service interface: design for the user
 * 3 aspects to consider: complexity of method definition, parameters, return type (type/exception)
 *
 * @author taohong on 05/09/2018
 */
public interface SeckillService {
    /**
     * @return all seckill log
     */
    List<Seckill> getSeckillList();

    /**
     * @param seckillId
     * @returna single seckill log
     */
    Seckill getById(long seckillId);

    /**
     * Expose the url address when seckill starts, otherwise expose system time and seckill starting time
     * Exposer is so-called DTO
     *
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * Execution of seckill
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, SeckillCloseException, RepeatKillException;
}
