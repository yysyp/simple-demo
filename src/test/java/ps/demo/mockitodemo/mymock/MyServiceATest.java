package ps.demo.mockitodemo.mymock;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;

import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(MockitoJUnitRunner.class)
class MyServiceATest {

    @Test
    public void spy_Simple_demo() {
        List<String> list = new LinkedList<String>();
        List<String> spy = Mockito.spy(list);
        Mockito.when(spy.size()).thenReturn(100);

        spy.add("one");
        spy.add("two");

/*        spy的原理是，如果不打桩默认都会执行真实的方法，如果打桩则返回桩实现。
        可以看出spy.size()通过桩实现返回了值100，而spy.get(0)则返回了实际值*/
        assertEquals(spy.get(0), "one");
        assertEquals(100, spy.size());
    }


    @Test
    public void pubA1() {
        MyServiceA myServiceA = Mockito.spy(new MyServiceA());
        String str = "Abc123";

        //Mockito.when(myServiceA.friendA(Mockito.anyString())).thenReturn("mock result & called method...");
        Mockito.doReturn("mock result & not call method...").when(myServiceA).friendA(Mockito.anyString());

        System.out.println("------------------1");
        String s1 = myServiceA.pubA(str);
        System.out.println(s1);
        System.out.println("------------------2");
        String s2 = myServiceA.friendA(str);
        System.out.println(s2);
        System.out.println("------------------3");

        //Mockito.verify(myServiceA);

    }

    @Test
    public void pubA2() {
        MyServiceA myServiceA = Mockito.mock(MyServiceA.class);
        Mockito.doCallRealMethod().when(myServiceA).pubA(Mockito.anyString());
        String s = myServiceA.pubA("Abc123");
        System.out.println(s);
        //MyServiceA.pubA called
        //null_PubA
    }


    @Test
    public void mockSpecificMethod() {
        MyServiceA myServiceA = Mockito.mock(MyServiceA.class);
        Mockito.doCallRealMethod().when(myServiceA).pubA(Mockito.anyString());
        Mockito.doReturn("Mock friendA").when(myServiceA).friendA(Mockito.anyString());
        //Mockito.doNothing() This for mock void method
        String s = myServiceA.pubA("Abc123");
        System.out.println(s);
        //MyServiceA.pubA called
        //Mock friendA_PubA
    }

    @Test
    public void mockSpecificStaticMethod() {
        try (MockedStatic<MyServiceA> myServiceAMockedStatic = Mockito.mockStatic(MyServiceA.class);) {
            myServiceAMockedStatic.when(() -> MyServiceA.friendStaticA(Mockito.anyString()))
                    .thenReturn("mock static friend");
            myServiceAMockedStatic.when(() -> MyServiceA.pubStaticA(Mockito.anyString()))
            .thenCallRealMethod();
            String s = MyServiceA.pubStaticA("EEE44");//MyServiceA.friendStaticA("EFg456");
            System.out.println("--->>" + s);
        }
        //MyServiceA.pubStaticA called
        //--->>mock static friend_PubStatic
    }

}