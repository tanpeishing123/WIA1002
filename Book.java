// Book.java
public class Book {
    // 1. 数据属性 (Data Fields)
    int isbn;
    String title, author;
    
    // 2. 树节点指针 (Tree Node Pointers)
    Book left, right; 

    // 3. 构造函数 (Constructor)
    public Book(int isbn, String title, String author) {
        // 存储书籍的基本信息 （Store information of books)
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        
        // 显式地将左右子节点初始化为 null (表示这目前是一个独立的叶子节点)
        // (Initialize left and right child pointers to null, indicating this is currently a standalone leaf node)
        this.left = null;
        this.right = null;
    }
}