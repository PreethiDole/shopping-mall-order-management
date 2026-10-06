package shopping_mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shopping_mall.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

}
