package shopping_mall.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import shopping_mall.OrderDetails;
import shopping_mall.service.OrderDetailsService;

@RestController
@RequestMapping("/api/orders")
public class OrderDetailsController {

    private final OrderDetailsService orderDetailsService;

    public OrderDetailsController(OrderDetailsService orderDetailsService) {
        this.orderDetailsService = orderDetailsService;
    }

    // Create a new order
    @PostMapping
    public OrderDetails createOrder(
            @Valid @RequestBody OrderDetails order) {

        return orderDetailsService.createOrder(order);
    }

    // Get all orders
    @GetMapping
    public List<OrderDetails> getAllOrders() {
        return orderDetailsService.getAllOrders();
    }

    // Get one order
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetails> getOrderById(
            @PathVariable Long id) {

        Optional<OrderDetails> order =
                orderDetailsService.getOrderById(id);

        if (order.isPresent()) {
            return ResponseEntity.ok(order.get());
        }

        return ResponseEntity.notFound().build();
    }

    // Delete an order
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(
            @PathVariable Long id) {

        orderDetailsService.deleteOrder(id);

        return ResponseEntity.ok("Order deleted successfully");
    }

    // Update an existing order
    @PutMapping("/{id}")
    public ResponseEntity<OrderDetails> updateOrder(
            @PathVariable Long id,
            @RequestBody OrderDetails updatedOrder) {

        OrderDetails order =
                orderDetailsService.updateOrder(id, updatedOrder);

        if (order != null) {
            return ResponseEntity.ok(order);
        }

        return ResponseEntity.notFound().build();
    }

    // Calculate and update order total
    @PutMapping("/{id}/calculate-total")
    public ResponseEntity<OrderDetails> calculateOrderTotal(
            @PathVariable Long id) {

        OrderDetails order =
                orderDetailsService.updateOrderTotal(id);

        if (order != null) {
            return ResponseEntity.ok(order);
        }

        return ResponseEntity.notFound().build();
    }
}
