create table pais(
    id varchar(2),
    nombre varchar(50),
    primary key (id)
);

create table persona (
    id bigint auto_increment not null,
    nombre varchar(50),
    apellido varchar(50),
    pais_id varchar(2) not null,
    primary key (id)
);

alter table persona add constraint persona_pais__fk foreign key (pais_id) references pais;

