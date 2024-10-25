from locust import HttpUser, task
import time
from common import default_headers
import generators


class MobileDeviceUser(HttpUser):

    @task
    def new_user_create_offer(self):
        # User register into the application
        user = generators.random_user()
        self.client.put("/user", json=user, headers=default_headers)
        time.sleep(1)

        # User see the book catalog
        self.client.get("/books", headers=default_headers)
        time.sleep(1)

        # Click to the new offer button and fill the new offer form
        book = generators.random_book()
        self.client.put("/book", json=book, headers=default_headers)
        time.sleep(1)

        # User create the offer
        offer = generators.random_offer(book['id'], user['id'])
        self.client.post("/offer", json=offer, headers=default_headers)
        time.sleep(1)
