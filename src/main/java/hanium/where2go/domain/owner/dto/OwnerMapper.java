package hanium.where2go.domain.owner.dto;

import hanium.where2go.domain.owner.entity.Owner;
import org.springframework.stereotype.Component;

@Component
public class OwnerMapper {
    public OwnerDto.CreateResponse ownerToCreateResponse(Owner owner) {
        return OwnerDto.CreateResponse
                .builder()
                .name(owner.getName())
                .email(owner.getEmail())
                .build();
    }
}
