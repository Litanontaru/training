package com.epam.training.task4.lambda;

import javafx.util.Pair;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {
        List<Author> authors = new ArrayList<>();
        List<Book> books = new ArrayList<>();

        fillAuthorsAndBooksList(authors, books);

        System.out.println("Average age of authors: " + getAverageAgeAuthors(authors).getAsDouble());

        System.out.println("Authors before sort: " + authors);
        sortListAuthorsByAge(authors);
        System.out.println("Authors after sort: " + authors);

        System.out.println("Pensioners: " + getPensioners(authors));
        System.out.println("Names of books and their \"ages\": " + getNameBooks(books));
        System.out.println("Authors who wrote books in collaboration: " + getAuthorsWhoWroteBooksInCollaboration(books));
        System.out.println("Authors and their books: " + getAuthorsAndTheirBooks(books));

    }

    private static OptionalDouble getAverageAgeAuthors(List<Author> authors) {
        return authors
                .stream()
                .map(Author::getAge)
                .mapToInt(Number::intValue)
                .average();
    }

    private static void sortListAuthorsByAge(List<Author> authors) {
        authors.sort(Comparator.comparingInt(Author::getAge));
    }

    private static List<Author> getPensioners(List<Author> authors) {
        return authors.stream()
                .filter(author -> author.getDateOfDeath() == null)
                .filter(author -> author.getGender() == Gender.MALE && author.getAge() > 65
                        || author.getGender() == Gender.FEMALE && author.getAge() > 63)
                .collect(Collectors.toList());

    }

    private static Map<String, Integer> getNameBooks(List<Book> books) {
        /*System.out.print("Names of books and their \"ages\": ");
        books.stream()
                .map(book -> book.getName() + "-" + Period.between(book.getDateOfRelease(), LocalDate.now()).getYears() + " ")
                .forEach(System.out::print);
        System.out.println();*/

        return books.stream()
                .collect(Collectors.toMap(Book::getName, book -> Period.between(book.getDateOfRelease(), LocalDate.now()).getYears()));
    }

    private static Set<Author> getAuthorsWhoWroteBooksInCollaboration(List<Book> books) {
        /*return books.stream()
                .filter(book -> book.getAuthors().size() > 1)
                .map(Book::getAuthors)
                .map(HashSet::new)
                .reduce((x, y) -> {
                    x.addAll(y);
                    return x;
                })
                .get();*/
        return books.stream()
                .filter(book -> book.getAuthors().size() > 1)
                .flatMap(book -> book.getAuthors().stream())
                .collect(Collectors.toSet());
    }

    private static Map<String, String> getAuthorsAndTheirBooks(List<Book> books) {
        return books.stream()
                .flatMap(book -> book.getAuthors().stream().map(author -> new Pair<>(author, book)))
                .collect(Collectors.toMap(pair -> pair.getKey().getName(), pair -> pair.getValue().getName(), (k, m) -> k + ";" + m));
    }


    private static void fillAuthorsAndBooksList(List<Author> authors, List<Book> books) {
        Author author1 = new Author(
                "Bridge"
                , LocalDate.of(1889, Month.SEPTEMBER, 11)
                , LocalDate.of(1974, Month.MAY, 9)
                , Gender.FEMALE
        );
        authors.add(author1);

        Author author2 = new Author(
                "Adams"
                , LocalDate.of(1920, Month.MAY, 9)
                , LocalDate.of(2016, Month.DECEMBER, 24)
                , Gender.MALE
        );
        authors.add(author2);

        Author author3 = new Author(
                "May"
                , LocalDate.of(1950, Month.JUNE, 17)
                , null
                , Gender.MALE
        );
        authors.add(author3);

        Author author4 = new Author(
                "Boosh"
                , LocalDate.of(1970, Month.DECEMBER, 19)
                , null
                , Gender.MALE
        );
        authors.add(author4);

        Author author5 = new Author(
                "Qwers"
                , LocalDate.of(1990, Month.MAY, 11)
                , null
                , Gender.FEMALE
        );
        authors.add(author5);

        Author author6 = new Author(
                "Groh"
                , LocalDate.of(1950, Month.MAY, 29)
                , null
                , Gender.MALE
        );
        authors.add(author6);

        Book book1 = new Book(
                "Bloch"
                , LocalDate.of(1940, Month.AUGUST, 17)
                , new ArrayList<>(Arrays.asList(author1, author2))
        );
        books.add(book1);

        Book book2 = new Book(
                "House"
                , LocalDate.of(1950, Month.JUNE, 15)
                , new ArrayList<>(Arrays.asList(author1))
        );
        books.add(book2);

        Book book3 = new Book(
                "Table"
                , LocalDate.of(1955, Month.SEPTEMBER, 1)
                , new ArrayList<>(Arrays.asList(author2, author3))
        );
        books.add(book3);

        Book book4 = new Book(
                "Apple"
                , LocalDate.of(2000, Month.AUGUST, 17)
                , new ArrayList<>(Arrays.asList(author5, author6))
        );
        books.add(book4);

        Book book5 = new Book(
                "Cake"
                , LocalDate.of(2005, Month.APRIL, 1)
                , new ArrayList<>(Arrays.asList(author4))
        );
        books.add(book5);

    }
}
