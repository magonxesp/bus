.PHONY: build deploy

build:
	export DOCKER_IMAGE="books_marketplace_in_memory:$$(date +%s)"; \
	docker build -t "$$DOCKER_IMAGE" -f Dockerfile ../../ \
	&& sed -i "s/image: books_marketplace_in_memory:.*/image: $$DOCKER_IMAGE/g" stack.yml

deploy:
	docker stack deploy -c stack.yml in_memory

stop:
	docker stack rm in_memory

logs-backend:
	docker service logs -f --tail 10 in_memory_backend

logs-consumer:
	docker service logs -f --tail 10 in_memory_consumer
