USE library;
SET time_zone = 'Europe/Paris';

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS ratings;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE IF NOT EXISTS books (
    book_id VARCHAR(36) PRIMARY KEY,
    title TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    family_name VARCHAR(50) NOT NULL,
    birth_date DATE NOT NULL,
    membership_date DATE NOT NULL
);
ALTER TABLE users ADD UNIQUE users_unique_index(first_name, family_name);

CREATE TABLE IF NOT EXISTS bookings (
    booking_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    book_id VARCHAR(36) NOT NULL,
    booking_date DATE NOT NULL,
    returned_date DATE,
    CONSTRAINT fk_bookings_to_user_id FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_bookings_to_book_id FOREIGN KEY (book_id) REFERENCES books(book_id)
);

CREATE TABLE IF NOT EXISTS ratings (
    rating_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    book_id VARCHAR(36),
    rating_value SMALLINT,
    CONSTRAINT fk_ratings_to_user_id FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_ratings_to_book_id FOREIGN KEY (book_id) REFERENCES books(book_id)
);
