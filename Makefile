TAG := 0.0.1

images: pg-base pg-primary pg-replica

.PHONY: pg-base
pg-base:
	cd pg-base && docker build -f Dockerfile -t pg-base:$(TAG) .

.PHONY: pg-primary
pg-primary:
	cd pg-primary && docker build -f Dockerfile -t pg-primary:$(TAG) .

.PHONY: pg-replica
pg-replica:
	cd pg-replica && docker build -f Dockerfile -t pg-replica:$(TAG) .


setup: pg-base pg-primary pg-replica up
	

up:
	docker-compose up -d

down:
	docker-compose down

.PHONY: clean
clean: 
	docker-compose down
	docker container prune -f

.PHONY: clobber
clobber: clean
	@docker volume prune -f
	@docker rmi pg-primary:$(TAG) pg-replica:$(TAG)
