/*----------------------------------------------------*/

# DROP TABLE IF EXISTS configuracoes;
# CREATE TABLE configuracoes
# (
#     id              SERIAL  NOT NULL PRIMARY KEY,
#     dias_de_aluguel INTEGER NOT NULL
# );

DROP TABLE IF EXISTS usuarios;
CREATE TABLE usuarios
(
    id          SERIAL          NOT NULL    PRIMARY KEY,
    nome        VARCHAR(40)     NOT NULL,
    sobrenome   VARCHAR(80)     NOT NULL,
    cpf         VARCHAR(11)     NOT NULL,
    email       VARCHAR(100)    NOT NULL,
    senha       VARCHAR(70)     NOT NULL,
    admin       BOOLEAN         NOT NULL    DEFAULT FALSE
);

/*----------------------------------------------------*/

DROP TABLE IF EXISTS secoes;
CREATE TABLE secoes
(
    id          SERIAL      NOT NULL    PRIMARY KEY,
    nome        VARCHAR(60) NOT NULL,
    descricao   TEXT                    DEFAULT NULL,
    ativa       BOOLEAN     NOT NULL    DEFAULT TRUE
);

DROP TABLE IF EXISTS livros;
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

/*----------------------------------------------------*/

DROP TABLE IF EXISTS alugados;
CREATE TABLE alugados
(
    id              SERIAL  NOT NULL    PRIMARY KEY,
    id_usuario      INTEGER NOT NULL    REFERENCES usuarios,
    id_livro        INTEGER NOT NULL    REFERENCES livros,
    data_retirada   DATE    NOT NULL,
    data_devolucao  DATE                DEFAULT NULL
);

# DROP TABLE IF EXISTS multas;
# CREATE TABLE multas
# (
#     id          SERIAL  NOT NULL PRIMARY KEY,
#     id_usuario  INTEGER NOT NULL REFERENCES usuarios,
#     id_livro    INTEGER NOT NULL REFERENCES livros,
#     data        DATE    NOT NULL
# );