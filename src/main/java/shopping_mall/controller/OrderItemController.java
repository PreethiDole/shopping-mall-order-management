package shopping_mall.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import shopping_mall.OrderItem;
import shopping_mall.service.OrderItemService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    // Create order item
    @PostMapping
    public OrderItem createOrderItem(@Valid @RequestBody OrderItem orderItem) {
        return orderItemService.createOrderItem(orderItem);
    }

    // Get all order items
    @GetMapping
    public List<OrderItem> getAllOrderItems() {
        return orderItemService.getAllOrderItems();
    }

    // Get one order item
    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long id) {

        Optional<OrderItem> orderItem =
                orderItemService.getOrderItemById(id);

        if (orderItem.isPresent()) {
            return ResponseEntity.ok(orderItem.get());
        }

        return ResponseEntity.notFound().build();
    }

    // Delete order item
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderItem(@PathVariable Long id) {

        orderItemService.deleteOrderItem(id);

        return ResponseEntity.ok("Order item deleted successfully");
    }
 // Get all items belonging to an order
    @GetMapping("/order/{orderId}")
    public List<OrderItem> getItemsByOrderId(@PathVariable Long orderId) {
        return orderItemService.getItemsByOrderId(orderId);
    }
 // Calculate total amount for an order
    @GetMapping("/order/{orderId}/total")
    public Float calculateOrderTotal(@PathVariable Long orderId) {

        return orderItemService.calculateOrderTotal(orderId);
    }
 // Update an order item
    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> updateOrderItem(
            @PathVariable Long id,
            @Valid @RequestBody OrderItem updatedOrderItem) {

        OrderItem orderItem =
                orderItemService.updateOrderItem(id, updatedOrderItem);

        if (orderItem != null) {
            return ResponseEntity.ok(orderItem);
        }

        return ResponseEntity.notFound().build();
    }
}
