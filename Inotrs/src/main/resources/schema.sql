drop table categoria if exists;
drop table producto if exists;
drop table edificio if exists;
drop table persona if exists;
drop table incidencia if exists;
drop table usuario if exists;

drop sequence if exists hibernate_sequence;

create sequence hibernate_sequence start with 100 increment by 1;



create table categoria (
	id bigint not null, 
	nombre varchar(512), 
	primary key (id)
);

create table persona (
	login varchar(50) not null, 
	nombre varchar(512), 
	apellidos varchar(512), 
	telefono varchar(9), 
	despacho varchar(512), 
	puesto varchar(512), 
	nivel varchar(20),
	tipo varchar(512),
	edificio_id bigint,
	producto_id bigint,
	incidencias bigint,
	primary key (login)
);

create table incidencia (
	id varchar(50) not null, 
	descripcion varchar(512), 
	fecha_Inicio date,
	fecha_Fin date,
	prioridad varchar(100), 
	estado varchar(100), 
	tipo varchar(100), 
	login varchar(50), 
	login_Tecnico varchar(50),
	primary key (id)
);


create table edificio (
	id bigint not null, 
	nombre varchar(512), 
	ubicacion varchar(512), 
	primary key (id)
);

create table producto (
	id varchar(15) not null, 
	marca varchar(512), 
	modelo varchar(512),
	stock float not null, 
	cantidad float not null, 
	categoria_id bigint,
	edificio_id bigint,
	primary key (id)
);

alter table producto add constraint fk_producto_categoria foreign key (categoria_id) references categoria;
alter table producto add constraint fk_producto_edificio foreign key (edificio_id) references edificio;
alter table persona add constraint fk_persona_edificio foreign key (edificio_id) references edificio;
alter table persona add constraint fk_persona_producto foreign key (producto_id) references producto;
alter table incidencia add constraint fk_incidencia_persona foreign key (login) references persona;
alter table incidencia add constraint fk_incidencia_personaTecnico foreign key (login_Tecnico) references persona;

