

package ps.demo.memreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ps.demo.memreview.entity.NoteCard;
import lombok.*;
import java.util.*;
import java.math.*;
@Repository
public interface NoteCardDao extends JpaRepository<NoteCard, Long>, JpaSpecificationExecutor {

}


