package hanium.where2go.domain.owner.service;

import hanium.where2go.domain.owner.dto.OwnerDto;
import hanium.where2go.domain.owner.dto.OwnerMapper;
import hanium.where2go.domain.owner.entity.Owner;
import hanium.where2go.domain.owner.repository.OwnerRepository;
import hanium.where2go.domain.user.entity.Role;
import hanium.where2go.global.jwt.JwtProvider;
import hanium.where2go.global.response.BaseException;
import hanium.where2go.global.response.ExceptionCode;
import hanium.where2go.global.utils.BusinessNumUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService{

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final BusinessNumUtils businessNumUtils;

    @Override
    public OwnerDto.CreateResponse createOwner(OwnerDto.CreateRequest createOwnerDto) {
        if(ownerRepository.findByEmail(createOwnerDto.getEmail()).isPresent()) {
            throw new BaseException(ExceptionCode.DUPLICATED_USER);
        }

        Owner owner = Owner.builder()
                .email(createOwnerDto.getEmail())
                .password(createOwnerDto.getPassword())
                .name(createOwnerDto.getName())
                .phoneNumber(createOwnerDto.getPhoneNum())
                .nickname(createOwnerDto.getNickname())
//                .isVerified(false)
                .build();

        owner.hashPassword(passwordEncoder);
        ownerRepository.save(owner);

        return ownerMapper.ownerToCreateResponse(owner);
    }

    @Override
    public boolean duplicateEmail(String email) {
        return ownerRepository.findByEmail(email).isPresent() ? true : false;
    }

    @Override
    public boolean validateEmail(String token) {
        String email = jwtProvider.extractEmail(token);
        Owner owner = ownerRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(ExceptionCode.USER_NOT_FOUND));
        owner.authorize(Role.OWNER);
        return true;
    }

    @Override
    public boolean validateBusinessNum(OwnerDto.BusinessNumStatus businessNum) {
        if(!businessNumUtils.validateBusinessNum(businessNum.getBusinessNum())) {
            throw new BaseException(ExceptionCode.INVALID_BUSINESS_KEY);
        }

        Owner owner = ownerRepository.findByEmail(businessNum.getEmail())
                .orElseThrow(() -> new BaseException(ExceptionCode.USER_NOT_FOUND));
        owner.changeBusinessRegistration(businessNum.getBusinessNum());

        return true;
    }

   @Override
    public void patchOwner(Long ownerId, OwnerDto.PatchRequest patchOwnerDto) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new BaseException(ExceptionCode.USER_NOT_FOUND));
        owner.changeOwner(patchOwnerDto);
    }

    @Override
    public void deleteOwner(Long ownerId) {
        ownerRepository.deleteById(ownerId);
    }
}
