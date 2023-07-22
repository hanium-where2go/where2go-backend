package hanium.where2go.domain.customer.service;

import hanium.where2go.domain.customer.entity.Customer;
import hanium.where2go.domain.customer.repository.CustomerRepository;
import hanium.where2go.domain.user.adapter.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerDetailServiceImpl implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        return new UserAdapter(customer);
    }
}
