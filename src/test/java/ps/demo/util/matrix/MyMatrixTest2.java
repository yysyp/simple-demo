package ps.demo.util.matrix;

import ps.demo.util.MyBeanUtil;
import ps.demo.util.MyExcelUtil;
import ps.demo.util.MyFileUtil;
import ps.demo.util.MyTimeUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyMatrixTest2 {

    public static void main(String[] args) {
        MyMatrix myMatrix = new MyMatrix(2, 3);
        for (int r = 0; r < myMatrix.getRows(); r++) {
            for (int c = 0; c < myMatrix.getCols(); c++) {
                myMatrix.setCell(c, r, "Object: ["+r+"]["+c+"]");
            }
        }

        System.out.println(myMatrix);

        myMatrix.addRow();

        System.out.println(myMatrix);

        myMatrix.addColumn();
        System.out.println(myMatrix);

        List<String> row = Arrays.asList("--1--", "--2--", "--3--", "--4--");
        myMatrix.addRow(new ArrayList<>(row));
        System.out.println(myMatrix);

        myMatrix.addRow();
        List<String> col = Arrays.asList("a", "b", "c", "d", "e");
        myMatrix.addColumn(MyBeanUtil.convertToListObject(col));
        System.out.println(myMatrix);

        myMatrix.addColumn();

        List<List<Object>> listlist = myMatrix.toListList();
        MyMatrix myMatrix2 = new MyMatrix(5, 6);
        System.out.println("--------------->>before fill");
        System.out.println(myMatrix2);
        myMatrix2.fillTable(listlist);
        System.out.println("--------------->>after fill");
        System.out.println(myMatrix2);
        //MyExcelUtil.writeBySimple(MyFileUtil.getFileTsInHomeDir("myMatrix.xlsx").getPath(), );
    }


}