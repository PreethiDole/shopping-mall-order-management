package shopping_mall.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import shopping_mall.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	List<OrderItem> findByOrderId(Long orderId);

}
