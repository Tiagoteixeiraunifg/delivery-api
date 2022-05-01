create table if not exists usuario (
	
	id bigint not null auto_increment,
	nome varchar(100) not null,
	sobrenome varchar(100) not null,
	email varchar(100) not null,
	password varchar(250) not null,
	userperfil varchar(20) not null,
	primary key (id)


)