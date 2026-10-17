package Group15.example.ServiceB.model; // 根據你實際的 package 修改

import java.util.List;
import java.util.ArrayList;

// 這是你的 Model (資料物件)
public class Order {
    
    private String orderId;       // 訂單編號
    private String customerName;  // 顧客姓名
    private List<OrderItem> items;// 訂單項目 (包含多個餐點)
    private double totalAmount;   // 總金額 (之後要計算)
    private String status;        // 狀態 (如: PENDING, COMPLETED)

    // 1. 無參數建構子 (Spring Boot 或是 JSON 轉換時常需要)
    public Order() {
        this.items = new ArrayList<>();
    }

    // 2. 有參數建構子 (方便你寫 Mock Data 時快速建立物件)
    public Order(String orderId, String customerName, List<OrderItem> items) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.items = items;
        this.status = "PENDING"; // 預設狀態
    }

    // 3. Getters and Setters (必備！Spring Boot 轉換 JSON 需要這些)
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}