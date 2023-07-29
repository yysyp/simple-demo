package ps.demo.interview;


/**
 * #有N个阶梯，一个人每一步只能走一个台阶或是两个台阶，问这个人上这个阶梯有多少种走法？
 *
 */
public class GoStepsBottomUp {

    public static void main(String[] args) {
        System.out.println(ways(3));
        System.out.println(ways(4));
        System.out.println(ways(10));
    }

    public static int ways(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int [] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 2;
        for (int i = 2; i < n; i++) {
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n-1];
    }
}
