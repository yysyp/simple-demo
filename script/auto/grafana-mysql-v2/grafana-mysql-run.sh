#! /bin/bash -eu

docker build -f Dockerfile-mysql8 -t test/mysql8:latest .
docker build -f Dockerfile-grafana9 -t test/grafana9:latest .
docker image prune

#docker-compose up -d
docker compose up
