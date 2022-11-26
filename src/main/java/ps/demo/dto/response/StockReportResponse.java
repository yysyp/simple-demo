package ps.demo.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StockReportResponse extends AbsResponse {

    private List<CdbcReport> data = new ArrayList<>();

    @Getter
    @Setter
    @ToString
    public static class CdbcReport {
        private String reportType;
        private List<Map<String, String>> columns = new ArrayList<>();
        private List<Map<String, Object>> tableData = new ArrayList<>();
    }

//    @Getter
//    @Setter
//    @ToString
//    public static class Column {
//        private String Header;
//        private String accessor;
//    }
}
