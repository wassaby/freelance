CREATE TABLE `account_type` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into account_type(name) values('Администратор системы');
insert into account_type(name) values('Пользователь системы');


CREATE TABLE `account_usr` (
  `id` int(11) NOT NULL auto_increment,
  `login` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `account_type_id` int(3) NOT NULL,
  `fname` varchar(100),
  `lname` varchar(100),
  `ip_address` varchar(100) NOT NULL,
  `phone_identifier` varchar(100) NOT NULL,
  `sys_date` timestamp NOT NULL  default CURRENT_TIMESTAMP,
  `last_login` timestamp,
  `is_blocked` int(1) NOT NULL default 0,
  PRIMARY KEY  (`id`),
  KEY `account_type_id` (`account_type_id`),
  CONSTRAINT `fk_account_type_id` FOREIGN KEY (`account_type_id`) REFERENCES `account_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into account_usr(login, password, fname, lname, phone_identifier, ip_address, account_type_id, last_login) values('Admin', 'c21hbmFnZXI=', 'Администратор', 'Системы', '+77773865312', '127.0.0.1', 1, CURRENT_TIMESTAMP);

create TABLE `file` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(500) NOT NULL,
  `size` int(11) NOT NULL,
  `data` longblob NOT NULL,
  `content_type` varchar(200) NOT NULL,
  `longitude` varchar(255),
  `latitude` varchar(255),
  `authorization_required` int(11) NOT NULL default '1',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `report_type` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into report_type(name) values('Нарушения');

CREATE TABLE `report_status` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into report_status(name) values('Ожидает рассмотрения');
insert into report_status(name) values('Принято модератором');
insert into report_status(name) values('Ожидает отправки в ');
insert into report_status(name) values('Отправлено в ');
insert into report_status(name) values("Отклонено");
insert into report_status(name) values("Решено");

commit;

CREATE TABLE `report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `report_type_id` int(11) NOT NULL DEFAULT 1,
  `report_status_id` int(11) NOT NULL DEFAULT 1,
  `header` varchar(4096),
  `comment` varchar(4096),
  `sys_date` timestamp NOT NULL  default CURRENT_TIMESTAMP,
  `is_read` int(1) NOT NULL default 0,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `report_type_id` (`report_type_id`),
  KEY `report_status_id` (`report_status_id`),
  CONSTRAINT `fk_user_id_id` FOREIGN KEY (`user_id`) REFERENCES `account_usr` (`id`),
  CONSTRAINT `fk_report_type_id` FOREIGN KEY (`report_type_id`) REFERENCES `report_type` (`id`),
  CONSTRAINT `fk_report_status_id` FOREIGN KEY (`report_status_id`) REFERENCES `report_status` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table `report_file` (
	`id` int(11) not null auto_increment,
	`file_id` int(11) not null,
	`report_id` int(11) not null,
	`sys_date` timestamp NOT NULL  default CURRENT_TIMESTAMP,
	primary key (`id`),
	key `file_id` (`file_id`),
	key `report_id` (`report_id`),
	CONSTRAINT `fk__file_id` FOREIGN KEY (`file_id`) REFERENCES `file` (`id`),
    CONSTRAINT `fk__report_id` FOREIGN KEY (`report_id`) REFERENCES `report` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* Formatted on 2011/02/22 16:34 (QP5 v5.50) */
CREATE TABLE `sized_images_cashe` (
  `file_id` int(11) NOT NULL,
  `width` int(11) NOT NULL,
  `height` int(11) NOT NULL,
  `data` longblob NOT NULL,
  UNIQUE KEY `IDX_SIZED_IMG_CASHE` (`file_id`,`width`,`height`),
  KEY `IDX_SIZED_IMG_CASHE_FILE_ID` (`file_id`),
  CONSTRAINT `fk_file_id` FOREIGN KEY (`file_id`) REFERENCES `file` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


alter table report
add column published int(1) not null default 0;

alter table report_file
add column scan int(1) not null default 0;

create table history (
  id int(11) not null auto_increment,
  report_id int(11) not null,
  old_status_id int(11),
  new_status_id int(11),
  sys_data timestamp NOT NULL default CURRENT_TIMESTAMP,
  comment varchar(4096),
  primary key(id),
  key report_id (report_id),
  key old_status_id (old_status_id),
  key new_status_id (new_status_id),
  constraint fk_report_id foreign key (report_id) references report(id),
  constraint fk_old_status foreign key (old_status_id) references report_status(id),
  constraint fk_new_status foreign key (new_status_id) references report_status(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


drop table sized_images_cashe;

alter table report_type
add report_type_code varchar(50);

update report_type
set name = "ЭКО-нарушения", report_type_code="eco"
where id = 1;

insert into report_type(name, report_type_code) values("АВТО-нарушения", "auto");
insert into report_type(name, report_type_code) values("ЗОО-нарушения", "zoo");

create table instances(
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into instances(name) values ("ГУВД г.Алматы");
insert into instances(name) values ("Экологический центр");
insert into instances(name) values ("Общество защиты животных");
insert into instances(name) values("Управление пассажирского транспорта города Алматы");
insert into instances(name) values("Управление культуры города Алматы");
insert into instances(name) values("Управление природных ресурсов и регулирования природопользования");

alter table report
add instance_id int(11),
add key instance_id (instance_id),
add constraint fk_instance_id foreign key (instance_id) references instances(id);

alter table history
add old_instance_id int(11),
add new_instance_id int(11),
add key old_instance_id (old_instance_id),
add key new_instance_id (new_instance_id),
add constraint fk_old_instance_id foreign key (old_instance_id) references instances(id),
add constraint fk_new_instance_id foreign key (new_instance_id) references instances(id);

alter table report
add report_number varchar(255);

create UNIQUE index report_number_index on report (report_number);

alter table report_file
add deleted int(2);

alter table file
drop latitude;

alter table file
drop longitude;

alter table report
add longitude varchar(200);

alter table report
add latitude varchar(200);

insert into instances(name) values("Solve It");

alter table report drop index report_number_index;
 
commit;
