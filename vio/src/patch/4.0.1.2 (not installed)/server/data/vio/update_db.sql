/* 
этот патч не поставлен 
проблема со временем решил из-под рута mysql
SET @@global.time_zone='+06:00';

*/


ALTER TABLE file MODIFY data longblob;

ALTER TABLE history MODIFY COLUMN sys_data timestamp DEFAULT 0;
ALTER TABLE report MODIFY COLUMN sys_date timestamp DEFAULT 0;
ALTER TABLE report_file MODIFY COLUMN sys_date timestamp DEFAULT 0;
ALTER TABLE account_usr MODIFY COLUMN sys_date timestamp DEFAULT 0;

/* написать по 2 триггера на 4 таблицы */
/* триггер на изменение даты +5 часов при вставке новой записи*/
DELIMITER $$
CREATE TRIGGER insert_report_time 
before INSERT 
ON test 
FOR EACH ROW
  set new.sys_data = CONVERT_TZ(CURRENT_TIMESTAMP, '+00:00','+5:00');
$$
DELIMITER ;


/* триггер на изменение даты +5 часов при изменении записи*/
DELIMITER $$
CREATE TRIGGER update_report_time
before update 
ON test 
FOR EACH ROW
  set new.sys_data = CONVERT_TZ(CURRENT_TIMESTAMP, '+00:00','+5:00');
$$
DELIMITER ;



update history
set sys_data = CONVERT_TZ(sys_data, '+00:00','+5:00');

update report
set sys_date = CONVERT_TZ(sys_date, '+00:00','+5:00');

/*

report_file, account_usr 

*/