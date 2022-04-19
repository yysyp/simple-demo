

package ps.demo.company1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ps.demo.company1.entity.BigDog;
import lombok.*;
import java.util.*;
import java.math.*;
@Repository
public interface BigDogDao extends JpaRepository<BigDog, Long>, JpaSpecificationExecutor {

}


