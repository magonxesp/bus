.PHONY: build deploy

build:
	export DOCKER_IMAGE="books_marketplace_rabbitmq:$$(date +%s)"; \
	docker build -t "$$DOCKER_IMAGE" -f Dockerfile ../../ \
	&& sed -i.bak "s/image: books_marketplace_rabbitmq:.*/image: $$DOCKER_IMAGE/g" stack.yml

deploy:
	docker stack deploy -c stack.yml rabbitmq

stop:
	docker stack rm rabbitmq

logs-backend:
	docker service logs -f --tail 10 rabbitmq_backend

logs-consumer:
	docker service logs -f --tail 10 rabbitmq_consumer
