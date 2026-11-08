import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner in = new Scanner(System.in);
    private static final BookDAO dao = new BookDAO();
    private static final int LOW_STOCK_THRESHOLD = 5;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Book Inventory System (MySQL) ===");
            System.out.println("1. Add book");
            System.out.println("2. View all books");
            System.out.println("3. Find book by ID");
            System.out.println("4. Search by title/author");
            System.out.println("5. Update book");
            System.out.println("6. Delete book");
            System.out.println("7. Low-stock report (<= " + LOW_STOCK_THRESHOLD + ")");
            System.out.println("0. Exit");
            int choice = readInt("Choose: ");

            try {
                switch (choice) {
                    case 1 -> add();
                    case 2 -> show(dao.getAllBooks());
                    case 3 -> findOne();
                    case 4 -> show(dao.search(readLine("Keyword: ").trim()));
                    case 5 -> update();
                    case 6 -> delete();
                    case 7 -> show(dao.getLowStock(LOW_STOCK_THRESHOLD));
                    case 0 -> { System.out.println("Goodbye!"); return; }
                    default -> System.out.println("Invalid option.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Validation error: " + e.getMessage());
            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("A book with that ISBN already exists.");
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }
    }

    private static void add() throws SQLException {
        Book b = new Book(readLine("Title: "), readLine("Author: "), readLine("ISBN (10 or 13 digits): "),
                readPrice("Price: "), readInt("Quantity: "));
        int id = dao.addBook(b);
        System.out.println("Book added with ID " + id);
    }

    private static void findOne() throws SQLException {
        Book b = dao.getBookById(readInt("ID: "));
        System.out.println(b == null ? "No book with that ID." : b);
    }

    private static void update() throws SQLException {
        int id = readInt("ID to update: ");
        if (dao.getBookById(id) == null) { System.out.println("No book with that ID."); return; }
        Book b = new Book(id, readLine("New title: "), readLine("New author: "),
                readLine("New ISBN: "), readPrice("New price: "), readInt("New quantity: "));
        System.out.println(dao.updateBook(b) ? "Updated." : "Update failed.");
    }

    private static void delete() throws SQLException {
        int id = readInt("ID to delete: ");
        System.out.println(dao.deleteBook(id) ? "Deleted." : "No book with that ID.");
    }

    private static void show(List<Book> books) {
        if (books.isEmpty()) { System.out.println("No books found."); return; }
        System.out.printf("%-4s %-30s %-20s %-14s %8s %5s%n", "ID", "Title", "Author", "ISBN", "Price", "Qty");
        books.forEach(System.out::println);
    }

    // ---- input helpers (never crash on bad input) ----
    private static String readLine(String prompt) {
        System.out.print(prompt);
        return in.nextLine();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(in.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static BigDecimal readPrice(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return new BigDecimal(in.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid price, e.g. 19.99");
            }
        }
    }
}
