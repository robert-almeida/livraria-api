create table autores (
	id bigint not null auto_increment,
	nome varchar(100) not null,
	email varchar(100) not null,
	data_nascimento date not null,
	mini_curriculo varchar(100),
	primary key(id)
);
create table livros (
	id bigint not null auto_increment,
	titulo varchar(100) not null,
	data_lancamento date not null,
	numero_paginas int not null,
	autor_id bigint not null,
	primary key(id),
	foreign key (autor_id) references autores(id)
);