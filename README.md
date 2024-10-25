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
* *in-memory-async* (WIP 👷‍♂️): Uses the in memory async bus implementation based in kotlin coroutines.

## Performance test

First you need to run the example application you want to benchmark.
Let's suppose we want to test the application that uses in memory implementations.
Well, we start the ``in-memory`` example application and PostgreSQL.

```sh
$ docker compose up -d in-memory postgresql
```

Then, install ``virtualenv`` if it is not installed

```sh
$ pip install virtualenv
```

Run ``create-env`` action

```sh
$ make create-env
```

Activate the virtualenv and run locust with the "new user creating a offer" test

```sh
$ souce venv/bin/activate
(venv) $ export PYTHONPATH="$(pwd)/performance-test"
(venv) $ locust -f performance-test/new-user-create-offer.py
```

### Available performance tests

All tests scenarios are thinking about a user usign a mobile application.

* *new-user-create-offer.py*: The user is new to the application, he signs up and then registers a new book that don't exist on the marketplace, then the user create a new offer for that book.

Comming soon more tests.
