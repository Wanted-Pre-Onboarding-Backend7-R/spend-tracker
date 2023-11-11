package com.wanted.spendtracker.domain;

import com.wanted.spendtracker.dto.request.MemberSignUpRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.wanted.spendtracker.domain.Role.ROLE_USER;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String accountName;

    @Column(nullable = false, length = 60)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    private Member(String accountName, String password, Role role) {
        this.accountName = accountName;
        this.password = password;
        this.role = (role != null) ? role : ROLE_USER;
    }

    public static Member of(MemberSignUpRequest request, String encodedPassword) {
        return Member.builder()
                .accountName(request.getAccountName())
                .password(encodedPassword).build();
    }

}
