SPRING 01
Exercise: Create a REST API for managing a library system with the following features:

✓    CRUD Operations:
        Manage books (similar to your first exercise but now with additional relationships).
        Manage authors (create, update, list all, and delete authors).

✓    Relationships:
        A book should be linked to an author (one-to-many relationship: an author can have multiple books).

✓    Search and Filtering:
        Search for books by title, author, or genre.
        Filter books published in a specific year or after a given year.

    Pagination and Sorting:
        Paginate the results when retrieving all books or authors.
        Allow sorting by title, publication date, or author's name.

✓    Validation:
        Use Hibernate Validator annotations to validate fields like book title (not empty) and publication year (valid range).

    Tests:
        Integration and Unit Tests

✓   Exception Handling:
        Handle exceptions gracefully using @ControllerAdvice and @ExceptionHandler.
