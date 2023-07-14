package hanium.where2go.domain.customer.service;

import hanium.where2go.domain.customer.dto.*;
import hanium.where2go.domain.customer.entity.Customer;
import hanium.where2go.domain.customer.repository.CustomerRepository;
import hanium.where2go.global.jwt.JwtProvider;
import hanium.where2go.global.response.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;


    @Transactional
    public void signup(CustomerSignupRequestDto customerSignupRequestDto) {

        if (customerRepository.findByEmail(customerSignupRequestDto.getEmail()).isPresent()) {
            throw new BaseException(400, "이미 존재하는 사용자입니다.");
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
            throw new BaseException(401, authenticationException.getMessage());
        }
    }

}
