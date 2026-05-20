# Smart Library Project — Full Code Explanation & Marking Guide
 
---
 
## Project Overview
 
This project implements a **university Smart Library system** using two core data structures:
- A **Binary Search Tree (BST)** — stores all available books, searchable by ISBN in O(log n) time
- A **Stack** — records borrowing history in LIFO order (most recently borrowed shown first)
The program runs as a **menu-driven console application** that a librarian or student can use without modifying any source code.

## File Structure
 
```
SmartLibrary/
├── LibraryADT.java     → Interface (ADT contract)
├── Book.java           → Data entity / BST node
├── BookBST.java        → Binary Search Tree logic
├── BorrowStack.java    → Stack history logic
├── SmartLibrary.java   → Main logic + console menu
└── Main.java           → Entry point (runs the program)
```
 
---
 
## How to Compile and Run
 
```bash
# Step 1: Compile all files
javac *.java
 
# Step 2: Run the program
java Main
```
 
---
 