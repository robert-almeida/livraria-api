create table autores (
	id bigint not null auto_increment,
	nome varchar(100) not null,
	email varchar(100) not null,
	dataNascimento date not null,
	miniCurriculo varchar(100) not null,
	primary key(id)
);
create table livros (
	id bigint not null auto_increment,
	titulo varchar(100) not null,
	dataLancamento date not null,
	numeroPaginas int not null,
	autor_id bigint not null,
	primary key(id),
	foreign key (autor_id) references autores(id)
);