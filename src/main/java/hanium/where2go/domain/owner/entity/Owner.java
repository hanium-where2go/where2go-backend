package hanium.where2go.domain.owner.entity;


import hanium.where2go.domain.restaurant.entity.Restaurant;
import hanium.where2go.domain.user.entity.User;
import jakarta.persistence.*;
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

    private String businessRegistration;

    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Restaurant restaurant;
}
