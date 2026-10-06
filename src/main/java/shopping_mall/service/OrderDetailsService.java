package shopping_mall.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import shopping_mall.OrderDetails;
import shopping_mall.OrderItem;
import shopping_mall.repository.OrderDetailsRepository;
import shopping_mall.repository.OrderItemRepository;

@Service
public class OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderDetailsService(
            OrderDetailsRepository orderDetailsRepository,
            OrderItemRepository orderItemRepository) {

        this.orderDetailsRepository = orderDetailsRepository;
        this.orderItemRepository = orderItemRepository;
    }

    // Create an order
    public OrderDetails createOrder(OrderDetails order) {
        return orderDetailsRepository.save(order);
    }

    // Get all orders
    public List<OrderDetails> getAllOrders() {
        return orderDetailsRepository.findAll();
    }

    // Get one order by ID
    public Optional<OrderDetails> getOrderById(Long id) {
        return orderDetailsRepository.findById(id);
    }

    // Delete an order
    public void deleteOrder(Long id) {
        orderDetailsRepository.deleteById(id);
    }

    // Update an order
    public OrderDetails updateOrder(Long id, OrderDetails updatedOrder) {

        Optional<OrderDetails> existingOrder =
                orderDetailsRepository.findById(id);

        if (existingOrder.isPresent()) {

            OrderDetails order = existingOrder.get();

            order.setDateOfPurchase(updatedOrder.getDateOfPurchase());
            order.setTotal(updatedOrder.getTotal());
            order.setCustomerId(updatedOrder.getCustomerId());
            order.setPaymentMode(updatedOrder.getPaymentMode());
            order.setOrderStatus(updatedOrder.getOrderStatus());
            order.setShopId(updatedOrder.getShopId());

            return orderDetailsRepository.save(order);
        }

        return null;
    }

    // Calculate and update order total
 // Calculate and update order total
    public OrderDetails updateOrderTotal(Long orderId) {

        Optional<OrderDetails> existingOrder =
                orderDetailsRepository.findById(orderId);

        if (existingOrder.isPresent()) {

            List<OrderItem> items =
                    orderItemRepository.findByOrderId(orderId);

            float total = 0.0f;

            for (OrderItem item : items) {
                if (item.getPrice() != null && item.getQuantity() != null) {
                    total += item.getPrice() * item.getQuantity();
                }
            }

            OrderDetails order = existingOrder.get();

            order.setTotal(total);

            return orderDetailsRepository.save(order);
        }

        return null;
    }
}
