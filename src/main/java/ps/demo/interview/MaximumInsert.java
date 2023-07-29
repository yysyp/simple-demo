package ps.demo.interview;

/**
 * Given a str, please calculate the maximum 'a' you can insert into that make
 * the string only has maximum 2 consecutive 'a'.
 */
public class MaximumInsert {

    public static void main(String[] args) {
        String s = "d";
        int count = 0;
        for (int i = 0, n = s.length(); i < n; i++) {
            Character preCh = null;
            if (i - 1 > 0) {
                preCh = s.charAt(i-1);
            }
            Character curCh = s.charAt(i);
            Character aftCh = null;
            if (i + 1 < n) {
                aftCh = s.charAt(i + 1);
            }
            if (preCh != null && preCh == 'a') {
                continue;
            }
            if (curCh == 'a' && aftCh != null && aftCh == 'a') {
                continue;
            }
            if (curCh == 'a') {
                count ++;
                continue;
            }
            count += 2;
        }
        if (s.charAt(s.length() - 1) != 'a') {
            count += 2;
        }
        System.out.println("Count insert number of a is " + count);

    }
}
