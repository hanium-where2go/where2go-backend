package hanium.where2go.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user")
@Inheritance
public abstract class UserJpaEntity extends BaseJpaEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    public Long id;
    public String name;
    public String email;
    public String password;
    public String phoneNumber;
    public String nickname;
    public boolean isVerified;
}
