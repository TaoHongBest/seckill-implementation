package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @author taohong on 04/09/2018
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Resource
    SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() {
        long id = 1000L;
        long number = 12345678901L;
        int insertCount = successKilledDao.insertSuccessKilled(id, number);
        System.out.println("insertCount: " + insertCount);
    }

    @Test
    public void queryByIdWithSeckill() {
        long id = 1000L;
        long number = 12345678901L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id, number);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }
}