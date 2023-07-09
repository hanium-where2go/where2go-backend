package hanium.where2go.domain.customer.service;

import hanium.where2go.domain.customer.dto.CustomerSignupRequestDto;
import hanium.where2go.domain.customer.entity.Customer;
import hanium.where2go.domain.customer.repository.CustomerRepository;
import hanium.where2go.global.response.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

}
