package [(${packageName}+'.'+${moduleName}+'.'+${entityFolder})];

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

import ps.demo.common.MyBaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.*;
import java.math.*;

import lombok.*;
import java.util.*;
import java.math.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "[(${tableName})]")
public class [(${entityName})] extends MyBaseEntity {
[# th:each="attr,attrStat:${entityAttrs}" ]
    private [(${attr.get('type')})] [(${attr.get('name')})];
[/]

}
