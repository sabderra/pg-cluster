TAG := 0.0.1

build-images: build-pg-base build-pg-primary build-pg-replica

.PHONY: build-pg-base
build-pg-base:
	cd pg-base && docker build -f Dockerfile -t pg-base:$(TAG) .

.PHONY: build-pg-primary
build-pg-primary:
	cd pg-primary && docker build -f Dockerfile -t pg-primary:$(TAG) .

.PHONY: build-pg-replica
build-pg-replica:
	cd pg-replica && docker build -f Dockerfile -t pg-replica:$(TAG) .


setup: build-pg-base build-pg-primary build-pg-replica
	

up:
	docker-compose up -d

down:
	docker-compose down


psql:
	docker run -it --rm --network=pg-cluster_pg_cluster postgres:14-alpine bash

.PHONY: clean
clean: 
	docker-compose down
	docker container prune -f

.PHONY: clobber
clobber: clean
	@docker volume prune -f
	@docker rmi pg-primary:$(TAG) pg-replica:$(TAG)
