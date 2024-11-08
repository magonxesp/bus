import {
    randomIntBetween,
    randomString,
    uuidv4,
} from 'https://jslib.k6.io/k6-utils/1.4.0/index.js'

export const randomUser = () => ({
    id: uuidv4(),
    name: randomString(35),
})

export const randomBook = () => ({
    id: uuidv4(),
    title: randomString(35),
    author: randomString(35)
})

export const randomOffer = (bookId, sellerId) => ({
    id: uuidv4(),
    bookId: bookId,
    sellerId: sellerId,
    price: randomIntBetween(1, 99),
    stock: randomIntBetween(1, 5)
})
