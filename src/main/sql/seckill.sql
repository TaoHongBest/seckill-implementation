-- Seckill execution data-storing procedure
DELIMITER $$ -- console ; transformed to "$ $"
-- Define data-storing procedure
-- Parameters: in: params inputted ; out: params to output
-- row_count(): return the number of rows influenced by the previous sql of modifying type (DELETE, INSERT, UPDATE)
-- row_count(): 0: hasn't modified data; >0: numbers of influenced rows; <0: sql error/modifying sql hasn't been executed
CREATE PROCEDURE seckill.execute_seckill
  (in v_seckill_id bigint, in v_phone bigint, in v_kill_time timestamp, out r_result int)
  BEGIN
    DECLARE insert_count INT DEFAULT 0;
    START TRANSACTION;
    INSERT IGNORE INTO success_killed (seckill_id, user_phone, create_time)
    VALUES (v_seckill_id, v_phone, v_kill_time);
    SELECT row_count() INTO insert_count;
    IF (insert_count = 0)
    THEN
      ROLLBACK;
      SET r_result = -1;
    ELSEIF (insert_count < 0)
      THEN
        ROLLBACK;
        SET r_result = -2;
    ELSE
      UPDATE seckill
      SET number = number - 1
      WHERE seckill_id = v_seckill_id
        AND end_time > v_kill_time
        AND start_time < v_kill_time
        AND number > 0;
      SELECT row_count() INTO insert_count;
      IF (insert_count = 0)
      THEN
        ROLLBACK;
        SET r_result = 0;
      ELSEIF (insert_count < 0)
        THEN
          ROLLBACK;
          SET r_result = -2;
      ELSE
        COMMIT;
        SET r_result = 1;
      END IF;
    END IF;
  END;
$$
-- Data-storing procedure definition finished

DELIMITER ;

SET @r_result = -3;
-- Storing executing
CALL execute_seckill(1001, 12345678910, now(), @r_result);
-- Acquire result
SELECT @r_result;

-- Data-storing procedure
-- 1: Date-storing procedure optimization: transaction rowLock's holding time
-- 2: Don't rely on data-storing procedure too much
-- 3: Simple logic can apply to data-storing procedure
-- 4: QPS: One seckill   order 6000/qps

