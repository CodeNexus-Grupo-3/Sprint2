FROM mysql:8

ENV MYSQL_ROOT_PASSWORD=urubu100
ENV MYSQL_DATABASE=codenexus

COPY 001-Create_CodeNexus.sql /docker-entrypoint-initdb.d/

EXPOSE 3306