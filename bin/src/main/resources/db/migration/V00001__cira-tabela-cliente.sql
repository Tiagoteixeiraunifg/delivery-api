create table if not exists cliente(
	id bigint not null auto_increment,
    nome varchar(200) not null,
    email varchar(255) not null,
    telefone varchar(20) not null,
	primary key (id)
);