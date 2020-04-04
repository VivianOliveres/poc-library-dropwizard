USE library;

CREATE TABLE IF NOT EXISTS book (
    book_id VARCHAR(36) PRIMARY KEY,
    isbn VARCHAR(50) NOT NULL,
    title TEXT NOT NULL
);

insert into book (book_id, isbn, title) values ('76f06e59-fbcb-4216-ad9f-3a73efe260c5', '9780596007126', 'Head First Design Patterns: A Brain-Friendly Guide');
insert into book (book_id, isbn, title) values ('2fd55fbb-71ef-4882-bdbc-c3890c888d12', '0321349601', 'Java Concurrency in Practice');
insert into book (book_id, isbn, title) values ('c6708e32-89ba-4418-b70f-9f7e359e822b', 'B06XPJML5D', 'Designing Data-Intensive Applications: The Big Ideas Behind Reliable, Scalable, and Maintainable Systems');
insert into book (book_id, isbn, title) values ('ee1b0b0d-256f-45d9-b018-ced809656c06', 'B079P71JHY', 'Spark: The Definitive Guide: Big Data Processing Made Simple');
insert into book (book_id, isbn, title) values ('d244089b-cdcf-49be-a792-6f040d2b6713', 'B01EX49FOU', 'Programming in Scala: A Comprehensive Step-by-Step Guide, Third Edition');

--insert into book (book_id, isbn, title) values ('a0bec05b-6ae4-4ecb-9214-bf8d71d0e170', '', '');
--insert into book (book_id, isbn, title) values ('70dc27bb-7710-454d-a149-7afa07ea2aaa', '', '');
--insert into book (book_id, isbn, title) values ('201b6dbc-5024-4d61-859e-b912973007bb', '', '');
--insert into book (book_id, isbn, title) values ('4ebe30cf-793d-46c0-b7ef-bc84d2af2f78', '', '');
--insert into book (book_id, isbn, title) values ('f5353b87-7c4d-414b-9d7c-0b911cc9f19f', '', '');
