package com.library.controller;

import com.library.dao.BookDAO;
import com.library.model.Book;
import com.library.model.User;
import com.library.util.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class DashboardController {

    @FXML private Label usernameLabel;
    @FXML private Label pageLabel;
    @FXML private TextField searchField;
    @FXML private TableView<Book> tableView;
    @FXML private TableColumn<Book, String> colBookId;
    @FXML private TableColumn<Book, String> colTitle;
    @FXML private TableColumn<Book, String> colAuthor;
    @FXML private TableColumn<Book, String> colGenre;
    @FXML private TableColumn<Book, String> colPublisher;
    @FXML private TableColumn<Book, Integer> colYear;
    @FXML private TableColumn<Book, String> colIsbn;
    @FXML private TableColumn<Book, Integer> colQuantity;
    @FXML private TableColumn<Book, String> colStatus;
    @FXML private TableColumn<Book, Void> colActions;
    @FXML private Button prevButton;
    @FXML private Button nextButton;
    @FXML private Button logoutButton;
    @FXML private Button addBookButton;

    private final BookDAO bookDAO = new BookDAO();
    private User loggedInUser;

    // Pagination
    private static final int ITEMS_PER_PAGE = 10;
    private int currentPage = 0;
    private List<Book> allBooks = new ArrayList<>();

    @FXML
    public void initialize() {
        // Set up table columns with cell value factories
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colPublisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("yearPublished"));
        colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Style all columns with white text for dark theme
        String cellStyle = "-fx-text-fill: #e2e8f0; -fx-alignment: CENTER-LEFT;";
        colBookId.setStyle(cellStyle);
        colTitle.setStyle(cellStyle);
        colAuthor.setStyle(cellStyle);
        colGenre.setStyle(cellStyle);
        colPublisher.setStyle(cellStyle);
        colYear.setStyle(cellStyle);
        colIsbn.setStyle(cellStyle);
        colQuantity.setStyle(cellStyle);
        colStatus.setStyle(cellStyle);

        // Set up the Actions column with Edit and Delete buttons
        setupActionsColumn();

        // Apply dark theme cell factory to all text columns
        applyCellFactory(colBookId);
        applyCellFactory(colTitle);
        applyCellFactory(colAuthor);
        applyCellFactory(colGenre);
        applyCellFactory(colPublisher);
        applyCellFactory(colIsbn);
        applyCellFactory(colStatus);
        applyIntCellFactory(colYear);
        applyIntCellFactory(colQuantity);

        // Load books from database
        loadBooks();
    }

    /** Called by LoginController after successful authentication */
    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
        if (usernameLabel != null && user != null) {
            usernameLabel.setText(user.getUsername());
        }
    }

    // ===================== DATA LOADING =====================

    private void loadBooks() {
        try {
            allBooks = bookDAO.findAll();
            currentPage = 0;
            loadPage(currentPage);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load books: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ===================== PAGINATION =====================

    private void loadPage(int page) {
        int from = page * ITEMS_PER_PAGE;
        int to = Math.min(from + ITEMS_PER_PAGE, allBooks.size());

        if (allBooks.isEmpty()) {
            tableView.getItems().clear();
            pageLabel.setText("No books found");
            prevButton.setDisable(true);
            nextButton.setDisable(true);
            return;
        }

        ObservableList<Book> pageItems = FXCollections.observableArrayList(allBooks.subList(from, to));
        tableView.setItems(pageItems);

        int totalPages = (int) Math.ceil((double) allBooks.size() / ITEMS_PER_PAGE);
        pageLabel.setText("Page " + (page + 1) + " of " + totalPages);
        prevButton.setDisable(page == 0);
        nextButton.setDisable(page >= totalPages - 1);
    }

    @FXML
    private void onPrev() {
        loadPage(--currentPage);
    }

    @FXML
    private void onNext() {
        loadPage(++currentPage);
    }

    // ===================== SEARCH =====================

    @FXML
    private void onSearch() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            loadBooks();
            return;
        }
        try {
            allBooks = bookDAO.search(keyword);
            currentPage = 0;
            loadPage(currentPage);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Search failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onClearSearch() {
        searchField.clear();
        loadBooks();
    }

    // ===================== CRUD ACTIONS =====================

    @FXML
    private void onAddBook() {
        try {
            Stage stage = (Stage) addBookButton.getScene().getWindow();
            BookFormController controller = SceneManager.switchSceneAndGetController(
                stage, "book-form.fxml", "Library Management System - Add Book"
            );
            controller.setMode(false, null); // Add mode
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to open book form: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void onEditBook(Book book) {
        try {
            Stage stage = (Stage) tableView.getScene().getWindow();
            BookFormController controller = SceneManager.switchSceneAndGetController(
                stage, "book-form.fxml", "Library Management System - Edit Book"
            );
            controller.setMode(true, book); // Edit mode with book data
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to open edit form: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void onDeleteBook(Book book) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Book");
        confirm.setHeaderText("Are you sure?");
        confirm.setContentText("Delete \"" + book.getTitle() + "\" (ID: " + book.getBookId() + ")?\nThis action cannot be undone.");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    bookDAO.delete(book.getBookId());
                    loadBooks(); // Refresh table
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete book: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    // ===================== LOGOUT =====================

    @FXML
    private void onLogout() {
        try {
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            SceneManager.switchScene(stage, "login.fxml", "Library Management System - Login");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to logout: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ===================== ACTIONS COLUMN SETUP =====================

    private void setupActionsColumn() {
        colActions.setCellFactory(param -> new TableCell<>() {
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            private final HBox actionBox = new HBox(8, editBtn, deleteBtn);

            {
                editBtn.setStyle("-fx-background-color: rgba(102,126,234,0.2); -fx-text-fill: #667eea; -fx-font-size: 11; -fx-font-weight: bold; -fx-background-radius: 6; -fx-border-color: rgba(102,126,234,0.3); -fx-border-radius: 6; -fx-cursor: hand; -fx-padding: 3 10;");
                deleteBtn.setStyle("-fx-background-color: rgba(252,129,129,0.15); -fx-text-fill: #fc8181; -fx-font-size: 11; -fx-font-weight: bold; -fx-background-radius: 6; -fx-border-color: rgba(252,129,129,0.3); -fx-border-radius: 6; -fx-cursor: hand; -fx-padding: 3 10;");
                actionBox.setStyle("-fx-alignment: CENTER;");

                editBtn.setOnAction(event -> {
                    Book book = getTableView().getItems().get(getIndex());
                    onEditBook(book);
                });
                deleteBtn.setOnAction(event -> {
                    Book book = getTableView().getItems().get(getIndex());
                    onDeleteBook(book);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : actionBox);
            }
        });
    }

    // ===================== CELL FACTORIES FOR DARK THEME =====================

    private <T> void applyCellFactory(TableColumn<Book, T> column) {
        column.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.toString());
                    setStyle("-fx-text-fill: #e2e8f0;");
                }
            }
        });
    }

    private void applyIntCellFactory(TableColumn<Book, Integer> column) {
        column.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.valueOf(item));
                    setStyle("-fx-text-fill: #e2e8f0;");
                }
            }
        });
    }

    // ===================== UTILITY =====================

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
