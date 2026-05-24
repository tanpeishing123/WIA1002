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
            System.out.println("  [Warning] ISBN " + i + " already exists in catalogue.");
        }
        return r;
    }

    // --- Search by ISBN (Menu item 2: Search Book, O log n) ---
    public Book search(int i) {
        return sea(root, i);
    }

    private Book sea(Book r, int i) {
        if (r == null || r.isbn == i) return r;
        return (i < r.isbn) ? sea(r.left, i) : sea(r.right, i);
    }

    // --- Search by Title keyword (Additional Feature) ---
    // In-order traversal — checks every node for partial title match (case-insensitive)
    public boolean searchByTitle(String keyword) {
        return searchTitleInOrder(root, keyword.toLowerCase());
    }

    private boolean searchTitleInOrder(Book r, String keyword) {
        if (r == null) return false;
        boolean found = false;
        found |= searchTitleInOrder(r.left, keyword);
        if (r.title.toLowerCase().contains(keyword)) {
            System.out.println("  [ISBN: " + r.isbn + "] " + r.title + " by " + r.author);
            found = true;
        }
        found |= searchTitleInOrder(r.right, keyword);
        return found;
    }

    // --- Search by Author keyword (Additional Feature) ---
    // In-order traversal — checks every node for partial author match (case-insensitive)
    public boolean searchByAuthor(String keyword) {
        return searchAuthorInOrder(root, keyword.toLowerCase());
    }

    private boolean searchAuthorInOrder(Book r, String keyword) {
        if (r == null) return false;
        boolean found = false;
        found |= searchAuthorInOrder(r.left, keyword);
        if (r.author.toLowerCase().contains(keyword)) {
            System.out.println("  [ISBN: " + r.isbn + "] " + r.title + " by " + r.author);
            found = true;
        }
        found |= searchAuthorInOrder(r.right, keyword);
        return found;
    }

    // --- Display All Books in ascending ISBN order (Additional Feature) ---
    // In-order traversal (left -> node -> right) always produces ascending ISBN order
    // because BST stores smaller ISBNs on the left and larger on the right
    public boolean displayAll() {
        return inOrder(root);
    }

    private boolean inOrder(Book r) {
        if (r == null) return false;
        boolean found = false;
        found |= inOrder(r.left);
        System.out.println("  [ISBN: " + r.isbn + "] " + r.title + " by " + r.author);
        found = true;
        inOrder(r.right);
        return found;
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