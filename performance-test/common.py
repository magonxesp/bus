from faker import Faker
from faker.providers import internet, lorem, profile, python


default_headers = {
    "Content-Type": "application/json",
    "Accept": "application/json"
}

fake = Faker()
fake.add_provider(internet)
fake.add_provider(lorem)
fake.add_provider(profile)
fake.add_provider(python)
