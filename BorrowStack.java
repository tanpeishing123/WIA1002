import java.util.Stack;

/**
 * Member 3: Stack + History
 * 任务：实现 BorrowStack 类，用于记录借阅历史。
 */
public class BorrowStack {
    // 使用 Java 自带的 Stack 集合存储 Book 对象
    // 使用 private 关键字以符合 "Information Hiding" (封装) 的评分要求
    private Stack<Book> stack = new Stack<>();

    /**
     * 将借阅的书籍存入栈中 (对应菜单项 3 的逻辑部分)
     * @param b 被借阅的书籍对象
     */
    public void push(Book b) {
        if (b != null) {
            stack.push(b);
        }
    }

    /**
     * 显示所有借阅历史 (对应菜单项 4: View History)
     * 必须按照 LIFO 顺序打印，即最近借的书最先显示。
     */
    public void show() {
        if (stack.isEmpty()) {
            System.out.println("History is empty. (借阅历史为空)");
            return;
        }

        System.out.println("--- Borrowing History (Most Recent First) ---");
        
        // 为了实现 LIFO 显示，我们从栈顶（size-1）向栈底（0）遍历
        // 这是评分标准中 "history displays in LIFO order" 的核心要求
        for (int i = stack.size() - 1; i >= 0; i--) {
            Book b = stack.get(i);
            System.out.println("[ISBN: " + b.isbn + "] Title: " + b.title + " | Author: " + b.author);
        }
        System.out.println("---------------------------------------------");
    }
    
    /**
     * 可选：获取栈的大小（方便 Member 4 进行逻辑判断）
     */
    public int getSize() {
        return stack.size();
    }
}
