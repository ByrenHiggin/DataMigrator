CREATE TABLE TEST_DATATABLE
(
    id       SERIAL  NOT NULL,
    title    TEXT    NOT NULL,
    complete BOOLEAN NOT NULL DEFAULT false,
    top3_id   INTEGER NOT NULL,

    CONSTRAINT todo_pkey PRIMARY KEY (id)
);
