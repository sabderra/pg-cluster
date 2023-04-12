#!/bin/bash
echo "host replication all 0.0.0.0/0 trust" >> "$PGDATA/pg_hba.conf"
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
CREATE USER $POSTGRES_REPL_USER REPLICATION LOGIN CONNECTION LIMIT 100 ENCRYPTED PASSWORD '$POSTGRES_REPL_PASSWORD';
EOSQL

cat >> ${PGDATA}/postgresql.conf <<EOF
wal_level = replica
wal_log_hints = on 
archive_mode = on
archive_command = 'cd .'
max_wal_senders = 8
wal_keep_size = 8
hot_standby = on
log_replication_commands on
EOF
