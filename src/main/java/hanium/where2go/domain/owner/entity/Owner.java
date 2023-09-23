package hanium.where2go.domain.owner.entity;


import hanium.where2go.domain.owner.dto.OwnerDto;
import hanium.where2go.domain.restaurant.entity.Restaurant;
import hanium.where2go.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@Table(name = "owner")
@NoArgsConstructor
@AllArgsConstructor
public class Owner extends User {

    private String businessRegistration;

    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Restaurant restaurant;

    public void changeOwner(OwnerDto.PatchRequest ownerDto) {
        if (ownerDto.getName() != null) super.setName(ownerDto.getName());
        if (ownerDto.getPhoneNum() != null) super.setPhoneNumber(ownerDto.getPhoneNum());
    }

    public void changeBusinessRegistration(String businessNum) {
        this.businessRegistration = businessNum;
    }
}
