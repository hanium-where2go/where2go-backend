package hanium.where2go.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
public class CategoryJpaEntity extends BaseJpaEntity {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;
    private String categoryName;
}
