-- Table: public.curso
begin;

-- DROP TABLE IF EXISTS public.curso;

CREATE TABLE IF NOT EXISTS public.curso
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    nome character varying(255) COLLATE pg_catalog."default",
    sigla character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT curso_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.curso
    OWNER to postgres;


INSERT INTO public.curso (nome, sigla) VALUES
('Analise e desenvolvimento de sistemas', 'ADS');

end;

-- Table: public.semestre
begin;

-- DROP TABLE IF EXISTS public.semestre;

CREATE TABLE IF NOT EXISTS public.semestre
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    nome character varying(255) COLLATE pg_catalog."default",
    curso_id bigint NOT NULL,
    CONSTRAINT semestre_pkey PRIMARY KEY (id),
    CONSTRAINT semestre_curso_id FOREIGN KEY (curso_id)
        REFERENCES public.curso (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.semestre
    OWNER to postgres;


INSERT INTO public.semestre (nome, curso_id) VALUES
('1 Semestre', 1),
('2 Semestre', 1),
('3 Semestre', 1),
('4 Semestre', 1),
('5 Semestre', 1),
('6 Semestre', 1);

end;

-- Table: public.disciplina
begin;

-- DROP TABLE IF EXISTS public.disciplina;

CREATE TABLE IF NOT EXISTS public.disciplina
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    nome character varying(255) COLLATE pg_catalog."default",
    sigla character varying(255) COLLATE pg_catalog."default",
    curso_id bigint NOT NULL,
    CONSTRAINT disciplina_pkey PRIMARY KEY (id),
    CONSTRAINT disciplina_curso_id FOREIGN KEY (curso_id)
        REFERENCES public.curso (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.disciplina
    OWNER to postgres;


INSERT INTO public.disciplina (nome, sigla, curso_id) VALUES
('ILM-001', 'Programação em Microinformática', 1),
('IAL-002', 'Algoritmos e Lógica de Programação', 1),
('IHW-100', 'Laboratório de Hardware', 1),
('IAC-001', 'Arquitetura e Organização de Computadores', 1),
('AAG-001', 'Administração Geral', 1),
('MMD-001', 'Matemática Discreta', 1),
('LIN-100', 'Inglês I', 1),
('IES-100', 'Engenharia de Software I', 1),
('ILP-010', 'Linguagem de Programação', 1),
('ISI-002', 'Sistemas de Informação', 1),
('CCG-001 ', 'Contabilidade', 1),
('MCA-002', 'Cálculo', 1),
('LPO-001', 'Comunicação e Expressão', 1);

end;

-- Table: public.periodo
begin;

-- DROP TABLE IF EXISTS public.periodo;

CREATE TABLE IF NOT EXISTS public.periodo
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    nome character varying(255) COLLATE pg_catalog."default",
    curso_id bigint NOT NULL,
    CONSTRAINT periodo_pkey PRIMARY KEY (id),
    CONSTRAINT periodo_curso_id FOREIGN KEY (curso_id)
        REFERENCES public.curso (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.periodo
    OWNER to postgres;


INSERT INTO public.periodo (nome, curso_id) VALUES
('Matutino', 1),
('Vespertino', 1),
('Noturno', 1);

end;

-- Table: public.usuario
begin;

-- DROP TABLE IF EXISTS public.usuario;

CREATE TABLE IF NOT EXISTS public.usuario
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    email character varying(255) COLLATE pg_catalog."default",
    login character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    semestre integer,
    curso_id bigint NOT NULL,
    CONSTRAINT usuario_pkey PRIMARY KEY (id),
    CONSTRAINT email_uni UNIQUE (email),
    CONSTRAINT login_uni UNIQUE (login),
    CONSTRAINT usuario_curso_id FOREIGN KEY (curso_id)
        REFERENCES public.curso (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.usuario
    OWNER to postgres;


--senha = usuario
INSERT INTO public.usuario(email, login, password, semestre, curso_id) VALUES
('usuario@email.com', 'usuario', '$2a$10$sQnC73si3NoOPV4iSJX0EOmhcVUvIgotrcd17D0xpbR4PfkJYCptu', 1, 1),
('usuario2@email.com', 'usuario2', '$2a$10$sQnC73si3NoOPV4iSJX0EOmhcVUvIgotrcd17D0xpbR4PfkJYCptu', 2, 1);

end;

-- Table: public.tema
begin;

-- DROP TABLE IF EXISTS public.tema;

CREATE TABLE IF NOT EXISTS public.tema
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    descricao character varying(255) COLLATE pg_catalog."default",
    excluido boolean NOT NULL,
    titulo character varying(255) COLLATE pg_catalog."default",
    curso_id bigint NOT NULL,
    usuario_id bigint NOT NULL,
    CONSTRAINT tema_pkey PRIMARY KEY (id),
    CONSTRAINT tema_usuario_id FOREIGN KEY (usuario_id)
        REFERENCES public.usuario (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT tema_curso_id FOREIGN KEY (curso_id)
        REFERENCES public.curso (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tema
    OWNER to postgres;


INSERT INTO public.tema (descricao, excluido, titulo, curso_id, usuario_id) VALUES
('Sistema para administrar uma padaria', false, 'Padaria', 1, 1),
('Sistema para administrar uma barbearia', false, 'Barbearia', 1, 2),
('Sistema para administrar um mercado', false, 'Mercado', 1, 1),
('Sistema para administrar uma papelaria', false, 'Papelaria', 1, 1);

end;

-- Table: public.tema_disciplina
begin;

-- DROP TABLE IF EXISTS public.tema_disciplina;

CREATE TABLE IF NOT EXISTS public.tema_disciplina
(
    tema_id bigint NOT NULL,
    disciplina_id bigint NOT NULL,
    CONSTRAINT tema_disciplina_id FOREIGN KEY (disciplina_id)
        REFERENCES public.disciplina (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT disciplina_tema_id FOREIGN KEY (tema_id)
        REFERENCES public.tema (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tema_disciplina
    OWNER to postgres;


INSERT INTO public.tema_disciplina (tema_id, disciplina_id) VALUES
(1, 1),
(1, 2),
(1, 4),
(2, 3),
(2, 5),
(3, 6),
(3, 10),
(4, 1),
(4, 7);

end;