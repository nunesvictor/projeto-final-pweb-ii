--
-- PostgreSQL database dump
--

-- Dumped from database version 14.2
-- Dumped by pg_dump version 14.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: categoria; Type: TABLE; Schema: public; Owner: nunesvictor
--

CREATE TABLE public.categoria (
    id bigint NOT NULL,
    active boolean DEFAULT true NOT NULL,
    created_at timestamp without time zone NOT NULL,
    descricao character varying(255) NOT NULL,
    updated_at timestamp without time zone NOT NULL
);


ALTER TABLE public.categoria OWNER TO nunesvictor;

--
-- Name: categoria_id_seq; Type: SEQUENCE; Schema: public; Owner: nunesvictor
--

CREATE SEQUENCE public.categoria_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.categoria_id_seq OWNER TO nunesvictor;

--
-- Name: categoria_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nunesvictor
--

ALTER SEQUENCE public.categoria_id_seq OWNED BY public.categoria.id;


--
-- Name: cliente_enderecos; Type: TABLE; Schema: public; Owner: nunesvictor
--

CREATE TABLE public.cliente_enderecos (
    cliente_id bigint NOT NULL,
    enderecos_id bigint NOT NULL
);


ALTER TABLE public.cliente_enderecos OWNER TO nunesvictor;

--
-- Name: cliente_pessoa_fisica; Type: TABLE; Schema: public; Owner: nunesvictor
--

CREATE TABLE public.cliente_pessoa_fisica (
    id bigint NOT NULL,
    active boolean DEFAULT true NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    usuario_id bigint,
    cpf character varying(11),
    email character varying(255),
    nome character varying(255),
    telefone character varying(11)
);


ALTER TABLE public.cliente_pessoa_fisica OWNER TO nunesvictor;

--
-- Name: endereco; Type: TABLE; Schema: public; Owner: nunesvictor
--

CREATE TABLE public.endereco (
    id bigint NOT NULL,
    apelido character varying(255),
    bairro character varying(255) NOT NULL,
    cep character varying(8) NOT NULL,
    complemento character varying(255),
    localidade character varying(255) NOT NULL,
    logradouro character varying(255) NOT NULL,
    numero character varying(255) NOT NULL,
    uf character varying(2) NOT NULL
);


ALTER TABLE public.endereco OWNER TO nunesvictor;

--
-- Name: endereco_id_seq; Type: SEQUENCE; Schema: public; Owner: nunesvictor
--

CREATE SEQUENCE public.endereco_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.endereco_id_seq OWNER TO nunesvictor;

--
-- Name: endereco_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nunesvictor
--

ALTER SEQUENCE public.endereco_id_seq OWNED BY public.endereco.id;


--
-- Name: forma_pagamento; Type: TABLE; Schema: public; Owner: nunesvictor
--

CREATE TABLE public.forma_pagamento (
    id bigint NOT NULL,
    active boolean DEFAULT true NOT NULL,
    created_at timestamp without time zone NOT NULL,
    descricao character varying(255) NOT NULL,
    parcelamento_maximo integer DEFAULT 1 NOT NULL,
    percentual_descontoavista numeric(10,2) DEFAULT 0 NOT NULL,
    percentual_juros_parcelado numeric(10,2) DEFAULT 0 NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    imagem character varying(255),
    slug character varying(255),
    CONSTRAINT forma_pagamento_parcelamento_maximo_check CHECK ((parcelamento_maximo >= 1))
);


ALTER TABLE public.forma_pagamento OWNER TO nunesvictor;

--
-- Name: forma_pagamento_id_seq; Type: SEQUENCE; Schema: public; Owner: nunesvictor
--

CREATE SEQUENCE public.forma_pagamento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.forma_pagamento_id_seq OWNER TO nunesvictor;

--
-- Name: forma_pagamento_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nunesvictor
--

ALTER SEQUENCE public.forma_pagamento_id_seq OWNED BY public.forma_pagamento.id;


--
-- Name: item_venda; Type: TABLE; Schema: public; Owner: nunesvictor
--

CREATE TABLE public.item_venda (
    id bigint NOT NULL,
    quantidade integer DEFAULT 1 NOT NULL,
    produto_id bigint,
    venda_id bigint,
    CONSTRAINT item_venda_quantidade_check CHECK ((quantidade >= 1))
);


