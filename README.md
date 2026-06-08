#Smart Library System

An advanced, robust, and interactive console-based library management system built using core Data Structure principles in Java. This project demonstrates the practical application of Abstract Data Types (ADTs), Binary Search Trees (BSTs), and Stack-based transaction logs to handle varying data access patterns efficiently.

Developed as a group assignment for **WIA1002 Data Structure**, Universiti Malaya.

---

## 📋 Project Specifications
- **Course Code:** WIA1002 Data Structure
- **Occurrences:** OCC11
- **Semester:** Semester 2, 2025/2026
- **Platform:** Java (Console Application)

---

## 👥 Team Members & Task Allocation
Following a robust modular programming strategy, the system logic and interfaces are divided cleanly among 5 members to enforce information hiding and complete decoupling:

| Member Name | Matric Number | Project Role | Core Responsibilities |
| :--- | :--- | :--- | :--- |
| **TAN PEI SHING** | 24220647 | **System Admin & Console Developer** | Programmed the menu-driven driver loop (`Main.java`); engineered deep input sanitization for `Scanner` inputs, and linked BST/Stack logic via `SmartLibrary.java` to support Extra Features. |
| **PENG YI** | 24225006 | **ADT Designer & System Architect** | Designed the foundational abstract interface (`LibraryADT.java`) to ensure encapsulation and information hiding across the system. |
| **SHI TAILIN** | 24209897 | **Catalogue BST Architect** | Implemented the primary Binary Search Tree structure (`BookBST.java`) including recursive insertion, $O(\\log n)$ exact ISBN searching, and node deletion algorithms. |
| **ZHONG XIANHAO** | 23072660 | **Borrowing History Manager** | Developed the transactional logging system using a custom Stack wrapper (`BorrowStack.java`), ensuring history logs follow a strict LIFO (Last-In, First-Out) order. |
| **MIAO XINYU** | 24218381 | **Entity & Fine Algorithm Developer** | Developed the core `Book.java` entity class; engineered the manual standalone date-conversion algorithm (without external libraries) to support the Fine Management System's day-difference calculations. |

---

## 🛠️ System Architecture & Data Structures

### 1. Abstract Data Type (ADT) & Encapsulation
- **Implementation:** `LibraryADT.java`
- **Concept:** Follows the "vending machine" analogy. The entire application interactions pass through a predefined interface. Internal logic structures (BST and Stack instances) are kept completely `private` within `SmartLibrary.java`. If the underlying data structure changes in the future, the terminal client layer remains completely untouched.

### 2. Binary Search Tree (BST) — Catalogue Database
- **Implementation:** `BookBST.java`
- **Access Pattern:** Logarithmic Search ($O(\log n)$ average time complexity).
- **Logic:** Books are indexed dynamically based on their unique integer ISBN keys. Left subtrees contain smaller ISBN records, while right subtrees store larger ones. Borrowing a book triggers an immediate node removal from the BST to reflect real-time book availability.

### 3. Stack — Borrowing History Log
- **Implementation:** `BorrowStack.java`
- **Access Pattern:** Last-In, First-Out (LIFO).
- **Logic:** Tracks active borrowing operations. When a book is checked out, its transaction block is pushed onto the top of the stack. When checking history, the stack reads from top-to-bottom, naturally displaying the user's most recent activity first.

---

## ✨ Advanced & Extended Features
Moving beyond basic specifications, this system incorporates highly practical, real-world utility modules:
1. **Book Return System:** Popping records off the historical transaction stack and cleanly re-inserting them back into the active BST catalogue.
2. **Fine Management System:** Automatically penalizes borrowers for overdue loans based on a 14-day free trial limit. It calculates overdue counts via a completely custom standalone date converter algorithm without using any third-party external libraries.
   - **Loan Period:** 14 Days Free
   - **Fine Rate:** RM 0.50 per overdue day
3. **Case-Insensitive Extended Search:** In-order traversal filters that allow partial string pattern matching across both `Book Title` and `Book Author` fields.
4. **Sorted Catalogue View:** Utilizes structural BST in-order traversal (`left -> node -> right`) to dynamically stream all active library inventory sorted in ascending ISBN order.

---

## 🔒 Robust Input Validation (Crash-Proof QA)
The user interface implements strict error-trapping routines to prevent run-time exceptions:
- **ISBN Integrity Constraints:** Rejects alphanumeric inputs, checks character limits (max 10 digits to comfortably fit within standard integer limits), blocks negative values, and catches duplicate values gracefully.
- **Strict String Boundaries:** Rejects blank lines or accidental whitespaces for Book Titles and Authors.
- **Date Format Sanitization:** Standardizes inputs to `DD MM YYYY`. Validates calendar constraints (Days: 1-31, Months: 1-12, Years: 2000-2100) and explicitly blocks chronological rule breaks (e.g., returning a book before its actual borrow date).
- **Menu Bounds Enforcement:** Gracefully catches out-of-range numerical choices or character strings entered into the terminal menu driver.

---

## 📂 File Directory Mapping
