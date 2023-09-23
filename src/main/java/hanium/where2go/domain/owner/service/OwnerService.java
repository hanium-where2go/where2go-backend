package hanium.where2go.domain.owner.service;

import hanium.where2go.domain.owner.dto.OwnerDto;
import hanium.where2go.domain.owner.entity.Owner;

public interface OwnerService {
    OwnerDto.CreateResponse createOwner(OwnerDto.CreateRequest createOwnerDto);
    boolean validateEmail(String token);
    boolean duplicateEmail(String email);
    boolean validateBusinessNum(String businessNum);
    void patchOwner(Long ownerId, OwnerDto.PatchRequest patchOwnerDto);
    void deleteOwner(Long ownerId);

}
