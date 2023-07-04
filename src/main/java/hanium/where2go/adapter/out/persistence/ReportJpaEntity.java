package hanium.where2go.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "report")
@NoArgsConstructor
@AllArgsConstructor
public class ReportJpaEntity extends BaseJpaEntity{

    @Id
    @GeneratedValue
    @Column(name = "report_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private ReviewJpaEntity review;

    private String content;
}
