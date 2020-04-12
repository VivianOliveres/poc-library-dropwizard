USE library;

insert into books (book_id, title) values ('76f06e59-fbcb-4216-ad9f-3a73efe260c5', 'Head First Design Patterns: A Brain-Friendly Guide');
insert into books (book_id, title) values ('2fd55fbb-71ef-4882-bdbc-c3890c888d12', 'Java Concurrency in Practice');
insert into books (book_id, title) values ('c6708e32-89ba-4418-b70f-9f7e359e822b', 'Designing Data-Intensive Applications: The Big Ideas Behind Reliable, Scalable, and Maintainable Systems');
insert into books (book_id, title) values ('ee1b0b0d-256f-45d9-b018-ced809656c06', 'Spark: The Definitive Guide: Big Data Processing Made Simple');
insert into books (book_id, title) values ('d244089b-cdcf-49be-a792-6f040d2b6713', 'Programming in Scala: A Comprehensive Step-by-Step Guide, Third Edition');

insert into users (first_name, family_name, birth_date, membership_date) values ('Vivian', 'Oliveres', '1984-11-17', '2001-07-25');
insert into users (first_name, family_name, birth_date, membership_date) values ('Erich', 'Gamma', '1961-03-13', '1980-08-26');
insert into users (first_name, family_name, birth_date, membership_date) values ('James', 'Gosling', '1955-05-19', '1978-02-20');
insert into users (first_name, family_name, birth_date, membership_date) values ('Margaret', 'Heafield', '1936-08-17', '1955-07-25');

insert into bookings (user_id, book_id, booking_date) values (1, 'c6708e32-89ba-4418-b70f-9f7e359e822b', '2020-04-03');
insert into bookings (user_id, book_id, booking_date) values (1, 'ee1b0b0d-256f-45d9-b018-ced809656c06', '2020-04-03');
insert into bookings (user_id, book_id, booking_date) values (2, 'd244089b-cdcf-49be-a792-6f040d2b6713', '2020-03-27');
insert into bookings (user_id, book_id, booking_date, returned_date) values (3, 'd244089b-cdcf-49be-a792-6f040d2b6713', '2020-03-27', '2020-03-30');
