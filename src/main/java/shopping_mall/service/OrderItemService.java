package shopping_mall.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import shopping_mall.OrderItem;
import shopping_mall.repository.OrderItemRepository;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    // Create order item
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    // Get all order items
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    // Get order item by ID
    public Optional<OrderItem> getOrderItemById(Long id) {
        return orderItemRepository.findById(id);
    }

    // Delete order item
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }
 // Get all items for an order
    public List<OrderItem> getItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }
 // Calculate total for an order
    public Float calculateOrderTotal(Long orderId) {

        List<OrderItem> items =
                orderItemRepository.findByOrderId(orderId);

        float total = 0;

        for (OrderItem item : items) {
            total += item.getPrice() * item.getQuantity();
        }

        return total;
    }
 // Update an order item
    public OrderItem updateOrderItem(Long id, OrderItem updatedOrderItem) {

        Optional<OrderItem> existingOrderItem =
                orderItemRepository.findById(id);

        if (existingOrderItem.isPresent()) {

            OrderItem orderItem = existingOrderItem.get();

            orderItem.setOrderId(updatedOrderItem.getOrderId());
            orderItem.setProductId(updatedOrderItem.getProductId());
            orderItem.setQuantity(updatedOrderItem.getQuantity());
            orderItem.setPrice(updatedOrderItem.getPrice());

            return orderItemRepository.save(orderItem);
        }

        return null;
    }
}
