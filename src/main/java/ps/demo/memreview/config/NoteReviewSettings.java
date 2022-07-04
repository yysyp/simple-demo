package ps.demo.memreview.config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
@ConfigurationProperties(prefix = "note-card")
public class NoteReviewSettings {

    private Integer fontSize;

    private Integer pageSize;

    private Double failMinus;

    private Double okMinus;

    private List<Integer> reviewAtMinutes;

}
