.PHONY: build deploy

build:
	export DOCKER_IMAGE="books_marketplace_rabbitmq_polling:$$(date +%s)"; \
	docker build -t "$$DOCKER_IMAGE" -f Dockerfile ../../ \
	&& sed -i "s/image: books_marketplace_rabbitmq_polling:.*/image: $$DOCKER_IMAGE/g" stack.yml

deploy:
	docker stack deploy -c stack.yml rabbitmq_polling

stop:
	docker stack rm rabbitmq_polling
