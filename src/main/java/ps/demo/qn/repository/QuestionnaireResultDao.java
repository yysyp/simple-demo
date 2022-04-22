

package ps.demo.qn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ps.demo.qn.entity.QuestionnaireResult;
import lombok.*;
import java.util.*;
import java.math.*;
@Repository
public interface QuestionnaireResultDao extends JpaRepository<QuestionnaireResult, Long>, JpaSpecificationExecutor {

}


