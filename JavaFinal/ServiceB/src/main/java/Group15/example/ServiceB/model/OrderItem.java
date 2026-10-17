package Group15.example.ServiceB.model;

public class OrderItem {
    
    private String mealId;    // 餐點 ID (用這個去問 Service A)
    private String mealName;  // 餐點名稱
    private int quantity;     // 數量
    private double price;     // 單價

    public OrderItem() {}

    public OrderItem(String mealId, String mealName, int quantity, double price) {
        this.mealId = mealId;
        this.mealName = mealName;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public String getMealId() { return mealId; }
    public void setMealId(String mealId) { this.mealId = mealId; }

    public String getMealName() { return mealName; }
    public void setMealName(String mealName) { this.mealName = mealName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
