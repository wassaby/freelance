create table item_status(
id int(11) not null auto_increment,
name varchar(255) not null,
primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into item_status(name) values("Active");
insert into item_status(name) values("Archive");

alter table item
add column item_status_id int(11) not null default 1,
add key (item_status_id),
add constraint fk_item_status foreign key (item_status_id) references item_status(id);