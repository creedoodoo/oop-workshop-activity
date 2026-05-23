package com.library.controller;

import com.library.dao.BookDAO;
import com.library.model.Book;
import com.library.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class BookFormController {

    @FXML private Label formTitle;
    @FXML private Label formSubtitle;
    @FXML private Label errorLabel;
    @FXML private TextField bookIdField;
    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private ComboBox<String> genreCombo;
    @FXML private TextField publisherField;
    @FXML private TextField yearField;
    @FXML private TextField isbnField;
    @FXML private Spinner<Integer> quantitySpinner;
    @FXML private ComboBox<String> statusCombo;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private final BookDAO bookDAO = new BookDAO();
    private boolean isEditMode = false;
    private Book existingBook = null;

    @FXML
    public void initialize() {
        // Set up quantity spinner with range 1-999, default value 1
        SpinnerValueFactory<Integer> valueFactory =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999, 1);
        quantitySpinner.setValueFactory(valueFactory);
        quantitySpinner.setEditable(true);

        // Default status selection
        statusCombo.setValue("Available");
    }

    /**
     * Sets the form mode: Add or Edit.
     * @param editMode true for Edit mode, false for Add mode
     * @param book the Book to edit (null for Add mode)
     */
    public void setMode(boolean editMode, Book book) {
        this.isEditMode = editMode;
        this.existingBook = book;

        if (editMode && book != null) {
            // Edit mode — populate fields with existing data
            formTitle.setText("Edit Book");
            formSubtitle.setText("Update the book details below");
            saveButton.setText("Update");

            bookIdField.setText(book.getBookId());
            bookIdField.setEditable(false);
            bookIdField.setStyle(bookIdField.getStyle() + " -fx-opacity: 0.6;");

            titleField.setText(book.getTitle());
            authorField.setText(book.getAuthor());
            genreCombo.setValue(book.getGenre());
            publisherField.setText(book.getPublisher());
            yearField.setText(String.valueOf(book.getYearPublished()));
            isbnField.setText(book.getIsbn());
            quantitySpinner.getValueFactory().setValue(book.getQuantity());
            statusCombo.setValue(book.getStatus());
        } else {
            // Add mode — auto-generate Book ID
            formTitle.setText("Add New Book");
            formSubtitle.setText("Fill in the book details below");
            saveButton.setText("Save");

            try {
                String nextId = bookDAO.getNextBookId();
                bookIdField.setText(nextId);
                bookIdField.setEditable(false);
                bookIdField.setStyle(bookIdField.getStyle() + " -fx-opacity: 0.6;");
            } catch (Exception e) {
                bookIdField.setText("BK-001");
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onSave() {
        // Validate required fields
        if (!validateFields()) {
            return;
        }

        try {
            Book book = buildBookFromFields();

            if (isEditMode) {
                bookDAO.update(book);
            } else {
                bookDAO.insert(book);
            }

            // Return to Dashboard
            navigateToDashboard();

        } catch (Exception e) {
            showError("Save failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onCancel() {
        navigateToDashboard();
    }

    private boolean validateFields() {
        if (bookIdField.getText().trim().isEmpty()) {
            showError("Book ID is required.");
            return false;
        }
        if (titleField.getText().trim().isEmpty()) {
            showError("Title is required.");
            return false;
        }
        if (authorField.getText().trim().isEmpty()) {
            showError("Author is required.");
            return false;
        }
        if (genreCombo.getValue() == null || genreCombo.getValue().isEmpty()) {
            showError("Please select a genre.");
            return false;
        }
        if (publisherField.getText().trim().isEmpty()) {
            showError("Publisher is required.");
            return false;
        }
        if (yearField.getText().trim().isEmpty()) {
            showError("Year Published is required.");
            return false;
        }
        try {
            int year = Integer.parseInt(yearField.getText().trim());
            if (year < 1000 || year > 9999) {
                showError("Please enter a valid year (e.g., 2024).");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Year must be a number.");
            return false;
        }
        if (isbnField.getText().trim().isEmpty()) {
            showError("ISBN is required.");
            return false;
        }
        if (statusCombo.getValue() == null || statusCombo.getValue().isEmpty()) {
            showError("Please select a status.");
            return false;
        }
        return true;
    }

    private Book buildBookFromFields() {
        return new Book(
            isEditMode && existingBook != null ? existingBook.getId() : 0,
            bookIdField.getText().trim(),
            titleField.getText().trim(),
            authorField.getText().trim(),
            genreCombo.getValue(),
            publisherField.getText().trim(),
            Integer.parseInt(yearField.getText().trim()),
            isbnField.getText().trim(),
            quantitySpinner.getValue(),
            statusCombo.getValue()
        );
    }

    private void navigateToDashboard() {
        try {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            SceneManager.switchScene(stage, "dashboard.fxml", "Library Management System - Dashboard");
        } catch (Exception e) {
            showError("Navigation error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }
}
