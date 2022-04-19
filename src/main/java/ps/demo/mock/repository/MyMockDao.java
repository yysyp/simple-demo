

package ps.demo.mock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ps.demo.mock.entity.MyMock;
import lombok.*;
import java.util.*;
import java.math.*;
@Repository
public interface MyMockDao extends JpaRepository<MyMock, Long>, JpaSpecificationExecutor {

}