ALTER TABLE public.item_venda OWNER TO nunesvictor;

--
-- Name: item_venda_id_seq; Type: SEQUENCE; Schema: public; Owner: nunesvictor
--

CREATE SEQUENCE public.item_venda_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.item_venda_id_seq OWNER TO nunesvictor;

--
-- Name: item_venda_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nunesvictor
--

ALTER SEQUENCE public.item_venda_id_seq OWNED BY public.item_venda.id;


--
-- Name: marca; Type: TABLE; Schema: public; Owner: nunesvictor
--

CREATE TABLE public.marca (
    id bigint NOT NULL,
    active boolean DEFAULT true NOT NULL,
    created_at timestamp without time zone NOT NULL,
    nome character varying(255) NOT NULL,
    updated_at timestamp without time zone NOT NULL
);


ALTER TABLE public.marca OWNER TO nunesvictor;

--
-- Name: marca_id_seq; Type: SEQUENCE; Schema: public; Owner: nunesvictor
--

CREATE SEQUENCE public.marca_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.marca_id_seq OWNER TO nunesvictor;

--
-- Name: marca_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nunesvictor
--

ALTER SEQUENCE public.marca_id_seq OWNED BY public.marca.id;


--
-- Name: pagamento; Type: TABLE; Schema: public; Owner: nunesvictor
--

CREATE TABLE public.pagamento (
    id bigint NOT NULL,
    active boolean DEFAULT true NOT NULL,
    created_at timestamp without time zone NOT NULL,
    desconto_total numeric(10,2) DEFAULT 0 NOT NULL,
    juros_total numeric(10,2) DEFAULT 0 NOT NULL,
    numero_parcelas integer DEFAULT 1 NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    valor_parcela double precision NOT NULL,
    valor_total double precision NOT NULL,
    forma_pagamento_id bigint,
    venda_id bigint,
    CONSTRAINT pagamento_numero_parcelas_check CHECK ((numero_parcelas >= 1))
);


ALTER TABLE public.pagamento OWNER TO nunesvictor;

--
-- Name: pagamento_id_seq; Type: SEQUENCE; Schema: public; Owner: nunesvictor
--

CREATE SEQUENCE public.pagamento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pagamento_id_seq OWNER TO nunesvictor;

--
-- Name: pagamento_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nunesvictor
--

ALTER SEQUENCE public.pagamento_id_seq OWNED BY public.pagamento.id;


--
-- Name: preco; Type: TABLE; Schema: public; Owner: nunesvictor
--

CREATE TABLE public.preco (
    id bigint NOT NULL,
    active boolean DEFAULT true NOT NULL,
    created_at timestamp without time zone NOT NULL,
    promocao boolean DEFAULT false NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    valor double precision,
    produto_id bigint
);


ALTER TABLE public.preco OWNER TO nunesvictor;

--
-- Name: preco_id_seq; Type: SEQUENCE; Schema: public; Owner: nunesvictor
--

CREATE SEQUENCE public.preco_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.preco_id_seq OWNER TO nunesvictor;

--
-- Name: preco_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nunesvictor
--

ALTER SEQUENCE public.preco_id_seq OWNED BY public.preco.id;


--
-- Name: produto; Type: TABLE; Schema: public; Owner: nunesvictor
--

CREATE TABLE public.produto (
    id bigint NOT NULL,
    active boolean DEFAULT true NOT NULL,
    created_at timestamp without time zone NOT NULL,
    descricao character varying(255) NOT NULL,
    imagem character varying(255) NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    marca_id bigint
);


ALTER TABLE public.produto OWNER TO nunesvictor;

--
-- Name: produto_categorias; Type: TABLE; Schema: public; Owner: nunesvictor
--

CREATE TABLE public.produto_categorias (
    produto_id bigint NOT NULL,
    categorias_id bigint NOT NULL
);


ALTER TABLE public.produto_categorias OWNER TO nunesvictor;

--
-- Name: produto_id_seq; Type: SEQUENCE; Schema: public; Owner: nunesvictor
--

CREATE SEQUENCE public.produto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.produto_id_seq OWNER TO nunesvictor;

