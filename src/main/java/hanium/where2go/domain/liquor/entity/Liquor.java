package hanium.where2go.domain.liquor.entity;

import hanium.where2go.domain.BaseEntity;
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
public class Liquor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "liquor")
    private Long id;
    private String liquorName;

    public void changeLiquorName(String liquorName) {
        this.liquorName = liquorName;
    }
}
