create table item_type(
id int(11) not null auto_increment,
name varchar(255) not null,
primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into item_type(name) values("Positive");
insert into item_type(name) values("Negative");

alter table item
add column item_type_id int(11) not null,
add key (item_type_id),
add constraint fk_item_type foreign key (item_type_id) references item_type(id);