--
-- Name: produto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nunesvictor
--

ALTER SEQUENCE public.produto_id_seq OWNED BY public.produto.id;


--
-- Name: role; Type: TABLE; Schema: public; Owner: nunesvictor
--

CREATE TABLE public.role (
    id bigint NOT NULL,
    authority character varying(255)
);


ALTER TABLE public.role OWNER TO nunesvictor;

--
-- Name: role_id_seq; Type: SEQUENCE; Schema: public; Owner: nunesvictor
--

CREATE SEQUENCE public.role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.role_id_seq OWNER TO nunesvictor;

--
-- Name: role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nunesvictor
--

ALTER SEQUENCE public.role_id_seq OWNED BY public.role.id;


--
-- Name: usuario; Type: TABLE; Schema: public; Owner: nunesvictor
--

CREATE TABLE public.usuario (
    id bigint NOT NULL,
    password character varying(255) NOT NULL,
    username character varying(255) NOT NULL
);


ALTER TABLE public.usuario OWNER TO nunesvictor;

--
-- Name: usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: nunesvictor
--

CREATE SEQUENCE public.usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuario_id_seq OWNER TO nunesvictor;

--
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nunesvictor
--

ALTER SEQUENCE public.usuario_id_seq OWNED BY public.usuario.id;


--
-- Name: usuarios_roles; Type: TABLE; Schema: public; Owner: nunesvictor
--

CREATE TABLE public.usuarios_roles (
    id_usuario bigint NOT NULL,
    id_role bigint NOT NULL
);


ALTER TABLE public.usuarios_roles OWNER TO nunesvictor;

--
-- Name: venda; Type: TABLE; Schema: public; Owner: nunesvictor
--

CREATE TABLE public.venda (
    id bigint NOT NULL,
    active boolean DEFAULT true NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    cliente_id bigint,
    endereco_id bigint,
    pagamento_id bigint
);


ALTER TABLE public.venda OWNER TO nunesvictor;

--
-- Name: venda_id_seq; Type: SEQUENCE; Schema: public; Owner: nunesvictor
--

CREATE SEQUENCE public.venda_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.venda_id_seq OWNER TO nunesvictor;

--
-- Name: venda_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nunesvictor
--

ALTER SEQUENCE public.venda_id_seq OWNED BY public.venda.id;


--
-- Name: categoria id; Type: DEFAULT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.categoria ALTER COLUMN id SET DEFAULT nextval('public.categoria_id_seq'::regclass);


--
-- Name: endereco id; Type: DEFAULT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.endereco ALTER COLUMN id SET DEFAULT nextval('public.endereco_id_seq'::regclass);


--
-- Name: forma_pagamento id; Type: DEFAULT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.forma_pagamento ALTER COLUMN id SET DEFAULT nextval('public.forma_pagamento_id_seq'::regclass);


--
-- Name: item_venda id; Type: DEFAULT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.item_venda ALTER COLUMN id SET DEFAULT nextval('public.item_venda_id_seq'::regclass);


--
-- Name: marca id; Type: DEFAULT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.marca ALTER COLUMN id SET DEFAULT nextval('public.marca_id_seq'::regclass);


--
-- Name: pagamento id; Type: DEFAULT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.pagamento ALTER COLUMN id SET DEFAULT nextval('public.pagamento_id_seq'::regclass);


--
-- Name: preco id; Type: DEFAULT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.preco ALTER COLUMN id SET DEFAULT nextval('public.preco_id_seq'::regclass);


--
-- Name: produto id; Type: DEFAULT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.produto ALTER COLUMN id SET DEFAULT nextval('public.produto_id_seq'::regclass);


--
-- Name: role id; Type: DEFAULT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.role ALTER COLUMN id SET DEFAULT nextval('public.role_id_seq'::regclass);


--
-- Name: usuario id; Type: DEFAULT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.usuario ALTER COLUMN id SET DEFAULT nextval('public.usuario_id_seq'::regclass);


--
-- Name: venda id; Type: DEFAULT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.venda ALTER COLUMN id SET DEFAULT nextval('public.venda_id_seq'::regclass);


--
-- Data for Name: categoria; Type: TABLE DATA; Schema: public; Owner: nunesvictor
--

