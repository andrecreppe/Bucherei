CREATE TABLE secoes
(
    id          SERIAL      NOT NULL PRIMARY KEY,
    nome        VARCHAR(60) NOT NULL,
    descricao   VARCHAR(50)
    ativa       BOOLEAN NOT NULL
);

CREATE TABLE livros
(
    id          SERIAL      NOT NULL PRIMARY KEY,
    nome        VARCHAR(60) NOT NULL,
    autor       VARCHAR(60) NOT NULL,
    ano         INTEGER     NOT NULL,
    editora     VARCHAR(60) NOT NULL,
    paginas     INTEGER     NOT NULL,
    id_secao    INTEGER     NOT NULL REFERENCES secoes
);

/*---------------------------*/

CREATE TABLE usuarios
(
    id          SERIAL NOT NULL PRIMARY KEY,
    nome        VARCHAR(40) NOT NULL,
    sobrenome   VARCHAR(80) NOT NULL
    cpf         VARCHAR(11) NOT NULL,
    email       VARCHAR(100) NOT NULL,
    senha       VARCHAR(70) NOT NULL
);

/*---------------------------*/

CREATE TABLE alugados
(
    id              SERIAL  NOT NULL PRIMARY KEY,
    id_usuario      INTEGER NOT NULL REFERENCES usuarios,
    id_livro        INTEGER NOT NULL REFERENCES livros,
    data_aluga      DATE    NOT NULL,
    data_devolucao  DATE
);