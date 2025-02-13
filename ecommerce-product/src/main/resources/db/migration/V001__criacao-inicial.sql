create table tb_categoria (
	id int not null auto_increment,
	nome varchar(20) not null,
	dt_criacao datetime not null,
	ativo tinyint(1) not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

create table tb_produto (
  	id bigint not null auto_increment,
  	nome varchar(60) not null,
	descricao text not null,
	preco decimal(10,2) not null,
	ativo tinyint(1) not null,
	dt_criacao datetime not null,
	dt_atualizacao datetime,
	categoria_id int,
	primary key (id),
	constraint categoria_produto foreign key (categoria_id) references tb_categoria(id)
) engine=InnoDB default charset=utf8;