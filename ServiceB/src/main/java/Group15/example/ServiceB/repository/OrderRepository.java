package Group15.example.ServiceB.repository;

import Group15.example.ServiceB.model.Order;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {
    
    // 暫時用 List 模擬資料庫/檔案
    // 之後這裡要改成 File I/O (寫入 .json 檔)
    private List<Order> orderDatabase = new ArrayList<>();

    public Order save(Order order) {
        orderDatabase.add(order);
        System.out.println("訂單已存檔: " + order.getOrderId());
        return order;
    }

    public List<Order> findAll() {
        return orderDatabase;
    }
}