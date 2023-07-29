package ps.demo.interview;

//给多个数，串联成最大的数
import java.util.*;
public class CombineNumbersToBigNumber {

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        System.out.println("Please provide numbers separated with blank:");
        String arr[] = (cin.nextLine()).split(" ");
        System.out.println(maxNum(arr));
    }

    public static String maxNum(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length; j++) {
                if (compareSwitch(arr[i], arr[j])) {
                    String temp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = temp;
                }
            }
        }
        String num = "";
        for (int m = 0; m < arr.length; m++) {
            num = num+arr[m];
        }
        return num;
    }

    public static boolean compareSwitch(String s1, String s2) {
        int length = s1.length()+s2.length();
        s1 += s2;
        s2 += s1;
        for (int i = 0; i < length; i++) {
            if (Integer.parseInt(s1.substring(i, i+1)) > Integer.parseInt(s2.substring(i, i+1))) {
                return false;
            }
            if (Integer.parseInt(s1.substring(i, i+1)) < Integer.parseInt(s2.substring(i, i+1))) {
                return true;
            }
        }
        return false;
    }

}
