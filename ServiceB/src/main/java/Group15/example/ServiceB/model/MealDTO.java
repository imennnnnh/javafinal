package Group15.example.ServiceB.model;

// 這是一個 DTO (Data Transfer Object)，專門用來接 Service A 回傳的 JSON
public class MealDTO {
    private String id;
    private String name;
    private int price; 
    private String category;
    
    // 必須要有 Getters 和 Setters，WebClient 才能自動塞資料進去
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}