COPY public.categoria (id, active, created_at, descricao, updated_at) FROM stdin;
1	t	2022-06-23 16:36:15.182443	Arroz	2022-06-23 16:36:15.182526
2	t	2022-06-23 16:36:21.524055	Feijão	2022-06-23 16:36:21.524088
3	t	2022-06-23 16:36:26.259551	Cereais	2022-06-23 16:36:26.259615
\.


--
-- Data for Name: cliente_enderecos; Type: TABLE DATA; Schema: public; Owner: nunesvictor
--

COPY public.cliente_enderecos (cliente_id, enderecos_id) FROM stdin;
2	2
1	1
2	3
\.


--
-- Data for Name: cliente_pessoa_fisica; Type: TABLE DATA; Schema: public; Owner: nunesvictor
--

COPY public.cliente_pessoa_fisica (id, active, created_at, updated_at, usuario_id, cpf, email, nome, telefone) FROM stdin;
1	t	2022-06-08 23:33:35.530385	2022-06-08 23:33:35.530421	1	00000000000	admin@mail.com	Admin	00000000000
2	t	2022-06-08 23:58:42.376955	2022-06-08 23:58:42.376989	2	01010101010	mail@mail.com	Victor Guimarães Nunes	01010101010
\.


--
-- Data for Name: endereco; Type: TABLE DATA; Schema: public; Owner: nunesvictor
--

COPY public.endereco (id, apelido, bairro, cep, complemento, localidade, logradouro, numero, uf) FROM stdin;
1	Caixa Postal 	Plano Diretor Sul	77019880		Palmas	Quadra ALC-SO 141 - Mirante do Lago Alameda Babaçu	20	TO
2	Casa	Plano Diretor Sul	77020586		Palmas	ARSE 24 Alameda 11	32	TO
3	Trabalho	Plano Diretor Sul	77021654	Defensoria Pública do Estado do Tocantins	Palmas	Quadra AA SE 50 Avenida Joaquim Teotônio Segurado	S/N	TO
\.


--
-- Data for Name: forma_pagamento; Type: TABLE DATA; Schema: public; Owner: nunesvictor
--

COPY public.forma_pagamento (id, active, created_at, descricao, parcelamento_maximo, percentual_descontoavista, percentual_juros_parcelado, updated_at, imagem, slug) FROM stdin;
2	t	2022-06-08 23:26:25	Boleto Bancário	1	0.05	0.00	2022-06-08 23:26:47	/images/barcode.svg	boleto
1	t	2022-06-08 23:25:58	PIX	1	0.00	0.00	2022-06-08 23:26:04	/images/pix.svg	pix
3	t	2022-06-08 23:27:52	Cartão de Crédito	12	0.00	0.08	2022-06-08 23:27:55	/images/credit-card.svg	cartao-credito
\.


--
-- Data for Name: item_venda; Type: TABLE DATA; Schema: public; Owner: nunesvictor
--

COPY public.item_venda (id, quantidade, produto_id, venda_id) FROM stdin;
\.


--
-- Data for Name: marca; Type: TABLE DATA; Schema: public; Owner: nunesvictor
--

COPY public.marca (id, active, created_at, nome, updated_at) FROM stdin;
1	t	2022-06-23 16:35:56.875756	Camil	2022-06-23 16:35:56.875857
2	t	2022-06-23 16:35:59.974538	Cristal	2022-06-23 16:35:59.974585
3	t	2022-06-23 16:36:05.158685	Kicaldo	2022-06-23 16:36:05.158728
4	t	2022-06-23 16:36:09.018213	Qualitá	2022-06-23 16:36:09.018253
\.


--
-- Data for Name: pagamento; Type: TABLE DATA; Schema: public; Owner: nunesvictor
--

COPY public.pagamento (id, active, created_at, desconto_total, juros_total, numero_parcelas, updated_at, valor_parcela, valor_total, forma_pagamento_id, venda_id) FROM stdin;
\.


--
-- Data for Name: preco; Type: TABLE DATA; Schema: public; Owner: nunesvictor
--

