// SmartLibrary.java
import java.util.Scanner;

public class SmartLibrary implements LibraryADT {

    // ISBN validation: digits only, up to 10 digits (int range)
    private static final int    ISBN_MAX_DIGITS = 10;

    // Fine settings
    private static final double FINE_RATE       = 0.50; // RM per day
    private static final int    LOAN_DAYS       = 14;   // free loan period

    // Private fields — Information Hiding
    private BookBST     catalogue = new BookBST();
    private BorrowStack history   = new BorrowStack();

    // =========================================================
    // Private helper: validate and read ISBN from console
    // Returns -1 if invalid
    // =========================================================
    private int readIsbn(Scanner sc) {
        if (!sc.hasNextLine()) {
            System.out.println("  [Error] ISBN is required.");
            return -1;
        }
        String token = sc.nextLine().trim();
        if (!token.matches("\\d+")) {
            System.out.println("  [Error] ISBN must contain digits only. No letters or symbols.");
            return -1;
        }
        if (token.length() > ISBN_MAX_DIGITS) {
            System.out.println("  [Error] ISBN must be at most " + ISBN_MAX_DIGITS + " digits.");
            return -1;
        }
        int isbn;
        try {
            isbn = Integer.parseInt(token);
        } catch (NumberFormatException e) {
            System.out.println("  [Error] ISBN value is too large.");
            return -1;
        }
        if (isbn <= 0) {
            System.out.println("  [Error] ISBN must be a positive number.");
            return -1;
        }
        return isbn;
    }

    // =========================================================
    // Private helper: validate and read a date from console
    // Returns int[]{day, month, year} or null if invalid
    // =========================================================
    private int[] readDate(Scanner sc) {
        System.out.print("  Enter date (DD MM YYYY): ");
        String line = sc.nextLine().trim();
        String[] parts = line.split("\\s+");
        if (parts.length != 3) {
            System.out.println("  [Error] Please enter date as: DD MM YYYY (e.g. 25 05 2025)");
            return null;
        }
        try {
            int d = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            if (m < 1 || m > 12) { System.out.println("  [Error] Month must be 1-12."); return null; }
            if (d < 1 || d > 31) { System.out.println("  [Error] Day must be 1-31.");   return null; }
            if (y < 2000 || y > 2100) { System.out.println("  [Error] Year must be 2000-2100."); return null; }
            return new int[]{d, m, y};
        } catch (NumberFormatException e) {
            System.out.println("  [Error] Date must contain numbers only.");
            return null;
        }
    }

