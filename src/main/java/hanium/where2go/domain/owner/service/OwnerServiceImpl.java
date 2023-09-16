package hanium.where2go.domain.owner.service;

import hanium.where2go.domain.owner.dto.OwnerDto;

public class OwnerServiceImpl implements OwnerService{
    @Override
    public OwnerDto.CreateResponse createOwner(OwnerDto.CreateRequest createOwnerDto) {
        return null;
    }

    @Override
    public boolean validateEmail(String token) {
        return false;
    }

    @Override
    public boolean duplicateEmail(OwnerDto.DuplicateEmailRequest duplicateEmailRequest) {
        return false;
    }

    @Override
    public boolean validateBusinessNum(String businessNum) {
        return false;
    }

    @Override
    public void patchOwner(Long ownerId, OwnerDto.PatchRequest patchOwnerDto) {

    }

    @Override
    public void deleteOwner(Long ownerId) {

    }
}
