package ps.demo.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyConvertUtilTest {

    @Test
    void alignSize() {
        List<String> line = new ArrayList<>();
        line.add("1");
        line.add("2");

        line = MyConvertUtil.alignSize(line, 2);
        System.out.println(line);

        line.add("3");
        line.add("4");
        line = MyConvertUtil.alignSize(line, 4);
        System.out.println(line);

        line = MyConvertUtil.alignSize(null, 5);
        System.out.println(line);

        line = MyConvertUtil.alignSize(null, 5);
        System.out.println(line);
    }

    @Test
    void convertToStringList() {
        List<Object> objectList = new ArrayList<>();
        objectList.add(212);
        objectList.add("aaa");
        objectList.add(new Date());
        List<String> stringList = MyConvertUtil.convertToStringList(objectList);
        System.out.println("stringList = " + stringList);


    }

    @Test
    void convertToObjectList() {
        List<String> stringList = new ArrayList<>();
        stringList.add("aaa");
        stringList.add("bbb");
        stringList.add("ccc");
        List<Object> objects = MyConvertUtil.convertToObjectList(stringList);
        System.out.println("objects=" + objects);
    }

    @Test
    void convertToTList() {
        List<Object> stringList = new ArrayList<>();
        stringList.add("aaa");
        stringList.add("bbb");
        stringList.add("ccc");

        List<String> stringList1 = MyConvertUtil.convertToTList(stringList);
        System.out.println("stringList1 = " + stringList1);
    }

    @Test
    void castList() {
        List<Object> stringList = new ArrayList<>();
        stringList.add("aaa");
        stringList.add("bbb");
        stringList.add("ccc");

        List<String> stringList2 = MyConvertUtil.castList(stringList, String.class);
        System.out.println("stringList2 = " + stringList2);
    }
}