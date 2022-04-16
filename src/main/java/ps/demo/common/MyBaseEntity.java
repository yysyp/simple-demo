/**
 * 
 */
package ps.demo.common;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

/**
 * Copy from BaseEntity.
 */
@Builder
@ToString
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class MyBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String createdBy;

	private Instant createdOn;

	private Boolean isActive;
	private Boolean isLogicalDeleted;

	private String modifiedBy;

	private Instant modifiedOn;

}
