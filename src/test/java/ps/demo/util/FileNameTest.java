package ps.demo.util;

public class FileNameTest {

    public static void main(String [] args) {
        String invalidFileName = "x<a>b:c\"d/e\\f|g?h*i'jzqryiurwiquwqerqu" +
                "iwrywquryuiwqryuweryiwruwryiwqyriqwruqwerwqirqwurwquiruqw" +
                "rqwrwqreqwrwqrqwrwquryiqwuryqiwurysnv,mcnv,njk423rf,nsaqwe";
        String validFileName = MyFileUtil.toValidFileName(invalidFileName);
        System.out.println(validFileName);
    }

}
