package ps.demo.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Person {

    private long id;

    @NotBlank
    @Size(min = 0, max = 20)
    private String title;

    @NotBlank
    @Size(min = 0, max = 30)
    private String nickName;

    private String email;

    private int age;

    private double score;

    private boolean flag;

    public Person(String nickName, String title, int age) {
        this.nickName = nickName;
        this.title = title;
        this.age = age;
    }

}
