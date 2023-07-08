package hanium.where2go.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role")
public abstract class User extends BaseEntity {

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
