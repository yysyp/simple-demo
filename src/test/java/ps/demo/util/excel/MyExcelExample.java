package ps.demo.util.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.*;
import ps.demo.util.MyConvertUtil;
import ps.demo.util.MyExcelUtil;
import ps.demo.util.MyFileUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MyExcelExample {

    public static void main(String[] args) {

        File myExcelFile = MyFileUtil.getFileTsInHomeDir("myexcel.xlsx");

        //Write XLSX1-----------------------------------------------------------------------
        System.out.println("--->>Excel write1 to myExcelFile=" + myExcelFile);
        try (OutputStream out = new FileOutputStream(myExcelFile);) {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            Sheet sheet = new Sheet(1, 0, MyExcelModel.class);
            sheet.setSheetName("sheet1");
            List<MyExcelModel> data = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                MyExcelModel row = new MyExcelModel.MyExcelModelBuilder()
                        .name("name" + i)
                        .age(new Random().nextInt(100))
                        .score(new Random().nextDouble() * 100).build();
                data.add(row);
            }
            writer.write(data, sheet);
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //Write XLSX2-----------------------------------------------------------------------
        System.out.println("--->>Excel write2 to myExcelFile=" + myExcelFile);
        List<MyExcelModel> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MyExcelModel row = new MyExcelModel.MyExcelModelBuilder()
                    .name("haha-name" + i)
                    .age(new Random().nextInt(100))
                    .score(new Random().nextDouble() * 100).build();
            data.add(row);
        }
        MyExcelUtil.writeBySimple(MyFileUtil.getFileTsInHomeDir("myexcel.xlsx"), data);


        //Read XLSX 1-----------------------------------------------------------------------
        File myexcelFile1 = MyFileUtil.getFileInHomeDir("myexcel.xlsx");

        Sheet sheet1 = new Sheet(1, 1, MyExcelModel.class);
        List<Object> objectList = MyExcelUtil.readMoreThan1000RowBySheet(myexcelFile1.getPath(), sheet1);
        List<MyExcelModel> list1 = MyConvertUtil.convertToTList(objectList);
        System.out.println("--->>1 Excel read from myExcelFile=" + myExcelFile);
        int i = 0;
        for (MyExcelModel myExcelModel : list1) {
            i++;
            System.out.println("[" + i + "] " + myExcelModel);
        }

        //Read XLSX 2-----------------------------------------------------------------------
        try (FileInputStream in = new FileInputStream(myexcelFile1);) {
            Sheet sheet = new Sheet(1, 1, MyExcelModel.class);
            List<Object> objects = EasyExcelFactory.read(in, sheet);
            List<MyExcelModel> list2 = new ArrayList<MyExcelModel>();
            for (Object obj : objects) {
                list2.add((MyExcelModel) obj);
            }

            System.out.println("--->>2 Excel read from myExcelFile=" + myExcelFile);
            i = 0;
            for (MyExcelModel myExcelModel : list2) {
                i++;
                System.out.println("[" + i + "] " + myExcelModel);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Read XLSX 3-----------------------------------------------------------------------
        System.out.println("--->>3 Excel read from myExcelFile=" + myExcelFile);
        List<MyExcelModel> list3 =  MyExcelUtil.readMoreThan1000Row(myexcelFile1, 1, 1, MyExcelModel.class);
        i = 0;
        for (MyExcelModel myExcelModel : list3) {
            i++;
            System.out.println("[" + i + "] " + myExcelModel);
        }


    }

}
