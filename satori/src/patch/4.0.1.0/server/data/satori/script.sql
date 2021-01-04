CREATE TABLE `account_type` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into account_type(name) values('System administrator');
insert into account_type(name) values('Content-manager of system');
insert into account_type(name) values('User of mobile software');


CREATE TABLE `account_usr` (
  `id` int(11) NOT NULL auto_increment,
  `login` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `account_type_id` int(3) NOT NULL,
  `ip_address` varchar(100),
  `unique_identifier` varchar(100),
  `sys_date` timestamp NOT NULL  default CURRENT_TIMESTAMP,
  `last_login` timestamp,
  `is_blocked` int(1) NOT NULL default 0,
  PRIMARY KEY  (`id`),
  KEY `account_type_id` (`account_type_id`),
  CONSTRAINT `fk_account_type_id` FOREIGN KEY (`account_type_id`) REFERENCES `account_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into account_usr(login, password, unique_identifier, ip_address, account_type_id, last_login) values('admin', 'c21hbmFnZXI=', 'system_admin', '127.0.0.1', 1, CURRENT_TIMESTAMP);
insert into account_usr(login, password, unique_identifier, ip_address, account_type_id, last_login) values('manager', 'c21hbmFnZXI=', 'content_manager', '127.0.0.1', 2, CURRENT_TIMESTAMP);

create TABLE `file` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(500) NOT NULL,
  `size` int(11) NOT NULL,
  `data` longblob NOT NULL,
  `content_type` varchar(200) NOT NULL,
  `authorization_required` int(11) NOT NULL default '1',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into file(id) values(-111);

CREATE TABLE `language` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) NOT NULL,
  `code` varchar(100) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into language(name, code) values('English', 'en');
insert into language(name, code) values('Svenska', 'sv');
insert into language(name, code) values('Nederlands', 'nl');
insert into language(name, code) values('Deutsch', 'de');
insert into language(name, code) values('Français', 'fr');
insert into language(name, code) values('Русский', 'ru');
insert into language(name, code) values('Italiano', 'it');
insert into language(name, code) values('Español', 'es');

create table days(
  id int(11) not null auto_increment,
  name varchar(50) not null,
  primary key(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into days(name) values('Day 1');
insert into days(name) values('Day 2');
insert into days(name) values('Day 3');
insert into days(name) values('Day 4');
insert into days(name) values('Day 5');
insert into days(name) values('Day 6');
insert into days(name) values('Day 7');
insert into days(name) values('Day 8');
insert into days(name) values('Day 9');
insert into days(name) values('Day 10');
insert into days(name) values('Day 11');
insert into days(name) values('Day 12');
insert into days(name) values('Day 13');
insert into days(name) values('Day 14');
insert into days(name) values('Day 15');
insert into days(name) values('Day 16');
insert into days(name) values('Day 17');
insert into days(name) values('Day 18');
insert into days(name) values('Day 19');
insert into days(name) values('Day 20');
insert into days(name) values('Day 21');

create table item(
id int(11) not null auto_increment,
lang_id int(11) not null default 1,
name varchar(1024),
pid int(11),
insert_date timestamp not null default current_timestamp,
primary key (id, lang_id),
constraint fk_item_lang foreign key (lang_id) references language(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table notification(
id int(11) not null auto_increment,
`text` longtext not null,
item_id int(11) not null,
day_id int(11) not null,
lang_id int(11) not null,
insert_date timestamp not null default current_timestamp,
primary key (id),
key item_id (item_id),
key day_id (day_id),
key lang_id (lang_id),
constraint fk_notif_day foreign key (day_id) references days (id),
constraint fk_item_notif foreign key (item_id) references item (id),
constraint fk_lang_notif foreign key (lang_id) references language (id)
) engine=innodb default charset=utf8;

create table content_article(
id int(11) not null auto_increment,
`text` longtext not null,
notification_id int(11) not null,
file_id int(11),
lang_id int(11) not null,
insert_date timestamp not null default current_timestamp,
primary key (id),
key notification_id (notification_id),
key file_id (file_id),
key lang_id_ct (lang_id),
constraint fk_notif_article foreign key (notification_id) references notification (id),
constraint fk_file_notif foreign key (file_id) references file (id),
constraint fk_lang_ct foreign key (lang_id) references language (id)
) engine=innodb default charset=utf8;




commit;
