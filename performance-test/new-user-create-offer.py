from locust import HttpUser, task
import time
from common import default_headers
import generators


class MobileDeviceUser(HttpUser):

    def ensure_user_exists(self, userId):
        for _ in range(5):
            with self.client.get(f"/users/{userId}", headers=default_headers, catch_response=True) as response:
                response.success()
                if response.status_code == 200:
                    break
            time.sleep(3)

    def ensure_book_exists(self, bookId):
        for _ in range(5):
            with self.client.get(f"/books/{bookId}", headers=default_headers, catch_response=True) as response:
                response.success()
                if response.status_code == 200:
                    break
            time.sleep(3)

    @task
    def new_user_create_offer(self):
        # User register into the application
        user = generators.random_user()
        self.client.put("/user", json=user, headers=default_headers)
        time.sleep(1)
        
        # User device wait for user is created (max 5 attempts)
        self.ensure_user_exists(user['id'])
        time.sleep(1)

        # User see the book catalog
        self.client.get("/books", headers=default_headers)
        time.sleep(1)

        # Click to the new offer button and fill the new offer form
        book = generators.random_book()
        self.client.put("/book", json=book, headers=default_headers)
        time.sleep(1)

        # User device wait for book is created (max 5 attempts)
        self.ensure_book_exists(book['id'])
        time.sleep(1)

        # User create the offer
        offer = generators.random_offer(book['id'], user['id'])
        self.client.post("/offer", json=offer, headers=default_headers)
        time.sleep(1)
