package Group15.example.ServiceB.model;

import java.util.List;

// DTO: 用來接收前端傳來的原始資料
public class OrderRequest {
    
    private String customerName;       // 顧客是誰
    private List<ItemRequest> items;   // 他點了什麼 (只包含 ID 和 數量)

    // 為了方便，我們把「點餐項目」直接定義在這裡面的內部類別 (Inner Class)
    // 因為前端傳來的項目只有 ID 跟數量，跟完整的 OrderItem 不一樣
    public static class ItemRequest {
        private String mealId;  // 餐點 ID (例如: "M001")
        private int quantity;   // 數量 (例如: 2)

        // Getters and Setters
        public String getMealId() { return mealId; }
        public void setMealId(String mealId) { this.mealId = mealId; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }

    // OrderRequest 的 Getters and Setters
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public List<ItemRequest> getItems() { return items; }
    public void setItems(List<ItemRequest> items) { this.items = items; }
}