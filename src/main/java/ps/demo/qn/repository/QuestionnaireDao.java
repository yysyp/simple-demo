

package ps.demo.qn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ps.demo.qn.entity.Questionnaire;
import lombok.*;
import java.util.*;
import java.math.*;
@Repository
public interface QuestionnaireDao extends JpaRepository<Questionnaire, Long>, JpaSpecificationExecutor {

}


