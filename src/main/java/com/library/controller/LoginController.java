package com.library.controller;

import com.library.dao.UserDAO;
import com.library.model.User;
import com.library.util.HashUtil;
import com.library.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Button loginButton;
    @FXML private Hyperlink signupLink;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    private void onLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        // Validate fields are not empty
        if (username.isEmpty() || password.isEmpty()) {
            showError("Please fill in all fields.");
            return;
        }

        try {
            // Hash the password and authenticate
            String hashedPassword = HashUtil.hashPassword(password);
            User user = userDAO.authenticate(username, hashedPassword);

            if (user != null) {
                // Login successful — switch to Dashboard
                Stage stage = (Stage) loginButton.getScene().getWindow();
                DashboardController controller = SceneManager.switchSceneAndGetController(
                    stage, "dashboard.fxml", "Library Management System - Dashboard"
                );
                controller.setLoggedInUser(user);
            } else {
                showError("Invalid username or password.");
            }
        } catch (Exception e) {
            showError("Connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onSignUp() {
        try {
            Stage stage = (Stage) signupLink.getScene().getWindow();
            SceneManager.switchScene(stage, "signup.fxml", "Library Management System - Sign Up");
        } catch (Exception e) {
            showError("Error loading signup page: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }
}
