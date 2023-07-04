package hanium.where2go.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "hashtag")
@NoArgsConstructor
@AllArgsConstructor
public class HashtagJpaEntity extends BaseJpaEntity{

    @Id
    @GeneratedValue
    @Column(name = "hashtag_id")
    private Long id;

    private String hashtagName;
}
