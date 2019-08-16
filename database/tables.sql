/*----------------------------------------------------*/

DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id          SERIAL          NOT NULL    PRIMARY KEY,
    name        VARCHAR(40)     NOT NULL,
    surname     VARCHAR(80)     NOT NULL,
    cpf         VARCHAR(11)     NOT NULL,
    email       VARCHAR(100)    NOT NULL,
    password    VARCHAR(70)     NOT NULL,
    icon        INTEGER                     DEFAULT 0,
    admin       BOOLEAN         NOT NULL    DEFAULT FALSE
);

/*----------------------------------------------------*/

DROP TABLE IF EXISTS sections;
CREATE TABLE sections
(
    id          SERIAL      NOT NULL    PRIMARY KEY,
    name        VARCHAR(60) NOT NULL,
    description TEXT                    DEFAULT NULL,
    active      BOOLEAN     NOT NULL    DEFAULT TRUE
);

DROP TABLE IF EXISTS books;
CREATE TABLE books
(
    id          SERIAL      NOT NULL PRIMARY KEY,
    name        VARCHAR(60) NOT NULL,
    author      VARCHAR(60) NOT NULL,
    year        INTEGER     NOT NULL,
    publisher   VARCHAR(60) NOT NULL,
    pages       INTEGER     NOT NULL,
    id_section  INTEGER     NOT NULL REFERENCES sections
);

/*----------------------------------------------------*/

DROP TABLE IF EXISTS rented;
CREATE TABLE rented
(
    id              SERIAL  NOT NULL    PRIMARY KEY,
    id_user         INTEGER NOT NULL    REFERENCES users,
    id_book         INTEGER NOT NULL    REFERENCES books,
    date_rented     DATE    NOT NULL,
    date_returned   DATE                DEFAULT NULL
);