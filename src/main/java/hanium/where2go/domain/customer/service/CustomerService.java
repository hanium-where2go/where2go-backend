package hanium.where2go.domain.customer.service;

import hanium.where2go.domain.category.entity.Category;
import hanium.where2go.domain.category.repository.CategoryRepository;
import hanium.where2go.domain.customer.dto.*;
import hanium.where2go.domain.customer.entity.Customer;
import hanium.where2go.domain.customer.entity.FavorCategory;
import hanium.where2go.domain.customer.entity.FavorLiquor;
import hanium.where2go.domain.customer.entity.Point;
import hanium.where2go.domain.customer.repository.CustomerRepository;
import hanium.where2go.domain.liquor.entity.Liquor;
import hanium.where2go.domain.liquor.repository.LiquorRepository;
import hanium.where2go.domain.user.dto.UserInfoRequestDto;
import hanium.where2go.domain.user.entity.Role;
import hanium.where2go.global.jwt.JwtProvider;
import hanium.where2go.global.response.BaseException;
import hanium.where2go.global.response.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final LiquorRepository liquorRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;


    @Transactional
    public void signup(CustomerSignupRequestDto customerSignupRequestDto) {

        if (customerRepository.findByEmail(customerSignupRequestDto.getEmail()).isPresent()) {
            throw new BaseException(ExceptionCode.DUPLICATED_USER);
        }

        Customer customer = Customer.builder()
            .email(customerSignupRequestDto.getEmail())
            .password(customerSignupRequestDto.getPassword())
            .name(customerSignupRequestDto.getName())
            .phoneNumber(customerSignupRequestDto.getPhoneNumber())
            .nickname(customerSignupRequestDto.getNickname())
            .isVerified(false)
            .build();

        customer.hashPassword(passwordEncoder);

        customerRepository.save(customer);
    }

    public boolean duplicateEmail(CustomerDuplicateEmailRequestDto customerDuplicateEmailRequestDto) {
        if (customerRepository.findByEmail(customerDuplicateEmailRequestDto.getEmail()).isPresent()) {
            return true;
        }
        return false;
    }

    //로그인 후 access token, refresh token 반환
    public CustomerLoginResponseDto login(CustomerLoginRequestDto customerLoginRequestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    customerLoginRequestDto.getEmail(),
                    customerLoginRequestDto.getPassword()
                )
            );
            return new CustomerLoginResponseDto(jwtProvider.generateAccessTokenByEmail(authentication.getName()), jwtProvider.generateRefreshToken());
        } catch (AuthenticationException authenticationException) {
            throw new BaseException(ExceptionCode.UNAUTHENTICATED_USER);
        }
    }

    public CustomerFindEmailResponseDto findEmail(CustomerFindEmailRequestDto customerFindEmailRequestDto) {
        Customer customer = customerRepository.findByNameAndPhoneNumber(customerFindEmailRequestDto.getName(), customerFindEmailRequestDto.getPhoneNumber())
            .orElseThrow(() -> new BaseException(ExceptionCode.USER_NOT_FOUND));
        return new CustomerFindEmailResponseDto(customer.getEmail());
    }

    public CustomerInfoResponseDto getInfo(Customer customer) {
        return CustomerInfoResponseDto.builder()
            .name(customer.getName())
            .nickname(customer.getNickname())
            .email(customer.getEmail())
            .phoneNumber(customer.getPhoneNumber())
            .build();
    }

    @Transactional
    public CustomerInfoResponseDto updateInfo(Customer customer, UserInfoRequestDto userInfoRequestDto) {
        customer.update(userInfoRequestDto, passwordEncoder);
        customerRepository.save(customer);

        return CustomerInfoResponseDto.builder()
            .name(customer.getName())
            .nickname(customer.getNickname())
            .email(customer.getEmail())
            .phoneNumber(customer.getPhoneNumber())
            .build();
    }

    @Transactional
    public void authorizeCustomer(String token) {
        String email = jwtProvider.extractEmail(token);
        Customer customer = customerRepository.findByEmail(email)
            .orElseThrow(() -> new BaseException(ExceptionCode.USER_NOT_FOUND));

        customer.authorize(Role.CUSTOMER);

        //포인트 생성
        Point point = Point.builder()
            .amount(0)
            .build();

        customer.setPoint(point);

        //기본 포인트 10000원 적립
        customer.earn(10000, "회원가입");
    }

    public CustomerPointResponseDto getPoint(Customer customer) {
        return CustomerPointResponseDto.builder()
            .point(customer.getPoint().getAmount())
            .build();
    }

    @Transactional
    public void createFavorLiquor(Customer customer, Long liquorId) {
        customer.getFavorLiquors().stream()
            .filter(fl -> fl.getLiquor().getId() == liquorId)
            .findAny()
            .ifPresent((f) -> {
                throw new BaseException(ExceptionCode.DUPLICATED_FAVOR_LIQUOR);
            });


        Liquor liquor = liquorRepository.findById(liquorId)
            .orElseThrow(() -> new BaseException(ExceptionCode.LIQUOR_NOT_FOUND));

        FavorLiquor favorLiquor = FavorLiquor.builder()
            .liquor(liquor)
            .build();

        customer.addFavorLiquor(favorLiquor);
    }

    public CustomerFavorLiquorResponseDto getFavorLiquors(Customer customer) {
        return new CustomerFavorLiquorResponseDto(customer.getFavorLiquors().stream()
            .map(favorLiquor -> favorLiquor.getLiquor().getId())
            .collect(Collectors.toList()));
    }

    @Transactional
    public void deleteFavorLiquor(Customer customer, Long liquorId) {
        FavorLiquor favorLiquor = customer.getFavorLiquors().stream().
            filter(fl -> fl.getLiquor().getId() == liquorId)
            .findFirst()
            .orElseThrow(() -> new BaseException(ExceptionCode.LIQUOR_NOT_FOUND));

        customer.removeFavorLiquor(favorLiquor);
    }

    @Transactional
    public void createFavorCategory(Customer customer, Long categoryId) {
        customer.getFavorCategories().stream()
            .filter(fc -> fc.getCategory().getId() == categoryId)
            .findAny()
            .ifPresent((f) -> {
                throw new BaseException(ExceptionCode.DUPLICATED_FAVOR_CATEGORY);
            });

        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new BaseException(ExceptionCode.CATEGORY_NOT_FOUND));

        FavorCategory favorCategory = FavorCategory.builder()
            .category(category)
            .build();

        customer.addFavorCategory(favorCategory);
    }

    public CustomerFavorLiquorResponseDto getFavorCategories(Customer customer) {
        return new CustomerFavorLiquorResponseDto(customer.getFavorCategories().stream()
            .map(favorCategory -> favorCategory.getCategory().getId())
            .collect(Collectors.toList()));
    }

    @Transactional
    public void deleteFavorCategory(Customer customer, Long categoryId) {
        FavorCategory favorCategory = customer.getFavorCategories().stream().
            filter(fc -> fc.getCategory().getId() == categoryId)
            .findFirst()
            .orElseThrow(() -> new BaseException(ExceptionCode.LIQUOR_NOT_FOUND));

        customer.removeFavorCategory(favorCategory);
    }

    @Transactional
    public void withdrawal(Customer customer) {
        customerRepository.delete(customer);
    }

    @Transactional
    public void resetPassword(Customer customer, CustomerResetPasswordRequestDto customerResetPasswordRequestDto) {
        customer.changePassword(customerResetPasswordRequestDto.getPassword(), passwordEncoder);
    }
}
