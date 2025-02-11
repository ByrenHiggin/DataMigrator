FROM postgres:alpine

ADD /docker/test_migrations/1_schema.sql /docker-entrypoint-initdb.d
ADD /docker/test_migrations/2_data.sql /docker-entrypoint-initdb.d

RUN chmod a+r /docker-entrypoint-initdb.d/*
