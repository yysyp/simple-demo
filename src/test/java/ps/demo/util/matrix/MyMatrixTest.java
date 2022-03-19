package ps.demo.util.matrix;

import com.google.common.collect.Lists;
import ps.demo.util.MyExcelUtil;
import ps.demo.util.MyFileUtil;
import ps.demo.util.MyTimeUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MyMatrixTest {

    public static void main(String[] args) {
        MyMatrix myMatrix = new MyMatrix(3, 5);
        for (int r = 0; r < myMatrix.getRows(); r++) {
            for (int c = 0; c < myMatrix.getCols(); c++) {
                myMatrix.setCell(c, r, "Object: ["+r+"]["+c+"]");
            }
        }
        print(myMatrix);
        System.out.println("-----------------------------");
        MyMatrix myMatrix1 = myMatrix.reverseRowColumn();
        print(myMatrix1);

        List<List<Object>> listList = myMatrix1.toListList();
        List<String> head = Arrays.asList("c1", "c2", "c3");
        File file = MyFileUtil.getFileInHomeDir("myMatrixExcel"+ MyTimeUtil.getNowStr() +".xlsx");
        MyExcelUtil.writeBySimple(file.getAbsolutePath(), listList, head);
    }

    public static void print(MyMatrix myMatrix) {
//        for (int r = 0; r < myMatrix.getRows(); r++) {
//            for (int c = 0; c < myMatrix.getCols(); c++) {
//                System.out.print(myMatrix.getCell(c, r)+"\t");
//            }
//            System.out.println("");
//        }
        System.out.println(myMatrix);
    }

}