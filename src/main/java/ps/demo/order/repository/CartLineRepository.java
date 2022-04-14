package ps.demo.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ps.demo.order.entity.CartLine;

import java.util.List;

/**
 * @author yunpeng.song
 */
@Repository
public interface CartLineRepository extends JpaRepository<CartLine, Long> {


}
