create table usuarios(
	nombre varchar(50) not null,
	sal bytea not null,
    hash_contraseña bytea not null,
	primary key (nombre)
);

create table solicitar_amistad(
    solicitante varchar(50) not null,
	solicitado varchar(50) not null,
	estado varchar(20) not null,
	check (estado in ('pendiente', 'aceptado')),
    primary key(solicitante, solicitado),
	foreign key(solicitante) references usuarios(nombre) ON DELETE CASCADE ON UPDATE CASCADE,
	foreign key(solicitado) references usuarios(nombre) ON DELETE CASCADE ON UPDATE CASCADE
);