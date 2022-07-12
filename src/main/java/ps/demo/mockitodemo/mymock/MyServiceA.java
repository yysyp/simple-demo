package ps.demo.mockitodemo.mymock;

import java.util.Locale;

public class MyServiceA {


    public String pubA(String s) {
        System.out.println("MyServiceA.pubA called");
        s = this.friendA(s);
        return s+"_PubA";
    }

    String friendA(String s) {
        System.out.println("MyServiceA.friendA called");
        s = this.privA(s);
        return s+"_FriendA";
    }

    private String privA(String s) {
        System.out.println("MyServiceA.privA called");
        return s+"_PrivA";
    }

    public static String pubStaticA(String s) {
        System.out.println("MyServiceA.pubStaticA called");
        s = friendStaticA(s);
        return s+"_PubStatic";
    }

    static String friendStaticA(String s) {
        System.out.println("MyServiceA.friendStaticA called");
        s = privStaticA(s);
        return s+"_FriendStatic";
    }

    private static String privStaticA(String s) {
        System.out.println("MyServiceA.privStaticA called");
        return s+"_PrivStatic";
    }

}
