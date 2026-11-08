import java.math.BigDecimal;

public class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private BigDecimal price;
    private int quantity;

    public Book(int id, String title, String author, String isbn, BigDecimal price, int quantity) {
        this.id = id;
        setTitle(title);
        setAuthor(author);
        setIsbn(isbn);
        setPrice(price);
        setQuantity(quantity);
    }

    public Book(String title, String author, String isbn, BigDecimal price, int quantity) {
        this(0, title, author, isbn, price, quantity);
    }

    // ---- Validation lives in the setters ----
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty())
            throw new IllegalArgumentException("Title cannot be empty.");
        if (title.trim().length() > 200)
            throw new IllegalArgumentException("Title must be 200 characters or fewer.");
        this.title = title.trim();
    }

    public void setAuthor(String author) {
        if (author == null || author.trim().isEmpty())
            throw new IllegalArgumentException("Author cannot be empty.");
        if (author.trim().length() > 100)
            throw new IllegalArgumentException("Author must be 100 characters or fewer.");
        this.author = author.trim();
    }

    public void setIsbn(String isbn) {
        if (isbn == null) throw new IllegalArgumentException("ISBN cannot be empty.");
        String cleaned = isbn.replaceAll("[\\s-]", "");
        if (!cleaned.matches("\\d{10}|\\d{13}"))
            throw new IllegalArgumentException("ISBN must be 10 or 13 digits.");
        this.isbn = cleaned;
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.signum() < 0)
            throw new IllegalArgumentException("Price cannot be negative.");
        if (price.scale() > 2)
            throw new IllegalArgumentException("Price can have at most 2 decimal places.");
        this.price = price;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0)
            throw new IllegalArgumentException("Quantity cannot be negative.");
        this.quantity = quantity;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public BigDecimal getPrice() { return price; }
    public int getQuantity() { return quantity; }

    @Override
    public String toString() {
        return String.format("%-4d %-30.30s %-20.20s %-14s %8s %5d",
                id, title, author, isbn, price, quantity);
    }
}

