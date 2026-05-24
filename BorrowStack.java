// BorrowStack.java
// Records borrowing history using a Stack (LIFO order).
import java.util.Stack;

public class BorrowStack {
    private Stack<Book> stack = new Stack<>();

    // --- Push borrowed book onto stack (Menu item 3: Borrow Book) ---
    public void push(Book b) {
        stack.push(b);
    }

    // --- Show history in LIFO order (Menu item 4: View History) ---
    public void show() {
        if (stack.isEmpty()) {
            System.out.println("  History is empty.");
        } else {
            System.out.println("  --- Borrowing History (Most Recent First) ---");
            for (int i = stack.size() - 1; i >= 0; i--) {
                Book b = stack.get(i);
                System.out.println("  [ISBN: " + b.isbn + "] " + b.title + " by " + b.author
                        + " | Borrowed: " + formatDate(b.borrowDay, b.borrowMonth, b.borrowYear));
            }
            System.out.println("  --------------------------------------------");
        }
    }

    // --- Find a borrowed book in the stack by ISBN (used by returnBook) ---
    // Returns the Book object if found, null if not in stack
    public Book findByIsbn(int isbn) {
        for (int i = stack.size() - 1; i >= 0; i--) {
            if (stack.get(i).isbn == isbn) {
                return stack.get(i);
            }
        }
        return null;
    }

    // --- Remove a book from the stack by ISBN (used when returning a book) ---
    public boolean removeByIsbn(int isbn) {
        for (int i = stack.size() - 1; i >= 0; i--) {
            if (stack.get(i).isbn == isbn) {
                stack.remove(i);
                return true;
            }
        }
        return false;
    }

    // --- Helper: format date as DD/MM/YYYY ---
    private String formatDate(int d, int m, int y) {
        if (d == 0) return "N/A";
        return String.format("%02d/%02d/%04d", d, m, y);
    }

    // --- Check if stack is empty ---
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}