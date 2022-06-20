package ps.demo.util.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.*;
import org.junit.jupiter.api.parallel.Execution;

@ToString
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MyExcelModel extends BaseRowModel {

    @ExcelProperty(value = "name", index = 0)
    private String name;

    @ExcelProperty(value = "age", index = 1)
    private int age;

    @ExcelProperty(value = "score", index = 2)
    private Double score;

}
