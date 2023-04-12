FROM pg-base:0.0.1

COPY ./setup-primary.sh /docker-entrypoint-initdb.d/setup-primary.sh
RUN chmod 0666 /docker-entrypoint-initdb.d/setup-primary.sh

