

package ps.demo.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ps.demo.school.entity.Student;
import lombok.*;
import java.util.*;
import java.math.*;
@Repository
public interface StudentDao extends JpaRepository<Student, Long>, JpaSpecificationExecutor {

}


