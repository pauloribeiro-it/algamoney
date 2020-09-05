CREATE TABLE lancamento(
	codigo bigint(20) primary key auto_increment,
	descricao varchar(50) not null,
	data_vencimento date not null,
	data_pagamento date,
	valor decimal(10,2) not null,
	observacao varchar(100),
	tipo varchar(20) not null,
	codigo_categoria bigint(20) not null,
	codigo_pessoa bigint(20) not null,
	foreign key (codigo_categoria) references categoria(codigo),
	foreign key(codigo_pessoa) references pessoa(codigo)
);

INSERT INTO lancamento (descricao, 			data_vencimento, data_pagamento, valor, 	observacao, 			  tipo, 		codigo_categoria, codigo_pessoa) values 
					   ('Salário mensal',  '2017-06-10', 	 '2017-06-10', 	 6500.00, 	'Distribuição de lucros', 'RECEITA', 	1, 				  1),
					   ('Bahamas', 		   '2017-02-10', 	 '2017-02-10', 	 100.32, 	null, 					  'DESPESA',  	2, 				  2),
					   ('Top Club', 	   '2017-06-10', 	 '2017-06-10', 	 120, 		null, 					  'RECEITA',  	3, 				  3),
					   ('CEMIG', 		   '2017-02-10', 	 '2017-02-10', 	 110.44,    'Geração',  			  'RECEITA',  	3, 				  4),
					   ('DMAE', 		   '2017-06-10', 	 '2017-06-10',	 200.30, 	null, 					  'DESPESA',  	3, 				  5),
					   ('Extra', 		   '2017-03-10', 	 '2017-03-10', 	 1010.32,	null, 					  'RECEITA',  	4, 				  6),
					   ('Bahamas', 		   '2017-06-10', 	 '2017-06-10',	 500, 		null, 					  'RECEITA',  	1, 				  7),
					   ('Top Club', 	   '2017-03-10', 	 '2017-03-10', 	 400.32,	null, 					  'DESPESA',  	4, 				  8),
					   ('Despachante', 	   '2017-06-10', 	 '2017-06-10',	 123.64, 	'Multas', 				  'DESPESA',  	3, 				  9),
					   ('Pneus', 		   '2017-04-10', 	 '2017-04-10', 	 665.33, 	null, 					  'RECEITA',  	5, 				  10),
					   ('Café', 		   '2017-06-10', 	 '2017-06-10',	 8.32, 		null, 					  'DESPESA',  	1, 				  5),
					   ('Eletrônicos', 	   '2017-04-10', 	 '2017-04-10', 	 2100.32, 	null, 					  'DESPESA',  	5, 				  4),
					   ('Instrumentos',    '2017-06-10', 	 '2017-06-10', 	 1040.32,	null, 					  'DESPESA',  	4, 				  3),
					   ('Café', 		   '2017-04-10', 	 '2017-04-10', 	 4.32, 		null, 					  'DESPESA',  	4, 				  2),
					   ('Lanche', 		   '2017-06-10', 	 '2017-06-10',	 10.20, 	null, 					  'DESPESA',  	4, 				  1);
