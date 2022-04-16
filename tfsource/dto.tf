package [(${packageName}+'.'+${moduleName}+'.'+${dtoFolder})];

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import java.util.List;
import java.util.*;
import java.math.*;
import ps.demo.common.MyBaseDto;

//
//[(${entityKey})]
@Getter
@Setter
@ToString
public class [(${dtoName})] extends MyBaseDto {

[# th:each="attr,attrStat:${entityAttrs}" ]
    //[(${attrStat.index})]
    private [(${attr.get('type')})] [(${attr.get('name')})];
[/]

}