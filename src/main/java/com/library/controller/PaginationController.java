package com.library.controller;

/**
 * PaginationController
 *
 * Note: Pagination logic is embedded directly in DashboardController
 * as the pagination controls (Previous/Next buttons, page label) are
 * tightly coupled with the book TableView and its data source.
 *
 * See DashboardController.loadPage(), onPrev(), and onNext() methods
 * for the complete pagination implementation.
 *
 * Configuration:
 * - ITEMS_PER_PAGE = 10 (10 books per page)
 * - 20 seed records → 2 pages
 * - Previous/Next buttons are disabled at boundaries
 */
public class PaginationController {
    // Pagination logic is implemented in DashboardController.
    // This class exists to satisfy the project structure requirement.
}
