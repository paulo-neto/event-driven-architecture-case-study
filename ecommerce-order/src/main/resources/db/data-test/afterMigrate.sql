set foreign_key_checks = 0;

delete from tb_forma_pagamento_pedido;
delete from tb_forma_pagamento;
delete from tb_produto_pedido;
delete from tb_cliente_pedido;
delete from tb_endereco_pedido;
delete from tb_pedido;

set foreign_key_checks = 1;

alter table tb_forma_pagamento_pedido auto_increment = 1;
alter table tb_forma_pagamento auto_increment = 1;
alter table tb_produto_pedido auto_increment = 1;
alter table tb_cliente_pedido auto_increment = 1;
alter table tb_endereco_pedido auto_increment = 1;
alter table tb_pedido auto_increment = 1;

-- FORMA PAGAMENTO
INSERT INTO `ecommerce-pedidos`.tb_forma_pagamento(ds_forma_pagamento, ativo) values ('Credito', 1);
INSERT INTO `ecommerce-pedidos`.tb_forma_pagamento(ds_forma_pagamento, ativo) values ('Debito', 1);
INSERT INTO `ecommerce-pedidos`.tb_forma_pagamento(ds_forma_pagamento, ativo) values ('Boleto', 1);
INSERT INTO `ecommerce-pedidos`.tb_forma_pagamento(ds_forma_pagamento, ativo) values ('Pix', 1);

-- PEDIDO
INSERT INTO `ecommerce-pedidos`.tb_pedido(dt_criacao, situacao) values (now(), 1);
INSERT INTO `ecommerce-pedidos`.tb_pedido(dt_criacao, situacao) values (now(), 1);
INSERT INTO `ecommerce-pedidos`.tb_pedido(dt_criacao, situacao) values (now(), 1);

-- CLIENTE PEDIDO
INSERT INTO `ecommerce-pedidos`.tb_cliente_pedido(pedido_id, login_cliente) values (1, 'pauloa.neto@gmail.com');
INSERT INTO `ecommerce-pedidos`.tb_cliente_pedido(pedido_id, login_cliente) values (2, 'gerlinefs@gmail.com');
INSERT INTO `ecommerce-pedidos`.tb_cliente_pedido(pedido_id, login_cliente) values (3, 'pauloa.neto@gmail.com');

-- ENDERECO PEDIDO
INSERT INTO `ecommerce-pedidos`.tb_endereco_pedido(pedido_id, cep, uf, municipio, logradouro, bairro, complemento, num_logradouro) values(1, 58280000, 'PB', 'Mamanguape', 'Rua Coronel Luiz Inácio', 'Centro', 'Próximo a cagepa', 783);
INSERT INTO `ecommerce-pedidos`.tb_endereco_pedido(pedido_id, cep, uf, municipio, logradouro, bairro, complemento, num_logradouro) values(2, 71927540, 'DF', 'Brasília', 'Av Jacarandá', 'Águas Claras', 'Próximo ao hospital Brasília', 14);
INSERT INTO `ecommerce-pedidos`.tb_endereco_pedido(pedido_id, cep, uf, municipio, logradouro, bairro, complemento, num_logradouro) values(3, 71927540, 'DF', 'Brasília', 'Av Jacarandá', 'Águas Claras', 'Próximo ao hospital Brasília', 14);

-- FORMA PAGAMENTO PEDIDO
INSERT INTO `ecommerce-pedidos`.tb_forma_pagamento_pedido(pedido_id, forma_pagamento_id) values (1,1);
INSERT INTO `ecommerce-pedidos`.tb_forma_pagamento_pedido(pedido_id, forma_pagamento_id) values (2,1);
INSERT INTO `ecommerce-pedidos`.tb_forma_pagamento_pedido(pedido_id, forma_pagamento_id) values (3,4);

-- PRODUTO PEDIDO
INSERT INTO `ecommerce-pedidos`.tb_produto_pedido(pedido_id, produto_id, valor, quantidade) values (1,1,5000.00, 1);
INSERT INTO `ecommerce-pedidos`.tb_produto_pedido(pedido_id, produto_id, valor, quantidade) values (1,4,4500.00, 1);
INSERT INTO `ecommerce-pedidos`.tb_produto_pedido(pedido_id, produto_id, valor, quantidade) values (2,2,4000.00, 1);
INSERT INTO `ecommerce-pedidos`.tb_produto_pedido(pedido_id, produto_id, valor, quantidade) values (3,3,2500.5, 1);




