

package ps.demo.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ps.demo.account.entity.LoginUser;
import lombok.*;
import java.util.*;
import java.math.*;
@Repository
public interface LoginUserDao extends JpaRepository<LoginUser, Long>, JpaSpecificationExecutor {

}


