package ps.demo.order.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ps.demo.order.entity.NewCart;

import java.util.List;

/**
 * @author yunpeng.song
 */
@Repository
public interface NewCartRepository extends JpaRepository<NewCart, Long> {

	@Query(value = "select * from new_cart where account_id = ?1", nativeQuery = true)
	NewCart findTheCartByAccountId(long accountId);

	List<NewCart> findBySupplyCenterId(Long supplyCenterId);


}
