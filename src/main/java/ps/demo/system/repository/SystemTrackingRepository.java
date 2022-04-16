package ps.demo.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ps.demo.system.entity.SystemTracking;

@Repository
public interface SystemTrackingRepository extends JpaRepository<SystemTracking, Long> {

}
