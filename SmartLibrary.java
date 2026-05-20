// SmartLibrary.java
import java.util.Scanner;

public class SmartLibrary implements LibraryADT {

    // PDF page 4: private fields for Information Hiding
    private BookBST     catalogue = new BookBST();
    private BorrowStack history   = new BorrowStack();

    // =========================================================
    // Private helper: validate and read an ISBN from console.
    // Returns -1 if input is invalid (caller should break/skip).
    // =========================================================
    private int readIsbn(Scanner sc) {
        if (!sc.hasNextLine()) {
            System.out.println("  [Error] ISBN is required.");
            return -1;
        }

        String token = sc.nextLine().trim();
        if (!token.matches("\\d+")) {
            System.out.println("  [Error] ISBN must contain only digits.");
            return -1;
        }

        if (token.length() > 10) {
            System.out.println("  [Error] ISBN must be at most 10 digits.");
            return -1;
        }

        int isbn;
        try {
            isbn = Integer.parseInt(token);
        } catch (NumberFormatException e) {
            System.out.println("  [Error] ISBN numeric value is too large for int.");
            return -1;
        }

        return isbn;
    }

    // --- Menu item 1: Add Book ---
    @Override
    public void addBook(int i, String t, String a) {
        catalogue.insert(i, t, a);
        System.out.println("  Book added: [ISBN: " + i + "] " + t + " by " + a);
    }

    // --- Menu item 2: Search Book (BST, O log n) ---
    @Override
    public void searchBook(int i) {
        Book b = catalogue.search(i);
        if (b != null) {
            System.out.println("  Found: [ISBN: " + b.isbn + "] " + b.title + " by " + b.author);
        } else {
            System.out.println("  Not Found.");
        }
    }

    // --- Menu item 3: Borrow Book (search BST -> push stack -> remove from BST) ---
    @Override
    public void borrowBook(int i) {
        Book b = catalogue.search(i);
        if (b != null) {
            history.push(new Book(b.isbn, b.title, b.author));
            catalogue.remove(i);
            System.out.println("  Borrowed: [ISBN: " + b.isbn + "] " + b.title);
        } else {
            System.out.println("  Book not in catalogue.");
        }
    }

    // --- Menu item 4: View History (LIFO order) ---
    @Override
    public void viewLatestHistory() {
        history.show();
    }

    // --- Console menu loop ---
    public void runMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("Choice: ");

            // Input validation: non-integer menu choice
            if (!sc.hasNextInt()) {
                System.out.println("  [Error] Please enter a number between 1 and 5.");
                sc.nextLine(); 
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine();
            if (choice == 5) break;

            handleChoice(choice, sc);
        }
        System.out.println("\n  Exiting Smart Library. Goodbye!");
        sc.close();
    }

    private void printMenu() {
        System.out.println("\n--- SmartLibrary Menu ---");
        System.out.println("1. Add Book");
        System.out.println("2. Search Book");
        System.out.println("3. Borrow Book");
        System.out.println("4. History");
        System.out.println("5. Exit");
    }

    private void handleChoice(int choice, Scanner sc) {
        switch (choice) {
            case 1:
                System.out.print("  Enter ISBN (up to 10 digits): ");
                int addIsbn = readIsbn(sc);
                if (addIsbn == -1) break;

                System.out.print("  Enter Title: ");
                String t = sc.nextLine(); 
                
                System.out.print("  Enter Author: ");
                String a = sc.nextLine(); 
                
                addBook(addIsbn, t, a);
                break;
            case 2:
                System.out.print("  Enter ISBN to search (up to 10 digits): ");
                int searchIsbn = readIsbn(sc);
                if (searchIsbn == -1) break;
                searchBook(searchIsbn);
                break;
            case 3:
                System.out.print("  Enter ISBN to borrow (up to 10 digits): ");
                int borrowIsbn = readIsbn(sc);
                if (borrowIsbn == -1) break;
                borrowBook(borrowIsbn);
                break;
            case 4:
                viewLatestHistory();
                break;
            default:
                System.out.println("  [Error] Invalid option. Please choose between 1 and 5.");
        }
    }
}