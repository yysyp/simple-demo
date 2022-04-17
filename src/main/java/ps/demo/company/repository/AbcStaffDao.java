

package ps.demo.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ps.demo.company.entity.AbcStaff;
import lombok.*;
import java.util.*;
import java.math.*;
@Repository
public interface AbcStaffDao extends JpaRepository<AbcStaff, Long>, JpaSpecificationExecutor {

}


