package hanium.where2go.adapter.out.persistence;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "owner")
@NoArgsConstructor
@AllArgsConstructor
public class OwnerJpaEntity extends UserJpaEntity {

    public String businessRegistration;
}
