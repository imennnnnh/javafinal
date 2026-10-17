package Group15.example.ServiceB.controller;

import Group15.example.ServiceB.model.Order;
import Group15.example.ServiceB.model.OrderRequest;
import Group15.example.ServiceB.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;


import java.util.List;
@CrossOrigin(origins = "*")
@RestController
public class OrderController {

    private final OrderService orderService;

    // 建構子注入 Service
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    // 1. 新增訂單 (POST)
    @PostMapping("/orders")
    public Order createOrder(@RequestBody OrderRequest request) {
        // 現在 Controller 變得很乾淨，只負責轉發工作給 Service
        return orderService.createOrder(request);
    }
    
    // 2. 查詢所有訂單 (GET) 
    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders(); 
    }
}