package Group15.serviceA.model;

public class Meal {
    private String id;          // 餐點編號
    private String name;        // 餐點名稱
    private double price;       // 價格
    private String category;    // 分類
    private String description; // 描述
    private double averageRating;

    public Meal() {}

    public Meal(String id, String name, double price, String category, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
    }

    // Getter and Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getAverageRating() { return averageRating; }
    public void setAverageRating(double averageRating) { this.averageRating = averageRating; }
    
    
    @Override
    public String toString() {
        return String.format("[%s] %-15s | NT$%4.0f | %-6s", id, name, price, category);
    }
}