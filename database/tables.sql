/*----------------------------------------------------*/

DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id          SERIAL          NOT NULL    PRIMARY KEY,
    name        VARCHAR(40)     NOT NULL,
    surname     VARCHAR(80)     NOT NULL,
    cpf         VARCHAR(11)     NOT NULL    UNIQUE,
    email       VARCHAR(100)    NOT NULL,
    password    VARCHAR(70)     NOT NULL,
    icon        INTEGER                     DEFAULT 0,
    admin       BOOLEAN         NOT NULL    DEFAULT FALSE
);

INSERT INTO users VALUES(DEFAULT, 'Admin', 'Admilson', '00000000001', 'admin@admin.com', 'c8837b23ff8aaa8a2dde915473ce0991', 0, true);

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
    id          SERIAL          NOT NULL PRIMARY KEY,
    name        VARCHAR(60)     NOT NULL,
    author      VARCHAR(60)     NOT NULL,
    year        INTEGER         NOT NULL,
    publisher   VARCHAR(60)     NOT NULL,
    pages       INTEGER         NOT NULL,
    image_path  VARCHAR(200)    NOT NULL,
    id_section  INTEGER         NOT NULL REFERENCES sections
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