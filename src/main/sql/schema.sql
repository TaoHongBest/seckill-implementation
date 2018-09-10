-- Scripts to initialize database

-- Create database
# CREATE DATABASE seckill;
# -- Use seckill
USE seckill;
-- Create seckill table
CREATE TABLE IF NOT EXISTS seckill (
  seckill_id  BIGINT       NOT NULL AUTO_INCREMENT
  COMMENT 'Item ID',
  name        VARCHAR(120) NOT NULL
  COMMENT 'Item name',
  number      INT          NOT NULL
  COMMENT 'Item quantity',
  start_time  TIMESTAMP    NOT NULL
  COMMENT 'Seckill start time',
  end_time    TIMESTAMP    NOT NULL
  COMMENT 'Seckill end time',
  create_time TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT 'Order creating time',
  PRIMARY KEY (seckill_id),
  KEY idx_start_time(start_time),
  KEY idx_end_time(end_time),
  KEY idx_create_time(create_time)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1000
  DEFAULT CHARSET = UTF8MB4
  COMMENT = 'Seckill table';
-- Drop this table for testing
# DROP TABLE IF EXISTS seckill;

-- Initialize the data
INSERT INTO seckill (name, number, start_time, end_time)
VALUES ('2000元秒杀iPhone 8', 100, '2018-09-03 00:00:00', '2018-09-04 00:00:00'),
       ('4000元秒杀iPhone X', 50, '2018-09-03 00:00:00', '2018-09-04 00:00:00'),
       ('1000元秒杀iPhone 7', 100, '2018-09-03 00:00:00', '2018-09-04 00:00:00'),
       ('500元秒杀iPhone 6s', 200, '2018-09-03 00:00:00', '2018-09-04 00:00:00');

-- Successful seckill table
-- Info regarding user login authentication
CREATE TABLE success_killed (
  seckill_id  BIGINT    NOT NULL
  COMMENT 'Item ID',
  user_phone  BIGINT    NOT NULL
  COMMENT 'User number',
  state       TINYINT   NOT NULL
  COMMENT 'Status token: -1:invalid 0:success 1:payed 2:dispatched',
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT 'Order creating time',
  PRIMARY KEY (seckill_id, user_phone), /* Composite primary key */
  KEY idx_create_time(create_time)
)
  ENGINE InnoDB
  DEFAULT CHARSET = UTF8MB4
  COMMENT 'Successful seckill table';

# -- Connecting to database console
# mysql -u root -p