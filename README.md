[README.md](https://github.com/user-attachments/files/28174908/README.md)
# 📚 Library Management System

A JavaFX-based Library Management System that allows users to manage a collection of books. The system features secure user authentication, responsive UI design with a modern dark theme, and persistent data storage using a remote Supabase PostgreSQL database.

This project was built for an Object-Oriented Programming (OOP) Workshop Activity.

---

## ✨ Features

* **User Authentication**: Secure Login and Registration system. Passwords are encrypted using SHA-256 hashing before being saved to the database.
* **Modern Dark Theme UI**: A fully responsive interface with gradients, glassmorphism effects, and dynamic hover states.
* **Dashboard with Pagination**: View all books in a neat table format with pagination (10 books per page).
* **Search Functionality**: Instantly search the library catalog by book title or author.
* **Full CRUD Operations**:
  * **Create**: Add new books with details like ISBN, Genre, Publisher, Quantity, and Status.
  * **Read**: View all books in the library.
  * **Update**: Edit existing book details.
  * **Delete**: Remove books from the library with confirmation dialogs.
* **Security**: Protection against SQL Injection using Java `PreparedStatement`s. Environment variables are hidden using a `.env` file.

---

## 🛠️ Technology Stack

* **Language**: Java 21
* **UI Framework**: JavaFX 21
* **Build Tool**: Maven
* **Database**: PostgreSQL (via [Supabase](https://supabase.com))
* **Environment Management**: dotenv-java

---

## ⚙️ Prerequisites

To run this project, your system needs the following installed:
1. **Java Development Kit (JDK) 21 or higher** (BellSoft Liberica JDK with JavaFX bundled is recommended).
2. **Maven** (usually bundled if you are using IntelliJ IDEA).
3. A **Supabase** account (for the remote database).

---

## 🚀 Setup & Installation Guide

Follow these steps to set up the project on your local machine.

### 1. Database Setup (Supabase)

You need to set up the remote database tables.

1. Create a new project on [Supabase](https://supabase.com/).
2. Navigate to the **SQL Editor** in your Supabase dashboard.
3. Open the `schema.sql` file included in this repository.
4. Copy its contents, paste it into the Supabase SQL Editor, and click **Run**.
   * *This will create the `users` and `books` tables and populate the database with 20 sample books.*

### 2. Environment Variables (`.env` file)

For security, the database credentials are not hardcoded. You must create a `.env` file to connect to your Supabase project.

1. Create a new file in the root folder of the project and name it exactly `.env`.
2. Add the following lines to the `.env` file:
   ```env
   DB_URL=jdbc:postgresql://[YOUR_SUPABASE_HOST]:5432/postgres
   DB_USER=postgres
   DB_PASSWORD=[YOUR_SUPABASE_PASSWORD]
   ```
3. Replace `[YOUR_SUPABASE_HOST]` and `[YOUR_SUPABASE_PASSWORD]` with your actual Supabase database credentials.
   * *Note: You can find your host URL in Supabase under **Project Settings -> Database**.*

### 3. Running the Application (IntelliJ IDEA)

1. Open the project folder in **IntelliJ IDEA**.
2. Wait for Maven to download the required dependencies listed in the `pom.xml`.
3. Locate the `Main.java` file in `src/main/java/com/library/Main.java`.
4. Right-click the file and select **Run 'Main.main()'**.

---

## 👤 Default Accounts

If you ran the `schema.sql` file correctly, an admin account is automatically created for testing purposes:

* **Username:** `admin`
* **Password:** `admin`

Alternatively, you can click **Sign Up** on the login screen to register a new account.
