create table user_item(
id int(11) not null auto_increment,
user_id int(11) not null,
item_id int(11) not null,
insert_date timestamp NOT NULL default CURRENT_TIMESTAMP,
primary key (id),
key(user_id, item_id),
constraint fk_user_item1 foreign key (user_id) references account_usr(id),
constraint fk_user_item2 foreign key (item_id) references item(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;