COPY public.preco (id, active, created_at, promocao, updated_at, valor, produto_id) FROM stdin;
1	t	2022-06-23 16:39:27.540687	f	2022-06-23 16:39:27.540758	19.9	1
2	t	2022-06-23 16:39:38.324963	f	2022-06-23 16:39:38.325013	20.9	2
3	t	2022-06-23 16:39:52.647638	f	2022-06-23 16:39:52.64767	17.9	3
4	t	2022-06-23 16:40:00.22673	f	2022-06-23 16:40:00.226763	18.9	4
5	t	2022-06-23 16:40:09.46172	f	2022-06-23 16:40:09.461751	16.9	5
6	t	2022-06-23 16:40:20.630056	f	2022-06-23 16:40:20.630192	17.9	6
\.


--
-- Data for Name: produto; Type: TABLE DATA; Schema: public; Owner: nunesvictor
--

COPY public.produto (id, active, created_at, descricao, imagem, updated_at, marca_id) FROM stdin;
1	t	2022-06-23 16:36:53.444721	Arroz Branco Tipo 1	arroz-branco-tipo1-camil.webp	2022-06-23 16:36:53.444758	1
2	t	2022-06-23 16:37:08.117277	Arroz Branco Tipo 1	arroz-branco-tipo1-cristal.jpeg	2022-06-23 16:37:08.117315	2
3	t	2022-06-23 16:37:34.365895	Feijão Carioca	feijao-carioca-camil.png	2022-06-23 16:37:34.365946	1
4	t	2022-06-23 16:37:44.606797	Feijão Carioca	feijao-carioca-cristal.jpeg	2022-06-23 16:37:44.606848	2
5	t	2022-06-23 16:37:57.13042	Feijão Carioca	feijao-carioca-kicaldo.webp	2022-06-23 16:37:57.130458	3
6	t	2022-06-23 16:38:07.946691	Feijão Carioca	feijao-carioca-qualita.png	2022-06-23 16:38:07.946728	4
7	t	2022-06-23 16:39:08.463338	Feijão Preto	feijao-preto-camil.webp	2022-06-23 16:39:08.463379	1
\.


--
-- Data for Name: produto_categorias; Type: TABLE DATA; Schema: public; Owner: nunesvictor
--

COPY public.produto_categorias (produto_id, categorias_id) FROM stdin;
1	1
1	3
2	1
2	3
3	3
3	2
4	3
4	2
5	3
5	2
6	3
6	2
7	3
7	2
\.


--
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: nunesvictor
--

COPY public.role (id, authority) FROM stdin;
1	ROLE_ADMIN
2	ROLE_USER
\.


--
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: nunesvictor
--

COPY public.usuario (id, password, username) FROM stdin;
1	$2a$10$Mj5LqcC/sNmwM7K.DsuAcOvWrHUNAok1ZzV70JRSvGqKZQK4iKUJm	admin
2	$2a$10$.iLZgB1IRuax2s65S1532e5tYn84.gE5Im4xQ5f1/60ARw9vCQsx6	user
\.


--
-- Data for Name: usuarios_roles; Type: TABLE DATA; Schema: public; Owner: nunesvictor
--

COPY public.usuarios_roles (id_usuario, id_role) FROM stdin;
1	1
2	2
\.


--
-- Data for Name: venda; Type: TABLE DATA; Schema: public; Owner: nunesvictor
--

COPY public.venda (id, active, created_at, updated_at, cliente_id, endereco_id, pagamento_id) FROM stdin;
\.


--
-- Name: categoria_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nunesvictor
--

SELECT pg_catalog.setval('public.categoria_id_seq', 3, true);


--
-- Name: endereco_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nunesvictor
--

SELECT pg_catalog.setval('public.endereco_id_seq', 3, true);


--
-- Name: forma_pagamento_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nunesvictor
--

SELECT pg_catalog.setval('public.forma_pagamento_id_seq', 3, true);


--
-- Name: item_venda_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nunesvictor
--

SELECT pg_catalog.setval('public.item_venda_id_seq', 1, false);


--
-- Name: marca_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nunesvictor
--

SELECT pg_catalog.setval('public.marca_id_seq', 4, true);


--
-- Name: pagamento_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nunesvictor
--

SELECT pg_catalog.setval('public.pagamento_id_seq', 1, false);


--
-- Name: preco_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nunesvictor
--

