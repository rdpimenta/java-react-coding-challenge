create table if not exists users(id bigint not null auto_increment, email varchar(255) not null, first_name varchar(255) not null, last_name varchar(255) not null, password varchar(255) not null, role varchar(255) not null, primary key(id));

insert into users (email, first_name, last_name, password, role) values ('jose.renato@email.com', 'Jose', 'Renato', 'password', "ADMINISTRATOR");