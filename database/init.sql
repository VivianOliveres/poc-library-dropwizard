USE library;

CREATE TABLE IF NOT EXISTS book (
    book_id VARCHAR(36) PRIMARY KEY,
    title TEXT NOT NULL
);

insert into book (book_id, title) values ('76f06e59-fbcb-4216-ad9f-3a73efe260c5', 'Head First Design Patterns: A Brain-Friendly Guide');
insert into book (book_id, title) values ('2fd55fbb-71ef-4882-bdbc-c3890c888d12', 'Java Concurrency in Practice');
insert into book (book_id, title) values ('c6708e32-89ba-4418-b70f-9f7e359e822b', 'Designing Data-Intensive Applications: The Big Ideas Behind Reliable, Scalable, and Maintainable Systems');
insert into book (book_id, title) values ('ee1b0b0d-256f-45d9-b018-ced809656c06', 'Spark: The Definitive Guide: Big Data Processing Made Simple');
insert into book (book_id, title) values ('d244089b-cdcf-49be-a792-6f040d2b6713', 'Programming in Scala: A Comprehensive Step-by-Step Guide, Third Edition');
