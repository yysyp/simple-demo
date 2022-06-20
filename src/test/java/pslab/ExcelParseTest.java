package pslab;

import com.alibaba.excel.metadata.Sheet;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import ps.demo.util.MyConvertUtil;
import ps.demo.util.MyExcelUtil;
import ps.demo.util.MyFileUtil;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelParseTest {

    public static void main(String[] args) {

        Sheet sheet4 = new Sheet(5);
        List<Object> table = MyExcelUtil.readMoreThan1000RowBySheet("C:\\Users\\Dell\\应收款-0619导.xlsx", sheet4);
        List<RecMod> recMods = new ArrayList<>();
        for (int i = 0, n = table.size(); i < n; i++) {
            if (i == 0) {
                continue;
            }
            List<String> line = (List<String>) table.get(i);
            RecMod r = RecMod.fromArray(line);
            recMods.add(r);
            //System.out.println("line:" + i + ": " + r);

        }


        //Grouping By with customized grouping keys and after grouping logic
        Map<String, List<BigDecimal>> grouped = recMods.stream().collect(Collectors.groupingBy(
                e -> {
                    return e.getSeq() + e.getName();
                },
                Collectors.collectingAndThen(Collectors.toList(), list -> {
                    ArrayList list1 = new ArrayList();
                    BigDecimal sum = list.stream().map(RecMod::getJq).reduce(BigDecimal.ZERO, BigDecimal::add);
                    list1.add(sum);
                    sum = list.stream().map(RecMod::getHc).reduce(BigDecimal.ZERO, BigDecimal::add);
                    list1.add(sum);
                    sum = list.stream().map(RecMod::getDyfw).reduce(BigDecimal.ZERO, BigDecimal::add);
                    list1.add(sum);
                    sum = list.stream().map(RecMod::getFzpz).reduce(BigDecimal.ZERO, BigDecimal::add);
                    list1.add(sum);
                    sum = list.stream().map(RecMod::getJsfw).reduce(BigDecimal.ZERO, BigDecimal::add);
                    list1.add(sum);
                    sum = list.stream().map(RecMod::getWsk).reduce(BigDecimal.ZERO, BigDecimal::add);
                    list1.add(sum);

                    return list1;
                })));


        //System.out.println(grouped);
        //printMap(grouped);
        List<RecMod> result = new ArrayList<>();
        Set<String> removeDuplicatedKey = new HashSet<>();
        for (RecMod recMod : recMods) {
            if (removeDuplicatedKey.contains(recMod.getSeq() + recMod.getName())) {
                continue;
            }
            removeDuplicatedKey.add(recMod.getSeq() + recMod.getName());
            result.add(recMod);
            List<BigDecimal> sumList = grouped.get(recMod.getSeq() + recMod.getName());
            recMod.setJq(sumList.get(0));
            recMod.setHc(sumList.get(1));
            recMod.setDyfw(sumList.get(2));
            recMod.setFzpz(sumList.get(3));
            recMod.setJsfw(sumList.get(4));
            recMod.setWsk(sumList.get(5));
        }

        //System.out.println(result);
        List<List<Object>> resultExcel = new ArrayList<>();
        for (RecMod recMod : result) {
            if (recMod.wsk.compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }
            resultExcel.add(recMod.toArray());
        }


        MyExcelUtil.writeBySimple(MyFileUtil.getFileTsInHomeDir("sum.xlsx").toString(), resultExcel, "1", "2", "3", "4", "5");


    }

    public static void printMap(Map<String, List<BigDecimal>> grouped) {
        for (Map.Entry<String, List<BigDecimal>> entry : grouped.entrySet()) {
            String key = entry.getKey();
            List<BigDecimal> value = entry.getValue();
            System.out.println("key=" + key);
            System.out.println("value=" + value);
        }
    }


    @ToString
    @Getter
    @Setter
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    static class RecMod {

        private String code;
        private String seq;
        private String name;
        private String salesDate;
        private String sffh;
        private String sfkp;
        private BigDecimal jq;
        private BigDecimal hc;
        private BigDecimal dyfw;
        private BigDecimal fzpz;
        private BigDecimal jsfw;
        private BigDecimal wsk;
        private BigDecimal dqwsk;

        public static RecMod fromArray(List<String> line) {
            String regularSeq = StringUtils.substringBefore(line.get(1) + "", "-");
            return new RecModBuilder().code(line.get(0))
                    .seq(regularSeq.trim())
                    .name((line.get(2) + "").trim())
                    .salesDate(line.get(3))
                    .sffh(line.get(4))
                    .sfkp(line.get(5))
                    .jq(MyConvertUtil.getAsDecimal(line, 6))
                    .hc(MyConvertUtil.getAsDecimal(line, 7))
                    .dyfw(MyConvertUtil.getAsDecimal(line, 8))
                    .fzpz(MyConvertUtil.getAsDecimal(line, 9))
                    .jsfw(MyConvertUtil.getAsDecimal(line, 10))
                    .wsk(MyConvertUtil.getAsDecimal(line, 11))
                    .dqwsk(MyConvertUtil.getAsDecimal(line, 12)).build();
        }

        public List<Object> toArray() {
            List<Object> line = new ArrayList<>();
            line.add(this.code);
            line.add(this.seq);
            line.add(this.name);
            line.add(this.salesDate);
            line.add(this.sffh);
            line.add(this.sfkp);
            line.add(MyConvertUtil.decimalToString(this.jq));
            line.add(MyConvertUtil.decimalToString(this.hc));
            line.add(MyConvertUtil.decimalToString(this.dyfw));
            line.add(MyConvertUtil.decimalToString(this.fzpz));
            line.add(MyConvertUtil.decimalToString(this.jsfw));
            line.add(MyConvertUtil.decimalToString(this.wsk));
            return line;
        }
    }
}
