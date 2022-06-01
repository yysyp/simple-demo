package ps.demo.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyStringUtilTest2 {


    public static void main(String[] args) {
        String str = "中华人民共和国的";
        String target = "中华农民共和国的";
        System.out.println(MyStringUtil.getSimilarityRatio(str, target));
    }

}