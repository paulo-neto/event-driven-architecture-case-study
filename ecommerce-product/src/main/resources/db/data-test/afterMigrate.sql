set foreign_key_checks = 0;

delete from tb_produto;
delete from tb_categoria;

set foreign_key_checks = 1;

alter table tb_categoria auto_increment = 1;
alter table tb_produto auto_increment = 1;

INSERT INTO `ecommerce-produtos`.tb_categoria (nome, dt_criacao, ativo) VALUES('Eletrônicos', now(), 1);
INSERT INTO `ecommerce-produtos`.tb_categoria (nome, dt_criacao, ativo) VALUES('Eletrodomesticos', now(), 1);
INSERT INTO `ecommerce-produtos`.tb_categoria (nome, dt_criacao, ativo) VALUES('Celulares', now(), 1);
INSERT INTO `ecommerce-produtos`.tb_categoria (nome, dt_criacao, ativo) VALUES('Brinquedos', now(), 1);
INSERT INTO `ecommerce-produtos`.tb_categoria (nome, dt_criacao, ativo) VALUES('Livros', now(), 1);
INSERT INTO `ecommerce-produtos`.tb_categoria (nome, dt_criacao, ativo) VALUES('Móveis', now(), 1);

INSERT INTO `ecommerce-produtos`.tb_produto (nome, descricao, preco, ativo, dt_criacao, categoria_id) VALUES('TV Samsung 50 polegadas', 'Smart TV 50 polegadas', 2500.5, 1, now(), 1);
INSERT INTO `ecommerce-produtos`.tb_produto (nome, descricao, preco, ativo, dt_criacao, categoria_id) VALUES('TV LG 50 polegadas', 'Smart TV 50 polegadas widescreem', 2500.5, 1, now(), 1);
INSERT INTO `ecommerce-produtos`.tb_produto (nome, descricao, preco, ativo, dt_criacao, categoria_id) VALUES('TV Philips 30 polegadas', 'Smart TV 30 polegadas', 2500.5, 1, now(), 1);
INSERT INTO `ecommerce-produtos`.tb_produto (nome, descricao, preco, ativo, dt_criacao, categoria_id) VALUES('TV Philco 20 polegadas', 'Smart TV 20 polegadas', 2500.5, 1, now(), 1);
INSERT INTO `ecommerce-produtos`.tb_produto (nome, descricao, preco, ativo, dt_criacao, categoria_id) VALUES('TV AOC 70 polegadas', 'Smart TV 70 polegadas', 2500.5, 1, now(), 1);