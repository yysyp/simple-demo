package ps.demo.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**<code>
List<MyExcelModel> data = new ArrayList<>();
for (int i = 0; i < 10; i++) {
    MyExcelModel row = new MyExcelModel.MyExcelModelBuilder()
    .name("haha-name" + i)
    .age(new Random().nextInt(100))
    .score(new Random().nextDouble() * 100).build();
    data.add(row);
}
MyExcelUtil.writeBySimple(MyFileUtil.getFileTsInHomeDir("myexcel.xlsx"), data);


System.out.println("--->>Excel read from myExcelFile=" + myExcelFile);
List<MyExcelModel> list3 =  MyExcelUtil.readMoreThan1000Row(myExcelFile, 1, 1, MyExcelModel.class);
int j = 0;
for (MyExcelModel myExcelModel : list3) {
    j++;
    System.out.println("[" + j + "] " + myExcelModel);
}</code>
*/
@Slf4j
public class MyExcelUtil {

    private static Sheet initSheet;

    static {
        initSheet = new Sheet(1, 0);
        initSheet.setSheetName("sheet");
        initSheet.setAutoWidth(Boolean.TRUE);
    }

    //List<String> row = (List<String>) table.get(i);
    public static List<Object> readLessThan1000Row(String filePath) {
        return readLessThan1000RowBySheet(filePath, null);
    }

    /**
     * Read less than 1000 line's data, with style
     * sheetNo: sheet page, default 1
     * headLineMun: read from line number, default 0 means from first line
     */
    public static List<Object> readLessThan1000RowBySheet(String filePath, Sheet sheet) {
        if (!StringUtils.hasText(filePath)) {
            return null;
        }

        sheet = sheet != null ? sheet : initSheet;

        InputStream fileStream = null;
        try {
            fileStream = new BufferedInputStream(new FileInputStream(filePath));
            return readLessThan1000RowBySheet(fileStream, sheet);
        } catch (FileNotFoundException e) {
            log.info("Read file failed, file：{}", filePath);
        } finally {
            try {
                if (fileStream != null) {
                    fileStream.close();
                }
            } catch (IOException e) {
                log.info("Read excel failed, err：{}", e);
            }
        }
        return null;
    }

    public static List<Object> readLessThan1000RowBySheet(InputStream fileStream, Sheet sheet) {
        sheet = sheet != null ? sheet : initSheet;
        return EasyExcelFactory.read(fileStream, sheet);
    }


    public static List<Object> readMoreThan1000Row(String filePath) {
        return readMoreThan1000RowBySheet(filePath, null);
    }


    public static List<Object> readMoreThan1000RowBySheet(String filePath, Sheet sheet) {
        if (!StringUtils.hasText(filePath)) {
            return null;
        }

        sheet = sheet != null ? sheet : initSheet;

        InputStream fileStream = null;
        try {
            fileStream = new BufferedInputStream(new FileInputStream(filePath));
            return readMoreThan1000RowBySheet(fileStream, sheet);
        } catch (FileNotFoundException e) {
            log.info("Read file failed, file：{}", filePath);
        } finally {
            try {
                if (fileStream != null) {
                    fileStream.close();
                }
            } catch (IOException e) {
                log.info("Read excel failed, err：{}", e);
            }
        }
        return null;
    }

    public static List<Object> readMoreThan1000RowBySheet(InputStream fileStream, Sheet sheet) {
        sheet = sheet != null ? sheet : initSheet;
        ExcelListener excelListener = new ExcelListener();
        EasyExcelFactory.readBySax(fileStream, sheet, excelListener);
        return excelListener.getDatas();
    }

    public static <T> List<T> readMoreThan1000Row(File file, int sheetNo, int headLineNum, Class klass) {
        Sheet sheet = new Sheet(sheetNo, headLineNum, klass);
        List<Object> objectList = MyExcelUtil.readMoreThan1000RowBySheet(file.getPath(), sheet);
        List<T> result = MyConvertUtil.convertToTList(objectList);
        return result;
    }


    public static void writeBySimple(String filePath, List<List<Object>> data) {
        List<String> head = new ArrayList<>();
        for (int i = 0, len = data.get(0).size(); i < len; i++) {
            head.add("" + i);
        }
        writeSimpleBySheet(filePath, data, head, null);
    }

