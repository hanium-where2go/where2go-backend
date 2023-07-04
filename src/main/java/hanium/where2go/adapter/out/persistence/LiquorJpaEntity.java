package hanium.where2go.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table
@NoArgsConstructor
@AllArgsConstructor
public class LiquorJpaEntity extends BaseJpaEntity{

    @Id
    @GeneratedValue
    @Column(name = "liquor")
    private Long id;
    private String liquorName;
}
