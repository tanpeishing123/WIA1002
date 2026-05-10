// LibraryADT.java
public interface LibraryADT {
    
    // 1. 添加书籍 (对应菜单项 1) 
    // Add books to the library collection (corresponding to menu item 1)
    void addBook(int isbn, String title, String author);
    
    // 2. 借阅书籍 (对应菜单项 3)
   // Borrow books from the library (corresponding to menu item 3)
    void borrowBook(int isbn);
    
    // 3. 查看最新历史记录 (对应菜单项 4)
   // View the latest history of borrowed books (corresponding to menu item 4)
    void viewLatestHistory();
    
    // 4. 搜索书籍 (对应菜单项 2)
    // Search for books in the library collection (corresponding to menu item 2)
    void searchBook(int isbn);
}