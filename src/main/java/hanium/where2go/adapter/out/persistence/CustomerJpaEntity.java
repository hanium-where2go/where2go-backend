package hanium.where2go.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "customer")
public class CustomerJpaEntity extends UserJpaEntity {
}
