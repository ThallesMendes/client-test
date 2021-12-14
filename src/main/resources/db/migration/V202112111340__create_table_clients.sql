CREATE TABLE public.clients
(
    uuid uuid NOT NULL,
    name character varying(100) NOT NULL,
    age smallint NOT NULL,
    document character varying(40) NOT NULL,
    phone character varying(20) NOT NULL,
    email character varying(60),
    deleted boolean DEFAULT false,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone,
    deleted_at timestamp with time zone,
    PRIMARY KEY (uuid)
);

CREATE INDEX name_idx ON public.clients (name ASC NULLS LAST);
CREATE INDEX document_idx ON public.clients (document ASC NULLS LAST);