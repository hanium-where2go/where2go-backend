package hanium.where2go.domain.restaurant.entity;


import hanium.where2go.domain.User;
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
public class Owner extends User {

    public String businessRegistration;
}
