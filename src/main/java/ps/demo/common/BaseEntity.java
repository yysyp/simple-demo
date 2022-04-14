/**
 * 
 */
package ps.demo.common;

import lombok.*;

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
public class BaseEntity {

	private String createdBy;

	private Instant createdOn;

	private Boolean isActive;
	private Boolean isLogicalDeleted;

	private String modifiedBy;

	private Instant modifiedOn;

}
