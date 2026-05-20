// BookBST.java
public class BookBST {
    private Book root;

    // --- Insert (Menu item 1: Add Book) ---
    public void insert(int isbn, String t, String a) {
        root = ins(root, isbn, t, a);
    }

    private Book ins(Book r, int i, String t, String a) {
        if (r == null) return new Book(i, t, a);
        if (i < r.isbn) {
            r.left = ins(r.left, i, t, a);
        } else if (i > r.isbn) {
            r.right = ins(r.right, i, t, a);
        } else {
            System.out.println("  [Warning] ISBN " + i + " already exists.");
        }
        return r;
    }

    // --- Search (Menu item 2: Search Book, O log n) ---
    public Book search(int i) {
        return sea(root, i);
    }

    private Book sea(Book r, int i) {
        if (r == null || r.isbn == i) return r;
        return (i < r.isbn) ? sea(r.left, i) : sea(r.right, i);
    }

    // --- Remove (used by borrowBook to delete from catalogue after borrowing) ---
    public void remove(int isbn) {
        root = del(root, isbn);
    }

    private Book del(Book r, int isbn) {
        if (r == null) return null;
        if (isbn < r.isbn) {
            r.left = del(r.left, isbn);
        } else if (isbn > r.isbn) {
            r.right = del(r.right, isbn);
        } else {
            if (r.left == null) return r.right;
            if (r.right == null) return r.left;
            Book successor = r.right;
            while (successor.left != null) successor = successor.left;
            r.isbn   = successor.isbn;
            r.title  = successor.title;
            r.author = successor.author;
            r.right  = del(r.right, successor.isbn);
        }
        return r;
    }
}