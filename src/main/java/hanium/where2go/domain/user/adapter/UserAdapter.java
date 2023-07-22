package hanium.where2go.domain.user.adapter;

import hanium.where2go.domain.user.entity.User;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

@Getter
public class UserAdapter extends org.springframework.security.core.userdetails.User {

    private User user;

    public UserAdapter(User user) {
        super(user.getEmail(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        this.user = user;
    }

}

