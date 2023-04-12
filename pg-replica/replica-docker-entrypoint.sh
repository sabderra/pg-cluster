#!/bin/bash
if [ ! -s "$PGDATA/PG_VERSION" ]; then
echo "*:*:*:$POSTGRES_REPL_USER:$POSTGRES_REPL_PASSWORD" > ~/.pgpass
chmod 0600 ~/.pgpass
until ping -c 1 -W 1 pg-primary
do
echo "Waiting for primary to ping..."
sleep 1s
done
until pg_basebackup -h pg-primary -D ${PGDATA} -U ${POSTGRES_REPL_USER} -X stream -C -S replica_1 -R -vP -W
do
echo "Waiting for master to connect..."
sleep 1s
done
echo "host replication all 0.0.0.0/0 trust" >> "$PGDATA/pg_hba.conf"
set -e

cat > ${PGDATA}/postgresql.auto.conf <<EOF
primary_conninfo = 'host=pg-primary port=5432 user=$POSTGRES_REPL_USER password=$POSTGRES_REPL_PASSWORD'
primary_slot_name = 'replica_1'
EOF


chown postgres ${PGDATA} -R
chmod 700 ${PGDATA} -R
fi

exec "$@"
