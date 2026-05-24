// Book.java
public class Book {
    int isbn;
    String title, author;
    Book left, right;

    // Borrow date fields — used for fine calculation
    // Stored as day/month/year integers for simplicity (no external library needed)
    int borrowDay, borrowMonth, borrowYear;
    boolean isBorrowed;

    public Book(int isbn, String title, String author) {
        this.isbn        = isbn;
        this.title       = title;
        this.author      = author;
        this.isBorrowed  = false;
        this.borrowDay   = 0;
        this.borrowMonth = 0;
        this.borrowYear  = 0;
    }

    // Set the borrow date when a book is borrowed
    public void setBorrowDate(int day, int month, int year) {
        this.borrowDay   = day;
        this.borrowMonth = month;
        this.borrowYear  = year;
        this.isBorrowed  = true;
    }

    // Calculate how many days since borrowDate until returnDate
    // Simple day-difference calculation without using any date library
    public int daysUntil(int retDay, int retMonth, int retYear) {
        int[] daysInMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        // Leap year adjustment
        if ((borrowYear % 4 == 0 && borrowYear % 100 != 0) || borrowYear % 400 == 0) {
            daysInMonth[2] = 29;
        }

        // Convert both dates to total days from a base point
        long borrow = toDays(borrowDay, borrowMonth, borrowYear);
        long ret    = toDays(retDay, retMonth, retYear);
        return (int)(ret - borrow);
    }

    // Helper: convert a date to a total day count (from year 0)
    private long toDays(int d, int m, int y) {
        long total = (long) y * 365 + d;
        for (int i = 1; i < m; i++) {
            int[] dim = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            total += dim[i];
        }
        total += (y / 4) - (y / 100) + (y / 400); // leap year days
        return total;
    }
}