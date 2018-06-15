-- Table: public."Participantes"

-- DROP TABLE public."Participantes";

CREATE TABLE public."Participantes"
(
    "Nombre" character varying(50) COLLATE pg_catalog."default" NOT NULL,
    "Votos" bigint NOT NULL,
    CONSTRAINT "Participantes_pkey" PRIMARY KEY ("Nombre")
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."Participantes"
    OWNER to postgres;



-- Table: public."Usuarios"

-- DROP TABLE public."Usuarios";

CREATE TABLE public."Usuarios"
(
    "Matricula" character varying(10) COLLATE pg_catalog."default" NOT NULL,
    "Nombre" character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "Usuarios_pkey" PRIMARY KEY ("Matricula")
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."Usuarios"
    OWNER to postgres;


-- Table: public."Votos_realizados"

-- DROP TABLE public."Votos_realizados";

CREATE TABLE public."Votos_realizados"
(
    "Matricula" character varying(10) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "Votos_realizados_pkey" PRIMARY KEY ("Matricula")
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."Votos_realizados"
    OWNER to postgres;