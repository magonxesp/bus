from common import fake
import uuid


def random_user():
    return {
        'id': str(uuid.uuid4()),
        'name': fake.user_name(),
    }


def random_book():
    return {
        'id': str(uuid.uuid4()),
        'title': " ".join(fake.words()),
        'author': fake.simple_profile()['name']
    }


def random_offer(bookId, sellerId):
    return {
        'id': str(uuid.uuid4()),
        'bookId': bookId,
        'sellerId': sellerId,
        'price': fake.pyfloat(min_value=1.0, max_value=99.0),
        'stock': fake.pyint(min_value=1)
    }
