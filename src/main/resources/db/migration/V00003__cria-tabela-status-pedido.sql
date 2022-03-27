create table if not exists statuspedido(
	id bigint not null auto_increment,
    entrega_id bigint not null,
    descricao text not null,
    data_registro datetime not null,
    primary key (id)
    

);

alter table statuspedido add constraint fk_status_predido_entrega
foreign key (entrega_id) references entrega (id)