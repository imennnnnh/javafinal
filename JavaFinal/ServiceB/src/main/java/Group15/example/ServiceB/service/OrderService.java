package Group15.example.ServiceB.service;

import Group15.example.ServiceB.model.Order;
import Group15.example.ServiceB.model.OrderItem;
import Group15.example.ServiceB.model.OrderRequest;
import Group15.example.ServiceB.model.MealDTO;
import Group15.example.ServiceB.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient serviceAClient;

    // 建構子注入 (Constructor Injection)
    public OrderService(OrderRepository orderRepository, WebClient serviceAClient) {
        this.orderRepository = orderRepository;
        this.serviceAClient = serviceAClient;
    }

    public Order createOrder(OrderRequest request) {
        // 1. 建立訂單基本資料
        Order newOrder = new Order();
        newOrder.setOrderId("ORD-" + System.currentTimeMillis());
        newOrder.setCustomerName(request.getCustomerName());
        newOrder.setStatus("PROCESSING");

        List<OrderItem> orderItems = new ArrayList<>();
        
        // 2. 處理每一個點餐項目 (使用迴圈處理)
        for (OrderRequest.ItemRequest itemReq : request.getItems()) {
            
            // --- 關鍵：使用 WebClient 呼叫 Service A 查價格 ---
            // 假設 Service A 的 API 是 GET /meals/{id}
            // 回傳格式假設是: {"id":"M001", "name":"珍奶", "price":50.0}
            
            double price = 0.0;
            String mealName = "未知餐點";

            try {
                // 呼叫 Service A 的 API (注意路徑變了！)
                MealDTO mealDto = serviceAClient
                        .get()
                        .uri("/api/menu/" + itemReq.getMealId()) // 改成 Service A 真正的路徑
                        .retrieve()
                        .bodyToMono(MealDTO.class)
                        .block();

                if (mealDto != null) {
                    mealName = mealDto.getName();
                    price = (double) mealDto.getPrice();
                }
            } catch (Exception e) {
            	System.err.println("連線 Service A 失敗 ID: " + itemReq.getMealId() + ", 錯誤: " + e.getMessage());
            }

            // 3. 建立 OrderItem
            OrderItem item = new OrderItem();
            item.setMealId(itemReq.getMealId());
            item.setMealName(mealName);
            item.setPrice(price);
            item.setQuantity(itemReq.getQuantity());
            
            orderItems.add(item);
        }

        newOrder.setItems(orderItems);

        // 4. 使用 Stream API 計算總金額 
        double totalAmount = orderItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        
        newOrder.setTotalAmount(totalAmount);

        // 5. 存檔
        return orderRepository.save(newOrder);
    }
    //查詢所有訂單
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}