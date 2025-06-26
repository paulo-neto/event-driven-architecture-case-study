create table tb_pedido (
    id bigint not null,
    dt_criacao datetime not null,
    situacao int not null constraint st_pedido_chk check (situacao = 1 or situacao = 2 or situacao = 3 or situacao = 4),

    primary key(id)
) engine=InnoDB default charset=utf8;


create table tb_produto_pedido (
    produto_id bigint not null,
    pedido_id bigint not null,
    valor decimal(10,2) not null,
    quantidade smallint(8) not null,

    constraint pk_ped_prod primary key(pedido_id, produto_id),
    constraint pedido1 foreign key (pedido_id) references tb_pedido(id)
) engine=InnoDB default charset=utf8;

create table tb_cliente_pedido (
    pedido_id bigint not null,
    login_cliente varchar(100) not null,

    primary key(pedido_id),
    constraint pedido2 foreign key (pedido_id) references tb_pedido(id)
) engine=InnoDB default charset=utf8;

create table tb_endereco_pedido (
    cep smallint(8) not null,
    uf varchar(2) not null,
    municipio varchar(100) not null,
    logradouro varchar(100) not null,
    bairro varchar(30) not null,
    complemento varchar(30) not null,
    num_logradouro smallint(5) not null,
    pedido_id bigint not null,

    primary key(pedido_id),
    constraint pedido3 foreign key (pedido_id) references tb_pedido(id)
) engine=InnoDB default charset=utf8;

create table tb_forma_pagamento (

    id int not null auto_increment,
    ds_forma_pagamento varchar(15),
    ativo tinyint(1) not null,

    primary key (id)
) engine=InnoDB default charset=utf8;

create table tb_forma_pagamento_pedido (
    pedido_id bigint not null,
    forma_pagamento_id int not null,

    primary key(pedido_id),
    constraint pedido4 foreign key (pedido_id) references tb_pedido(id),
    constraint forma_pagamento foreign key (forma_pagamento_id) references tb_forma_pagamento(id)
) engine=InnoDB default charset=utf8;


