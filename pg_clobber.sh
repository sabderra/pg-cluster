#! /usr/bin/env bash
docker-compose down
docker rm $(docker ps -a -f status=exited -q)
docker volume prune -f
docker rmi pg-main pg-replica
