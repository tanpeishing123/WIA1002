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
                System.out.println("  [ISBN: " + b.isbn + "] " + b.title + " by " + b.author);
            }
            System.out.println("  --------------------------------------------");
        }
    }
}