FROM pg-base:0.0.1

COPY ./replica-docker-entrypoint.sh /docker-entrypoint.sh
RUN chmod +x /docker-entrypoint.sh
ENTRYPOINT ["/docker-entrypoint.sh"]
CMD ["gosu", "postgres", "postgres", "-c", "log_statement=all"]
