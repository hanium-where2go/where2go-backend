package hanium.where2go.domain.user.entity;

import hanium.where2go.domain.customer.dto.CustomerDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "user")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String nickname;
    private boolean isVerified;

    //기본 유저 생성시 GUEST로 초기화
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.GUEST;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updateAt;

    public void hashPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void update(CustomerDto.InfoRequest infoRequestDto, PasswordEncoder passwordEncoder) {
        this.nickname = infoRequestDto.getNickname();
        this.password = infoRequestDto.getPassword();
        hashPassword(passwordEncoder);
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.password = password;
        hashPassword(passwordEncoder);
    }

    public void authorize(Role role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}
