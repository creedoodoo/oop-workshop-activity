-- ============================================
-- Library Management System — Database Schema
-- Run this in your Supabase SQL Editor
-- ============================================

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id       SERIAL PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    email    VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(64)  NOT NULL  -- SHA-256 hex digest (64 chars)
);

-- Books table
CREATE TABLE IF NOT EXISTS books (
    id             SERIAL PRIMARY KEY,
    book_id        VARCHAR(20)  UNIQUE NOT NULL,
    title          VARCHAR(255) NOT NULL,
    author         VARCHAR(150) NOT NULL,
    genre          VARCHAR(100),
    publisher      VARCHAR(150),
    year_published INT,
    isbn           VARCHAR(20)  UNIQUE,
    quantity       INT DEFAULT 1,
    status         VARCHAR(20) DEFAULT 'Available'
);

-- ============================================
-- Seed Data — 20 Sample Books
-- ============================================

INSERT INTO books (book_id, title, author, genre, publisher, year_published, isbn, quantity, status) VALUES
('BK-001', 'The Great Gatsby',              'F. Scott Fitzgerald', 'Classic',       'Scribner',           1925, '978-0743273565', 3, 'Available'),
('BK-002', 'To Kill a Mockingbird',         'Harper Lee',          'Classic',       'J. B. Lippincott',   1960, '978-0061935466', 2, 'Available'),
('BK-003', '1984',                          'George Orwell',       'Dystopian',     'Secker & Warburg',   1949, '978-0451524935', 4, 'Available'),
('BK-004', 'Brave New World',               'Aldous Huxley',       'Dystopian',     'Chatto & Windus',    1932, '978-0060850524', 2, 'Available'),
('BK-005', 'The Catcher in the Rye',        'J.D. Salinger',       'Literary',      'Little, Brown',      1951, '978-0316769174', 3, 'Available'),
('BK-006', 'Pride and Prejudice',           'Jane Austen',         'Romance',       'T. Egerton',         1813, '978-0141439518', 5, 'Available'),
('BK-007', 'The Hobbit',                    'J.R.R. Tolkien',      'Fantasy',       'Allen & Unwin',      1937, '978-0547928227', 3, 'Available'),
('BK-008', 'Harry Potter and the Sorcerer''s Stone', 'J.K. Rowling', 'Fantasy',   'Bloomsbury',         1997, '978-0439708180', 6, 'Available'),
('BK-009', 'The Da Vinci Code',             'Dan Brown',           'Thriller',      'Doubleday',          2003, '978-0307474278', 4, 'Available'),
('BK-010', 'Gone Girl',                     'Gillian Flynn',       'Thriller',      'Crown Publishing',   2012, '978-0307588371', 2, 'Available'),
('BK-011', 'Dune',                          'Frank Herbert',       'Sci-Fi',        'Chilton Books',      1965, '978-0441013593', 3, 'Available'),
('BK-012', 'The Alchemist',                 'Paulo Coelho',        'Fiction',       'HarperCollins',      1988, '978-0062315007', 4, 'Available'),
('BK-013', 'Atomic Habits',                 'James Clear',         'Self-Help',     'Avery',              2018, '978-0735211292', 5, 'Available'),
('BK-014', 'Sapiens',                       'Yuval Noah Harari',   'Non-Fiction',   'Harvill Secker',     2011, '978-0062316097', 3, 'Available'),
('BK-015', 'The Power of Habit',            'Charles Duhigg',      'Self-Help',     'Random House',       2012, '978-0812981605', 2, 'Available'),
('BK-016', 'Rich Dad Poor Dad',             'Robert T. Kiyosaki',  'Finance',       'Warner Books',       1997, '978-1612680194', 4, 'Available'),
('BK-017', 'The Lean Startup',              'Eric Ries',           'Business',      'Crown Business',     2011, '978-0307887894', 3, 'Available'),
('BK-018', 'Clean Code',                    'Robert C. Martin',    'Technology',    'Prentice Hall',      2008, '978-0132350884', 2, 'Available'),
('BK-019', 'Design Patterns',               'Gang of Four',        'Technology',    'Addison-Wesley',     1994, '978-0201633610', 2, 'Available'),
('BK-020', 'The Pragmatic Programmer',      'Andrew Hunt',         'Technology',    'Addison-Wesley',     1999, '978-0135957059', 3, 'Available');
