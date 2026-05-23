package com.library.controller;

import com.library.dao.UserDAO;
import com.library.model.User;
import com.library.util.HashUtil;
import com.library.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SignupController {

    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label messageLabel;
    @FXML private Button signupButton;
    @FXML private Hyperlink loginLink;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    private void onSignUp() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validate all fields are filled
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showMessage("Please fill in all fields.", true);
            return;
        }

        // Validate email format (basic check)
        if (!email.contains("@") || !email.contains(".")) {
            showMessage("Please enter a valid email address.", true);
            return;
        }

        // Validate password match
        if (!password.equals(confirmPassword)) {
            showMessage("Passwords do not match.", true);
            return;
        }

        // Validate password length
        if (password.length() < 6) {
            showMessage("Password must be at least 6 characters.", true);
            return;
        }

        try {
            // Check if username already exists
            if (userDAO.usernameExists(username)) {
                showMessage("Username already taken.", true);
                return;
            }

            // Check if email already exists
            if (userDAO.emailExists(email)) {
                showMessage("Email already registered.", true);
                return;
            }

            // Hash password and register
            String hashedPassword = HashUtil.hashPassword(password);
            User newUser = new User(username, email, hashedPassword);
            userDAO.register(newUser);

            // Navigate to login with success message
            showMessage("Account created successfully! Redirecting to login...", false);

            // Small delay before switching to login
            javafx.application.Platform.runLater(() -> {
                try {
                    Stage stage = (Stage) signupButton.getScene().getWindow();
                    SceneManager.switchScene(stage, "login.fxml", "Library Management System - Login");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            showMessage("Registration error: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    @FXML
    private void onLogin() {
        try {
            Stage stage = (Stage) loginLink.getScene().getWindow();
            SceneManager.switchScene(stage, "login.fxml", "Library Management System - Login");
        } catch (Exception e) {
            showMessage("Error loading login page: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    private void showMessage(String message, boolean isError) {
        messageLabel.setText(message);
        messageLabel.setStyle(isError
            ? "-fx-text-fill: #fc8181; -fx-font-size: 12;"
            : "-fx-text-fill: #68d391; -fx-font-size: 12;");
        messageLabel.setVisible(true);
        messageLabel.setManaged(true);
    }
}
