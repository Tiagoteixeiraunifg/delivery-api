create table if not exists cliente(
	id bigint not null auto_increment,
	user_id bigint not null,
    nome varchar(100) not null,
    sobrenome varchar(100) not null,
    cpf varchar(16) not null,
    telefone varchar(20) not null,
    email varchar(255) not null,
    end_rua varchar(150) not null,
    end_complemento varchar(150) default null,
    end_cidade varchar(80) not null,
    end_numero varchar(20) not null,
    end_estado varchar(100) not null,
    end_cep varchar(10) not null,
	primary key (id)
);

alter table cliente add constraint fk_cliente_user
foreign key (user_id) references usuario (id)