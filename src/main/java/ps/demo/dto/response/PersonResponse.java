package ps.demo.dto.response;

import lombok.*;
import ps.demo.dto.Person;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponse extends BasicOkResponse {

    private Person data;


}