SELECT pg_catalog.setval('public.preco_id_seq', 6, true);


--
-- Name: produto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nunesvictor
--

SELECT pg_catalog.setval('public.produto_id_seq', 7, true);


--
-- Name: role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nunesvictor
--

SELECT pg_catalog.setval('public.role_id_seq', 2, true);


--
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nunesvictor
--

SELECT pg_catalog.setval('public.usuario_id_seq', 2, true);


--
-- Name: venda_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nunesvictor
--

SELECT pg_catalog.setval('public.venda_id_seq', 1, false);


--
-- Name: categoria categoria_pkey; Type: CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (id);


--
-- Name: cliente_pessoa_fisica cliente_pessoa_fisica_pkey; Type: CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.cliente_pessoa_fisica
    ADD CONSTRAINT cliente_pessoa_fisica_pkey PRIMARY KEY (id);


--
-- Name: endereco endereco_pkey; Type: CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.endereco
    ADD CONSTRAINT endereco_pkey PRIMARY KEY (id);


--
-- Name: forma_pagamento forma_pagamento_pkey; Type: CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.forma_pagamento
    ADD CONSTRAINT forma_pagamento_pkey PRIMARY KEY (id);


--
-- Name: item_venda item_venda_pkey; Type: CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.item_venda
    ADD CONSTRAINT item_venda_pkey PRIMARY KEY (id);


--
-- Name: marca marca_pkey; Type: CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.marca
    ADD CONSTRAINT marca_pkey PRIMARY KEY (id);


--
-- Name: pagamento pagamento_pkey; Type: CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.pagamento
    ADD CONSTRAINT pagamento_pkey PRIMARY KEY (id);


--
-- Name: preco preco_pkey; Type: CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.preco
    ADD CONSTRAINT preco_pkey PRIMARY KEY (id);


--
-- Name: produto produto_pkey; Type: CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT produto_pkey PRIMARY KEY (id);


--
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- Name: categoria uk_2b57ojcj5l59a6a2v50u4m32v; Type: CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT uk_2b57ojcj5l59a6a2v50u4m32v UNIQUE (descricao);


--
-- Name: cliente_pessoa_fisica uk_4hh4bd81x5oxfv7aemms5smgh; Type: CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.cliente_pessoa_fisica
    ADD CONSTRAINT uk_4hh4bd81x5oxfv7aemms5smgh UNIQUE (telefone);


--
-- Name: cliente_pessoa_fisica uk_5cqwtr8d71r6w6krhgm15qkcv; Type: CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.cliente_pessoa_fisica
    ADD CONSTRAINT uk_5cqwtr8d71r6w6krhgm15qkcv UNIQUE (email);


--
-- Name: usuario uk_863n1y3x0jalatoir4325ehal; Type: CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT uk_863n1y3x0jalatoir4325ehal UNIQUE (username);


--
-- Name: forma_pagamento uk_dy0ouu86x6dw1jmls9kjtfkd1; Type: CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.forma_pagamento
    ADD CONSTRAINT uk_dy0ouu86x6dw1jmls9kjtfkd1 UNIQUE (slug);


--
-- Name: cliente_enderecos uk_esopwo551jm1jhv15aghmt4i6; Type: CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.cliente_enderecos
    ADD CONSTRAINT uk_esopwo551jm1jhv15aghmt4i6 UNIQUE (enderecos_id);


--
-- Name: marca uk_ix7qqllivg82mpp45cgolja3; Type: CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.marca
    ADD CONSTRAINT uk_ix7qqllivg82mpp45cgolja3 UNIQUE (nome);


--
-- Name: cliente_pessoa_fisica uk_mrgrx12k39s526fgi1pvc6uof; Type: CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.cliente_pessoa_fisica
    ADD CONSTRAINT uk_mrgrx12k39s526fgi1pvc6uof UNIQUE (cpf);


--
-- Name: forma_pagamento uk_nuwy6jcgaurd2w22yepw4kh3w; Type: CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.forma_pagamento
    ADD CONSTRAINT uk_nuwy6jcgaurd2w22yepw4kh3w UNIQUE (descricao);


--
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- Name: venda venda_pkey; Type: CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.venda
    ADD CONSTRAINT venda_pkey PRIMARY KEY (id);


