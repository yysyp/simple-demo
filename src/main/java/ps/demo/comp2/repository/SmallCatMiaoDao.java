

package ps.demo.comp2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ps.demo.comp2.entity.SmallCatMiao;
import lombok.*;
import java.util.*;
import java.math.*;
@Repository
public interface SmallCatMiaoDao extends JpaRepository<SmallCatMiao, Long>, JpaSpecificationExecutor {

}


