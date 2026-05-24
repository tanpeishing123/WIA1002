// LibraryADT.java
public interface LibraryADT {
    // Original 4 methods (from PDF sample)
    void addBook(int isbn, String title, String author);
    void borrowBook(int isbn);
    void viewLatestHistory();
    void searchBook(int isbn);

    // Additional features
    void returnBook(int isbn, int day, int month, int year);
    void searchByTitle(String keyword);
    void searchByAuthor(String keyword);
    void displayAllBooks();
}