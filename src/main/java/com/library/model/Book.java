package com.library.model;

import javafx.beans.property.*;

public class Book {

    private final IntegerProperty id;
    private final StringProperty bookId;
    private final StringProperty title;
    private final StringProperty author;
    private final StringProperty genre;
    private final StringProperty publisher;
    private final IntegerProperty yearPublished;
    private final StringProperty isbn;
    private final IntegerProperty quantity;
    private final StringProperty status;

    public Book(int id, String bookId, String title, String author, String genre,
                String publisher, int yearPublished, String isbn, int quantity, String status) {
        this.id = new SimpleIntegerProperty(id);
        this.bookId = new SimpleStringProperty(bookId);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.genre = new SimpleStringProperty(genre);
        this.publisher = new SimpleStringProperty(publisher);
        this.yearPublished = new SimpleIntegerProperty(yearPublished);
        this.isbn = new SimpleStringProperty(isbn);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.status = new SimpleStringProperty(status);
    }

    // --- Property accessors (for TableView column binding) ---

    public IntegerProperty idProperty() { return id; }
    public StringProperty bookIdProperty() { return bookId; }
    public StringProperty titleProperty() { return title; }
    public StringProperty authorProperty() { return author; }
    public StringProperty genreProperty() { return genre; }
    public StringProperty publisherProperty() { return publisher; }
    public IntegerProperty yearPublishedProperty() { return yearPublished; }
    public StringProperty isbnProperty() { return isbn; }
    public IntegerProperty quantityProperty() { return quantity; }
    public StringProperty statusProperty() { return status; }

    // --- Standard getters ---

    public int getId() { return id.get(); }
    public String getBookId() { return bookId.get(); }
    public String getTitle() { return title.get(); }
    public String getAuthor() { return author.get(); }
    public String getGenre() { return genre.get(); }
    public String getPublisher() { return publisher.get(); }
    public int getYearPublished() { return yearPublished.get(); }
    public String getIsbn() { return isbn.get(); }
    public int getQuantity() { return quantity.get(); }
    public String getStatus() { return status.get(); }

    // --- Standard setters ---

    public void setId(int id) { this.id.set(id); }
    public void setBookId(String bookId) { this.bookId.set(bookId); }
    public void setTitle(String title) { this.title.set(title); }
    public void setAuthor(String author) { this.author.set(author); }
    public void setGenre(String genre) { this.genre.set(genre); }
    public void setPublisher(String publisher) { this.publisher.set(publisher); }
    public void setYearPublished(int yearPublished) { this.yearPublished.set(yearPublished); }
    public void setIsbn(String isbn) { this.isbn.set(isbn); }
    public void setQuantity(int quantity) { this.quantity.set(quantity); }
    public void setStatus(String status) { this.status.set(status); }
}
