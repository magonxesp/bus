import { sleep, check } from 'k6'
import http from 'k6/http'
import { randomOffer, randomBook, randomUser } from './generators.js'

const baseUrl = __ENV.BASE_URL ?? 'http://localhost:8081'

export const options = {
    stages: [
        { duration: '30s', target: 10 },
        { duration: '1m', target: 300 },
        { duration: '1m', target: 500 },
        { duration: '1m', target: 1000 },
        // { duration: '1m', target: 5000 },
        // { duration: '1m', target: 10000 },
        { duration: '1m', target: 10 },
        { duration: '1m', target: 0 },
    ],
    thresholds: {
        http_req_failed: ['rate<0.01'], // http errors should be less than 1%
        http_req_duration: ['p(95)<200'], // 95% of requests should be below 200ms
    },
}

export default function () {
    const params = {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }
    }

    const user = randomUser()
    const putUserResponse = http.put(`${baseUrl}/user`, JSON.stringify(user), params)
    check(putUserResponse, {
        [`${baseUrl}/user is status 200`]: (r) => r.status === 200,
    });
    sleep(3)

    const getBooksResponse = http.get(`${baseUrl}/books`, params)
    check(getBooksResponse, {
        [`${baseUrl}/books is status 200`]: (r) => r.status === 200,
    });
    sleep(3)

    const book = randomBook()
    const putBookResponse = http.put(`${baseUrl}/book`, JSON.stringify(book), params)
    check(putBookResponse, {
        [`${baseUrl}/book is status 200`]: (r) => r.status === 200,
    });
    sleep(3)

    const offer = randomOffer(book.id, user.id)
    const createOfferResponse = http.post(`${baseUrl}/offer`, JSON.stringify(offer), params)
    check(createOfferResponse, {
        [`${baseUrl}/offer is status 200`]: (r) => r.status === 200,
    });
    sleep(1)
}
