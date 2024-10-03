# Bus

Preconfigured command, query and domain event bus.


## About the example application (WIP)

The example application is a books marketplace, where you can sell a book as user and other
can buy it.

* A user can list the available books
* A user can sell a book
* A user can view the book's sellers
* A user can choose a seller and buy the book

## Performance test

First you need to run the example application you want to benchmark.
Let's suppose we want to test the application that uses in memory implementations.
Well, we start the ``in-memory-bus`` example application and PostgreSQL.

```sh
$ docker compose up -d in-memory-bus postgresql
```

Then, install ``virtualenv`` if it is not installed

```sh
$ pip install virtualenv
```

Run ``create-env`` action under ``performance-test`` directory

```sh
$ cd performance-test
$ make create-env
```

Activate the virtualenv and run locust

```sh
$ souce venv/bin/activate
(venv) $ locust
```
