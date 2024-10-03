from locust import HttpUser, task
from faker import Faker
from faker.providers import internet, lorem
import uuid


faker = Faker()
faker.add_provider(internet)
faker.add_provider(lorem)

default_headers = {
    "Content-Type": "application/json",
    "Accept": "application/json"
}


class HelloWorldUser(HttpUser):
    @task
    def hello_world(self):
        self.client.put("/users", json=create_user(), headers=default_headers)


def create_user():
    return {
        'id': str(uuid.uuid4()),
        'name': faker.user_name(),
        'role': faker.word()
    }
