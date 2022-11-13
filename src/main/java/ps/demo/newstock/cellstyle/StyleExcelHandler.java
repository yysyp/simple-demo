package ps.demo.newstock.cellstyle;

import com.alibaba.excel.event.WriteHandler;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.text.DecimalFormat;

public class StyleExcelHandler implements WriteHandler {

    private static int time=0;
    @Override
    public void sheet(int i, Sheet sheet) {
    }

    @Override
    public void row(int i, Row row) {
    }

    @Override
    public void cell(int i, Cell cell) {
        // 从第二行开始设置格式，第一行是表头
        //这里可以获得Workbook是因为Sheet类有这个接口，但是其他地方没有对应的Sheet,所以要获得的话，需要用到反射了
        Workbook workbook = cell.getSheet().getWorkbook();
        CellStyle cellStyle = createStyle(workbook);
        //if (cell.getRowIndex() > 2) {

            String cellValue = cell.getStringCellValue();
            Double d = null;

            try {
                d = Double.parseDouble(cellValue);
            } catch (Exception e) {

            }

            if (d != null) {
//                DataFormat dataFormat = workbook.createDataFormat();
//                cellStyle.setDataFormat(dataFormat.getFormat(",###.##"));
                if (d < 0) {
                    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
                }
                cell.setCellValue(new DecimalFormat().format(d));
                cell.setCellStyle(cellStyle);
            } else if ((cellValue+"").startsWith("=")) {
                cell.setCellFormula(cellValue);
                cell.setCellStyle(cellStyle);
            } else if ((cellValue+"").trim().equals("科目\\时间")) {
                cell.setCellValue(cellValue);
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.YELLOW.getIndex());
                cell.setCellStyle(cellStyle);
            } else {
                cell.setCellValue(cellValue);
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
                cell.setCellStyle(cellStyle);
            }

            //cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);//设置前景填充样式
            //cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.DARK_RED.getIndex());//前景填充色
        //}
        //cell.getRow().getCell(i).setCellStyle(cellStyle);
    }

    /**
     * 实际中如果直接获取原单元格的样式进行修改, 最后发现是改了整行的样式, 因此这里是新建一个样* 式
     */
    private CellStyle createStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        // 下边框
        cellStyle.setBorderBottom(BorderStyle.THIN);
        // 左边框
        cellStyle.setBorderLeft(BorderStyle.THIN);
        // 上边框
        cellStyle.setBorderTop(BorderStyle.THIN);
        // 右边框
        cellStyle.setBorderRight(BorderStyle.THIN);
        // 水平对齐方式
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        // 垂直对齐方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }
}
