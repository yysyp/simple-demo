package ps.demo.util;

import ps.demo.dto.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MyStreamUtilTest {

    public static void main(String[] args) {
        Person user1 = Person.builder().nickName("zhangsan").title("manager").age(34).build();
        Person user12 = Person.builder().nickName("zhangsan2").title("manager").age(57).build();
        Person user13 = Person.builder().nickName("zhangsan3").title("manager").age(45).build();
        Person user2 = Person.builder().nickName("lisi").title("director").age(45).build();
        Person user3 = Person.builder().nickName("wangwu").title("vp").age(45).build();
        Person user32 = Person.builder().nickName("wangwu2").title("vp").age(45).build();

        List<Person> list = new ArrayList<Person>();
        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user12);
        list.add(user13);
        list.add(user32);

        Map<String, List<Person>> collect1
                = list.stream().collect(Collectors.groupingBy(Person::getTitle));
        System.out.println("===>>1: " + collect1);

        Map<String, Long> collect1_1
                = list.stream().collect(Collectors.groupingBy(Person::getTitle, Collectors.counting()));
        System.out.println("===>>1.1: " + collect1_1);

        Map<String, Long> collect1_2
                = list.stream().collect(Collectors.groupingBy(Person::getTitle, Collectors.summingLong(Person::getAge)));
        System.out.println("===>>1.2: " + collect1_2);

        Map<String, Double> collect1_3
                = list.stream().collect(Collectors.groupingBy(Person::getTitle, Collectors.averagingDouble(Person::getAge)));
        System.out.println("===>>1.3: " + collect1_3);

        //Group by multiple fields.
        Map<String, Map<Integer, List<Person>>> collect2
                = list.stream().collect(
                Collectors.groupingBy(
                        Person::getTitle, Collectors.groupingBy(Person::getAge)
                )
        );
        System.out.println("===>>2: " + collect2);

        Map<String, List<Person>> collect2_1
                = list.stream().collect(
                Collectors.groupingBy(u -> u.getTitle()+"#"+u.getAge())
        );
        System.out.println("===>>2.1: " + collect2_1);


        List<Person> list1 = list.stream().filter(MyStreamUtil.distinctByKey(e -> e.getAge())).collect(Collectors.toList());
        System.out.println("===>>List1: "+list1.size()+" detail: "+list1);
        List<Person> list2 = list.stream().filter(MyStreamUtil.distinctByKeys(e -> e.getAge(), e -> e.getTitle())).collect(Collectors.toList());
        System.out.println("===>>List2: "+list2.size() + " detail: "+list2);

    }

}