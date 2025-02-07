create table pauta (
                         id bigserial not null,
                         titulo varchar(200) not null,
                         descricao varchar(1000) not null,
                         tempo integer not null default 1,
                         constraint pk_pauta primary key(id)
);

create table pauta_sessao (
                        id bigserial not null,
                        pauta_id bigint not null,
                        inicio_votacao timestamp without time zone default current_timestamp not null,
                        fim_votacao timestamp without time zone default current_timestamp not null,
                        constraint pk_pauta_sessao primary key(id),
                        constraint fk_pauta_sessao_pauta foreign key(pauta_id) references pauta(id)

);

create table voto_sessao (
                        id bigserial not null,
                        sessao_id bigint not null,
                        ref_associado varchar(10) not null,
                        voto boolean not null,
                        constraint pk_voto_sessao primary key(id),
                        constraint fk_voto_sessao foreign key(sessao_id) references pauta_sessao(id),
                        constraint uk_sessao_ref_associado unique (ref_associado,sessao_id)
);

create index fk_pauta_sessao_pauta on pauta_sessao(pauta_id);
create index fk_voto_sessao on voto_sessao(sessao_id);

