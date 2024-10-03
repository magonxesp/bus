from faker import Faker
from faker.providers import internet, lorem
import uuid


faker = Faker()
faker.add_provider(internet)
faker.add_provider(lorem)

users = 1000000
sql_file = open("sql/users.sql", "a")
sql_file.write("INSERT INTO users (id, name, role) VALUES ")

for i in range(users):
    sql_file.write(f"('{str(uuid.uuid4())}', '{faker.user_name()}', '{faker.word()}')")
    if i < (users - 1):
        sql_file.write(",")
    else:
        sql_file.write(";")

sql_file.close()
