package Week4.Day4;

class Book {
    int bookID;
    String title;
    boolean isBorrowed;
    boolean isReserved;

    Book(int bookID, String title) {
        this.bookID = bookID;
        this.title = title;
        this.isBorrowed = false;
        this.isReserved = false;
    }

    void borrowBook() {
        if (!isBorrowed) {
            isBorrowed = true;
            System.out.println("Book borrowed successfully.");
        } else {
            System.out.println("Book is already borrowed.");
        }
    }

    void reserveBook() {
        if (isBorrowed && !isReserved) {
            isReserved = true;
            System.out.println("Book reserved successfully.");
        } else {
            System.out.println("Book cannot be reserved.");
        }
    }

    void returnBook() {
        isBorrowed = false;
        if (isReserved) {
            isReserved = false;
            System.out.println("Book returned and reservation cleared.");
        } else {
            System.out.println("Book returned and available.");
        }
    }

    void displayBookDetails() {
        System.out.println("Book ID: " + bookID);
        System.out.println("Title: " + title);
        System.out.println("Borrowed: " + isBorrowed);
        System.out.println("Reserved: " + isReserved);
    }
}

public class LibraryMain {
    public static void main(String[] args) {
        Book b1 = new Book(301, "Java Basics");

        b1.borrowBook();
        b1.reserveBook();
        b1.returnBook();
        b1.displayBookDetails();
    }
}
