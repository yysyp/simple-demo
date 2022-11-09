

package ps.demo.newstock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ps.demo.newstock.entity.NewStockData;
import lombok.*;
import java.util.*;
import java.math.*;
@Repository
public interface NewStockDataDao extends JpaRepository<NewStockData, Long>, JpaSpecificationExecutor {

}


