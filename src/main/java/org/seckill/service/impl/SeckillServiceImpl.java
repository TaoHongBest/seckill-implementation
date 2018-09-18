package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dao.cache.RedisDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * @author taohong on 09/09/2018
 */
// Annotation typs: @Component @Service @Dao @Controller
@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    // Inject Service dependency
    @Autowired
    private SeckillDao seckillDao;
    // Inject Service dependency
    @Autowired
    private SuccessKilledDao successKilledDao;

    @Autowired
    private RedisDao redisDao;

    // Salt for md5, used to complicate password
    private final String salt = "qeworbsnld35%^&(2@£45g420908234v£$23d9@23r";

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        // Optimizable point: cache optimization: Maintain consistence based on timeout
        // 1: Access redis
        Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null) {
            // 2: Access database
            seckill = seckillDao.queryById(seckillId);
            if (seckill == null) {
                return new Exposer(false, seckillId);
            } else {
                // 3: put to redis
                redisDao.putSeckill(seckill);
            }
        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        // System's current time
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        // The process of transforming a specific String, irreversible
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Transactional
    /**
     * Advantages of using annotation to control transaction:
     * 1: Dev team have consistent convention，explicit coding style to annotate transaction method
     * 2: Ensure minimum execution time of transaction method, do not insert other network operation, like PRC/HTTP Request/
     * or separate them to the external of transaction methods
     * 3: Not all methods need transaction, like only one edit operation, read-only, which do not need transaction control
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }
        // Execution of seckill logistic: stock reduction + buying record
        Date nowTime = new Date();
        try {
            // Record a buying
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            if (insertCount <= 0) {
                // Repeated seckill
                throw new RepeatKillException("seckill repeated");
            } else {
                // Stock reduction, hot-spot goods competition
                int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
                if (updateCount <= 0) {
                    // Log update failed, seckill has ended, rollback
                    throw new SeckillCloseException("seckill is closed");
                } else {
                    // Seckill succeed, commit
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    // Something about data dictionary
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            // All compile run exception transformed to run time exception
            throw new SeckillException("seckill inner error: " + e.getMessage());
        }
        // Stock reduction
    }
}
