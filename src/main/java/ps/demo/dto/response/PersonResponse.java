package ps.demo.dto.response;

import lombok.*;
import ps.demo.dto.Person;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponse extends AbsResponse {

    private Person data;


}
