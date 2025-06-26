set foreign_key_checks = 0;

delete from tb_forma_pagamento_pedido;
delete from tb_forma_pagamento;
delete from tb_produto_pedido;
delete from tb_cliente_pedido;
delete from tb_endereco_pedido;
delete from tb_pedido;

alter table tb_forma_pagamento_pedido auto_increment = 1;
alter table tb_forma_pagamento auto_increment = 1;
alter table tb_produto_pedido auto_increment = 1;
alter table tb_cliente_pedido auto_increment = 1;
alter table tb_endereco_pedido auto_increment = 1;
alter table tb_pedido auto_increment = 1;

INSERT INTO `ecommerce-pedidos`.tb_forma_pagamento(ds_forma_pagamento, ativo) values ('Credito', 1);
INSERT INTO `ecommerce-pedidos`.tb_forma_pagamento(ds_forma_pagamento, ativo) values ('Debito', 1);
INSERT INTO `ecommerce-pedidos`.tb_forma_pagamento(ds_forma_pagamento, ativo) values ('Boleto', 1);
INSERT INTO `ecommerce-pedidos`.tb_forma_pagamento(ds_forma_pagamento, ativo) values ('Pix', 1);