CREATE TABLE pessoa(
	codigo BIGINT PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	ativo BOOLEAN NOT NULL,
	logradouro VARCHAR(100),
	numero	   VARCHAR(5),
    complemento VARCHAR(100),
	bairro VARCHAR(100),
	cep VARCHAR(10),
	cidade VARCHAR(20),
	estado VARCHAR(20)	
);

INSERT INTO pessoa (nome, 		  		logradouro, 		 numero, complemento, bairro, 			   cep, 		cidade, 	   		estado, ativo) values
				   ('João Silva', 		'Rua do Abacaxi', 	 '10', 	 null, 		 'Brasil',  		   '38.400-12', 'Uberlândia', 		'MG',   true),
				   ('Maria Rita', 		'Rua do Sabiá', 	 '110',  'Apto 101', 'Colina', 			   '11.400-12', 'Ribeirão Preto', 	'SP', 	true),
				   ('Pedro Santos', 	'Rua da Bateria', 	 '23',	 null, 		 'Morumbi', 		   '54.212-12', 'Goiânia', 			'GO', 	true),
				   ('Ricardo Pereira',  'Rua do Motorista',  '123',  'Apto 302', 'Aparecida', 		   '38.400-12', 'Salvador', 		'BA', 	true),
				   ('Josué Mariano', 	'Av Rio Branco', 	 '321',	 null, 		 'Jardins', 		   '56.400-12', 'Natal', 			'RN', 	true),
				   ('Pedro Barbosa', 	'Av Brasil', 		 '100',  null, 		 'Tubalina', 		   '77.400-12', 'Porto Alegre', 	'RS', 	true),
				   ('Henrique Medeiros','Rua do Sapo', 		 '1120', 'Apto 201', 'Centro', 			   '12.400-12', 'Rio de Janeiro', 	'RJ', 	true),
				   ('Carlos Santana', 	'Rua da Manga', 	 '433',  null, 		 'Centro', 			   '31.400-12', 'Belo Horizonte', 	'MG', 	true),
				   ('Leonardo Oliveira','Rua do Músico', 	 '566',  null, 		 'Segismundo Pereira', '38.400-00', 'Uberlândia', 		'MG', 	true),
				   ('Isabela Martins',  'Rua da Terra', 	 '1233', 'Apto 10',  'Vigilato', 		   '99.400-12', 'Manaus', 			'AM', 	true);
