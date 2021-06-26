CREATE SCHEMA IF NOT EXISTS ava_lab;


create sequence ava_lab.hibernate_sequence;


CREATE TABLE ava_lab.ocr_data
(
    id          bigserial    NOT NULL,
    external_id int8         NOT NULL,
    word        varchar(255) NOT NULL,
    created_at  timestamp    NOT NULL,
    CONSTRAINT ocr_data_id_pkey PRIMARY KEY (id)
);
