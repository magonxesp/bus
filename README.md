# Bus

Preconfigured command, query and domain event bus.


## About the example application (WIP)

The example application is a books marketplace, where you can sell a book as user and other
can buy it.

* ✅ A user can list the available books 
* ✅ A user can sell a book
* ✅ A user can view the book's sellers
* ✅ A user can choose a seller and buy the book

### Technical considerations

The application is an API built with Ktor, the endpoints with method ``PUT`` and ``POST`` are implemented in mind there are asynchronous and should support do asynchronous operations, so they don't respond any content, only the ``200 OK`` status code.

Although, the endpoints with ``GET`` method are fully synchronous.

The PostgreSQL database doesn't use foreign keys for allow concurrent inserts without throwing errors of foreign key violations.

### Example applications available

* *in-memory*: Uses the sync in memory bus implementation.
* *in-memory-async*: Uses the in memory async bus implementation based in ``ThreadPoolExecutor`` with a maximum of 4 threads.
* *rabbitmq*: Uses the RabbitMQ bus implementation.

## Performance test

### Deploy example application

First you need to run the example application you want to benchmark.
Let's suppose we want to test the application that uses in memory implementations.
Well, we start the ``in-memory-async`` example application and PostgreSQL.

#### With docker compose
```sh
$ docker compose -f examples/in-memory-async/docker-compose.yml up -d backend postgresql
```

#### With docker swarm

⚠️ Services have the resources limited, change or remove resource limits for your needs ⚠️

Build the image

```sh
$ docker build -f examples/in-memory-async/Dockerfile -t books_marketplace_in_memory_async:latest .
```

Edit the stack file replacing the image by the built one. Then deploy the stack.

```sh
$ docker stack deploy -c examples/in-memory-async/stack.yml books_marketplace_in_memory_async
```

### Run the performance test

First, install [k6](https://grafana.com/docs/k6/latest/set-up/install-k6/) if it is not installed.

Then, run the "new user creating an offer" performance test for example.

```sh
$ k6 run k6/new-user-create-offer.js
```

Alternatively you can use the ``Makefile`` action.

```sh
$ make test-new-user-create-offer
```

### Available performance tests

All tests are located in the ``k6`` directory. 

Tests scenarios are thinking about a user using a mobile application.

* *new-user-create-offer.js*: The user is new to the application, he signs up and then registers a new book that don't exist on the marketplace, then the user create a new offer for that book.

Coming soon more tests.
