

package ps.demo.newstock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ps.demo.newstock.entity.NewStockData;
import lombok.*;
import java.util.*;
import java.math.*;
@Repository
public interface NewStockDataDao extends JpaRepository<NewStockData, Long>, JpaSpecificationExecutor {

    public List<NewStockData> findByCompanyCodeAndPeriodYearAndPeriodMonthAndKemu(String companyCode,
                                                                               Integer year,
                                                                               Integer month,
                                                                               String kemu);

    List<NewStockData> findByCompanyCodeAndPeriodMonthAndKemuOrderByPeriodYearAsc(String companyCode,
                                                                                  Integer month,
                                                                                  String kemu);

    public List<NewStockData> findByCompanyCodeAndPeriodYearAndPeriodMonthAndYoy(String companyCode,
                                                                                  Integer year,
                                                                                  Integer month,
                                                                                 BigDecimal yoy);

    public List<NewStockData> findByCompanyCodeAndPeriodYearAndPeriodMonthOrderByIdAsc(String companyCode,
                                                                                 Integer year,
                                                                                 Integer month);

    public boolean existsByCompanyCodeAndPeriodYearAndPeriodMonthAndKemuType(
            String companyCode,
            Integer year,
            Integer month,
            String kemuType);


    @Modifying
    @Query("delete from NewStockData t where t.companyCode=:companyCode")
    public void deleteByCompanyCode(@Param("companyCode") String companyCode);

}