    // =========================================================
    // Private helper: read a non-empty line of text
    // =========================================================
    private String readLine(Scanner sc, String prompt) {
        String input = "";
        while (input.isEmpty()) {
            System.out.print(prompt);
            input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("  [Error] This field cannot be empty. Please try again.");
            }
        }
        return input;
    }

    // =========================================================
    // Menu item 1: Add Book
    // =========================================================
    @Override
    public void addBook(int i, String t, String a) {
        catalogue.insert(i, t, a);
        System.out.println("  Book added: [ISBN: " + i + "] " + t + " by " + a);
    }

    // =========================================================
    // Menu item 2: Search Book by ISBN (BST recursive, O log n)
    // =========================================================
    @Override
    public void searchBook(int i) {
        Book b = catalogue.search(i);
        if (b != null) {
            System.out.println("  Found: [ISBN: " + b.isbn + "] " + b.title + " by " + b.author);
        } else {
            System.out.println("  Not Found.");
        }
    }

    // =========================================================
    // Menu item 3: Borrow Book
    // Searches BST -> records borrow date -> pushes to stack -> removes from BST
    // =========================================================
    @Override
    public void borrowBook(int i) {
        // Overloaded version handles actual logic
    }

    public void borrowBook(int i, int day, int month, int year) {
        Book b = catalogue.search(i);
        if (b != null) {
            Book borrowed = new Book(b.isbn, b.title, b.author);
            borrowed.setBorrowDate(day, month, year);
            history.push(borrowed);
            catalogue.remove(i);
            System.out.println("  Borrowed: [ISBN: " + b.isbn + "] " + b.title);
            System.out.println("  Borrow date  : " + String.format("%02d/%02d/%04d", day, month, year));
            System.out.println("  Please return within " + LOAN_DAYS + " days to avoid fine.");
        } else {
            System.out.println("  Book not in catalogue.");
        }
    }

    // =========================================================
    // Menu item 4: View Borrowing History (LIFO order)
    // =========================================================
    @Override
    public void viewLatestHistory() {
        history.show();
    }

    // =========================================================
    // Additional Feature 1: Return Book + Fine Calculation
    // =========================================================
    @Override
    public void returnBook(int isbn, int day, int month, int year) {
        Book borrowed = history.findByIsbn(isbn);
        if (borrowed == null) {
            System.out.println("  [Error] ISBN " + isbn + " not found in borrowing history.");
            System.out.println("         Make sure you borrowed this book first.");
            return;
        }
        int daysBorrowed = borrowed.daysUntil(day, month, year);
        if (daysBorrowed < 0) {
            System.out.println("  [Error] Return date cannot be earlier than borrow date.");
            return;
        }
        int    overdueDays = daysBorrowed - LOAN_DAYS;
        double fine        = (overdueDays > 0) ? overdueDays * FINE_RATE : 0.0;

        history.removeByIsbn(isbn);
        catalogue.insert(borrowed.isbn, borrowed.title, borrowed.author);

        System.out.println("  --- Return Summary ---");
        System.out.println("  Book     : [ISBN: " + borrowed.isbn + "] " + borrowed.title
                + " by " + borrowed.author);
        System.out.println("  Borrowed : "
                + String.format("%02d/%02d/%04d", borrowed.borrowDay, borrowed.borrowMonth, borrowed.borrowYear));
        System.out.println("  Returned : "
                + String.format("%02d/%02d/%04d", day, month, year));
        System.out.println("  Days borrowed : " + daysBorrowed + " days");
        System.out.println("  Loan period   : " + LOAN_DAYS + " days free");
        if (overdueDays > 0) {
            System.out.println("  Overdue       : " + overdueDays + " days");
            System.out.printf ("  Fine          : RM %.2f (RM %.2f/day x %d days)%n",
                    fine, FINE_RATE, overdueDays);
        } else {
            System.out.println("  Overdue       : None");
            System.out.println("  Fine          : RM 0.00 - Returned on time!");
        }
        System.out.println("  Book has been returned to the catalogue.");
        System.out.println("  ----------------------");
    }

    // =========================================================
    // Additional Feature 2: Search by Title keyword
    // =========================================================
    @Override
    public void searchByTitle(String keyword) {
        System.out.println("  --- Search Results for Title: \"" + keyword + "\" ---");
        boolean found = catalogue.searchByTitle(keyword);
        if (!found) {
            System.out.println("  No books found with title containing \"" + keyword + "\".");
        }
        System.out.println("  -------------------------------------------");
    }

    // =========================================================
    // Additional Feature 3: Search by Author keyword
    // =========================================================
    @Override
    public void searchByAuthor(String keyword) {
        System.out.println("  --- Search Results for Author: \"" + keyword + "\" ---");
        boolean found = catalogue.searchByAuthor(keyword);
        if (!found) {
            System.out.println("  No books found with author containing \"" + keyword + "\".");
        }
        System.out.println("  --------------------------------------------");
    }

    // =========================================================
    // Additional Feature 4: Display All Books (sorted by ISBN)
    // Uses BST in-order traversal — outputs books in ascending ISBN order
    // =========================================================
    @Override
    public void displayAllBooks() {
        System.out.println("  --- All Books in Catalogue (Sorted by ISBN) ---");
        boolean found = catalogue.displayAll();
        if (!found) {
            System.out.println("  Catalogue is empty. Add some books first.");
        }
        System.out.println("  -----------------------------------------------");
    }

    // =========================================================
    // Console menu loop
    // =========================================================
    public void runMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("Choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("  [Error] Please enter a number between 1 and 9.");
                sc.nextLine();
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine(); // consume leftover newline

            if (choice == 9) break;

            handleChoice(choice, sc);
        }
        System.out.println("\n  Exiting Smart Library. Goodbye!");
        sc.close();
    }

    private void printMenu() {
        System.out.println("\n=============================");
        System.out.println("     Smart Library Menu      ");
        System.out.println("=============================");
        System.out.println("  1. Add Book");
        System.out.println("  2. Search Book by ISBN");
        System.out.println("  3. Borrow Book");
        System.out.println("  4. View Borrowing History");
        System.out.println("  5. Return Book + Fine Check");
        System.out.println("  6. Search by Title");
        System.out.println("  7. Search by Author");
        System.out.println("  8. Display All Books");
        System.out.println("  9. Exit");
        System.out.println("=============================");
    }

    private void handleChoice(int choice, Scanner sc) {
        switch (choice) {

            case 1: // Add Book
                System.out.print("  Enter ISBN (up to 10 digits): ");
                int addIsbn = readIsbn(sc);
                if (addIsbn == -1) break;
                String t = readLine(sc, "  Enter Title: ");
                String a = readLine(sc, "  Enter Author: ");
                addBook(addIsbn, t, a);
                break;

            case 2: // Search by ISBN
                System.out.print("  Enter ISBN to search: ");
                int searchIsbn = readIsbn(sc);
                if (searchIsbn == -1) break;
                searchBook(searchIsbn);
                break;

            case 3: // Borrow Book
                System.out.print("  Enter ISBN to borrow: ");
                int borrowIsbn = readIsbn(sc);
                if (borrowIsbn == -1) break;
                int[] borrowDate = readDate(sc);
                if (borrowDate == null) break;
                borrowBook(borrowIsbn, borrowDate[0], borrowDate[1], borrowDate[2]);
                break;

            case 4: // View History
                viewLatestHistory();
                break;

            case 5: // Return Book
                System.out.print("  Enter ISBN to return: ");
                int returnIsbn = readIsbn(sc);
                if (returnIsbn == -1) break;
                int[] returnDate = readDate(sc);
                if (returnDate == null) break;
                returnBook(returnIsbn, returnDate[0], returnDate[1], returnDate[2]);
                break;

            case 6: // Search by Title
                String titleKeyword = readLine(sc, "  Enter title keyword: ");
                searchByTitle(titleKeyword);
                break;

            case 7: // Search by Author
                String authorKeyword = readLine(sc, "  Enter author keyword: ");
                searchByAuthor(authorKeyword);
                break;

            case 8: // Display All Books
                displayAllBooks();
                break;

            default:
                System.out.println("  [Error] Invalid option. Please choose between 1 and 9.");
        }
    }
}