    public static void writeBySimple(String filePath, List<List<Object>> data, List<String> head) {
        writeSimpleBySheet(filePath, data, head, null);
    }

    public static void writeBySimple(String filePath, List<List<Object>> data, String... heads) {
        List<String> listHead = new ArrayList<>();
        for (String h : heads) {
            listHead.add(h);
        }
        writeBySimple(filePath, data, listHead);
    }

    public static void writeBySimple(File file, List<? extends BaseRowModel> data) {
        try (OutputStream out = new FileOutputStream(file);) {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            Class modeKlass = data.get(0).getClass();
            Sheet sheet = new Sheet(1, 0, modeKlass);
            //sheet.setSheetName("sheet1");
            writer.write(data, sheet);
            writer.finish();
        } catch (Exception e) {
            log.info("Read file failed, file：{}", file);
        }
    }


    public static void writeSimpleBySheet(String filePath, List<List<Object>> data, List<String> head, Sheet sheet) {
        try (OutputStream outputStream = new FileOutputStream(filePath);) {
            writeSimpleBySheet(outputStream, data, head, sheet);
        } catch (Exception e) {
            log.info("Read file failed, file：{}", filePath);
        }
    }

    public static void writeSimpleBySheet(OutputStream outputStream, List<List<Object>> data, List<String> head, Sheet sheet) {
        sheet = (sheet != null) ? sheet : initSheet;

        if (head != null) {
            List<List<String>> list = new ArrayList<>();
            head.forEach(h -> list.add(Collections.singletonList(h)));
            sheet.setHead(list);
        }

        ExcelWriter writer = EasyExcelFactory.getWriter(outputStream);
        writer.write1(data, sheet);
        writer.finish();
    }


    public static void writeWithTemplate(String filePath, List<? extends BaseRowModel> data) {
        writeWithTemplateAndSheet(filePath, data, null);
    }


    public static void writeWithTemplateAndSheet(String filePath, List<? extends BaseRowModel> data, Sheet sheet) {

        try (OutputStream outputStream = new FileOutputStream(filePath);) {
            writeWithTemplateAndSheet(outputStream, data, sheet);
        } catch (Exception e) {
            log.info("Read file failed, file：{}", filePath);
        }

    }

    public static void writeWithTemplateAndSheet(OutputStream outputStream, List<? extends BaseRowModel> data, Sheet sheet) {
        if (CollectionUtils.isEmpty(data)) {
            return;
        }

        sheet = (sheet != null) ? sheet : initSheet;
        sheet.setClazz(data.get(0).getClass());

        ExcelWriter writer = EasyExcelFactory.getWriter(outputStream);
        writer.write(data, sheet);
        writer.finish();
    }


    public static void writeWithMultipleSheel(String filePath, List<MultipleSheelPropety> multipleSheelPropetys) {

        try (OutputStream outputStream = new FileOutputStream(filePath);) {
            writeWithMultipleSheel(outputStream, multipleSheelPropetys);
        } catch (Exception e) {
            log.info("Read file failed, file：{}", filePath);
        }
    }


    public static void writeWithMultipleSheel(OutputStream outputStream, List<MultipleSheelPropety> multipleSheelPropetys) {
        if (CollectionUtils.isEmpty(multipleSheelPropetys)) {
            return;
        }
        ExcelWriter writer = EasyExcelFactory.getWriter(outputStream);
        for (MultipleSheelPropety multipleSheelPropety : multipleSheelPropetys) {
            Sheet sheet = multipleSheelPropety.getSheet() != null ? multipleSheelPropety.getSheet() : initSheet;
            if (!CollectionUtils.isEmpty(multipleSheelPropety.getData())) {
                sheet.setClazz(multipleSheelPropety.getData().get(0).getClass());
            }
            writer.write(multipleSheelPropety.getData(), sheet);
        }
        writer.finish();
    }


    @Data
    public static class MultipleSheelPropety {

        private List<? extends BaseRowModel> data;

        private Sheet sheet;
    }


    @Getter
    @Setter
    public static class ExcelListener extends AnalysisEventListener {

        private List<Object> datas = new ArrayList<>();

        /**
         * parse by line
         * object : current line data
         */
        @Override
        public void invoke(Object object, AnalysisContext context) {

            // context.getCurrentRowNum()
            if (object != null) {
                datas.add(object);
            }
        }


        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            //destroy no-use resource
        }

    }


}

