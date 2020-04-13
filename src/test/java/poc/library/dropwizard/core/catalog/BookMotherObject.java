package poc.library.dropwizard.core.catalog;

import lombok.experimental.UtilityClass;
import poc.library.dropwizard.core.domain.Book;

import java.util.List;
import java.util.UUID;

@UtilityClass
public class BookMotherObject {

    public static Book DESIGN_PATERNS =
            new Book(
                    UUID.fromString("76f06e59-fbcb-4216-ad9f-3a73efe260c5"),
                    "Head First Design Patterns: A Brain-Friendly Guide");
    public static Book CONCURRENCY =
            new Book(
                    UUID.fromString("2fd55fbb-71ef-4882-bdbc-c3890c888d12"),
                    "Java Concurrency in Practice");
    public static Book DATA =
            new Book(
                    UUID.fromString("c6708e32-89ba-4418-b70f-9f7e359e822b"),
                    "Designing Data-Intensive Applications: The Big Ideas Behind Reliable, Scalable, and Maintainable Systems");
    public static Book SPARK =
            new Book(
                    UUID.fromString("ee1b0b0d-256f-45d9-b018-ced809656c06"),
                    "Spark: The Definitive Guide: Big Data Processing Made Simple");
    public static Book SCALA =
            new Book(
                    UUID.fromString("d244089b-cdcf-49be-a792-6f040d2b6713"),
                    "Programming in Scala: A Comprehensive Step-by-Step Guide, Third Edition");

    public static List<Book> ALL_BOOKS = List.of(DESIGN_PATERNS, CONCURRENCY, DATA, SPARK, SCALA);
}
