package ps.demo.interview;

/**
 * #有N个阶梯，一个人每一步只能走一个台阶或是两个台阶，问这个人上这个阶梯有多少种走法？
 *
 * 思路：拆分问题，不去思考N个台阶，而是拆分问题思考，如果只有一个台阶，那么只有一种走法。
 * 如果两个台阶那么有两种走法（一种一个一个台阶走，一种两个台阶一起走），如果三个台阶，那么
 * 所有的走法最终分成两类，一类是最后一个台阶是走两步，另外一类是最后一个台阶走一步，
 * 所以总的总类就是 ways(3-1) + ways(3-2)
 */
public class GoSteps {
    public static void main(String [] args) {
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
        return ways(n-1) + ways(n-2);
    }
}