--
-- Name: venda fk2hsoornxinfvsmac46lgw6dfj; Type: FK CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.venda
    ADD CONSTRAINT fk2hsoornxinfvsmac46lgw6dfj FOREIGN KEY (endereco_id) REFERENCES public.endereco(id);


--
-- Name: item_venda fk7wkinkvno0wlhv821hhu34y04; Type: FK CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.item_venda
    ADD CONSTRAINT fk7wkinkvno0wlhv821hhu34y04 FOREIGN KEY (produto_id) REFERENCES public.produto(id);


--
-- Name: preco fk8604la58jipk4scgarkn32hd1; Type: FK CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.preco
    ADD CONSTRAINT fk8604la58jipk4scgarkn32hd1 FOREIGN KEY (produto_id) REFERENCES public.produto(id);


--
-- Name: cliente_pessoa_fisica fk_flhaejxrnu9rfk9w4eg2exlt3; Type: FK CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.cliente_pessoa_fisica
    ADD CONSTRAINT fk_flhaejxrnu9rfk9w4eg2exlt3 FOREIGN KEY (usuario_id) REFERENCES public.usuario(id);


--
-- Name: pagamento fkbf6syaph4vriqxw6jtfubmwc3; Type: FK CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.pagamento
    ADD CONSTRAINT fkbf6syaph4vriqxw6jtfubmwc3 FOREIGN KEY (venda_id) REFERENCES public.venda(id);


--
-- Name: cliente_enderecos fkc3dtkjs5dppgk62n356gtfm53; Type: FK CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.cliente_enderecos
    ADD CONSTRAINT fkc3dtkjs5dppgk62n356gtfm53 FOREIGN KEY (enderecos_id) REFERENCES public.endereco(id);


--
-- Name: usuarios_roles fkdmgx7atyhfl7fku8egpmx65cw; Type: FK CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.usuarios_roles
    ADD CONSTRAINT fkdmgx7atyhfl7fku8egpmx65cw FOREIGN KEY (id_usuario) REFERENCES public.usuario(id);


--
-- Name: produto_categorias fkjc9xq6yjeq6w2c500bsjj8pip; Type: FK CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.produto_categorias
    ADD CONSTRAINT fkjc9xq6yjeq6w2c500bsjj8pip FOREIGN KEY (produto_id) REFERENCES public.produto(id);


--
-- Name: produto fkjwfodivt9e04sad1s7u0ya4jq; Type: FK CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT fkjwfodivt9e04sad1s7u0ya4jq FOREIGN KEY (marca_id) REFERENCES public.marca(id);


--
-- Name: usuarios_roles fkk8v3c4gmb30w7srb9xp9d5pap; Type: FK CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.usuarios_roles
    ADD CONSTRAINT fkk8v3c4gmb30w7srb9xp9d5pap FOREIGN KEY (id_role) REFERENCES public.role(id);


--
-- Name: item_venda fkkiky88fkai72328rhw3r3yebx; Type: FK CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.item_venda
    ADD CONSTRAINT fkkiky88fkai72328rhw3r3yebx FOREIGN KEY (venda_id) REFERENCES public.venda(id);


--
-- Name: produto_categorias fkl5nnwub3ldckfe3jpl2nxc00t; Type: FK CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.produto_categorias
    ADD CONSTRAINT fkl5nnwub3ldckfe3jpl2nxc00t FOREIGN KEY (categorias_id) REFERENCES public.categoria(id);


--
-- Name: pagamento fkn31voy77opqggmeklxo98saio; Type: FK CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.pagamento
    ADD CONSTRAINT fkn31voy77opqggmeklxo98saio FOREIGN KEY (forma_pagamento_id) REFERENCES public.forma_pagamento(id);


--
-- Name: venda fkr27q62kg4j4upx8o4sawj6u9c; Type: FK CONSTRAINT; Schema: public; Owner: nunesvictor
--

ALTER TABLE ONLY public.venda
    ADD CONSTRAINT fkr27q62kg4j4upx8o4sawj6u9c FOREIGN KEY (pagamento_id) REFERENCES public.pagamento(id);


--
-- PostgreSQL database dump complete
--

