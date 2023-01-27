-- Table: public.tema_disciplina

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