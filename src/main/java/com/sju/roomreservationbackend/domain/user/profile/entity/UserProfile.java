package com.sju.roomreservationbackend.domain.user.profile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    // 사용자 계정정보
    @NotBlank(message = "valid.user.username.blank")
    @Size(max=20, message = "valid.user.username.size")
    @Column(unique = true, nullable = false)
    private String username;
    @NotBlank(message = "valid.user.password.blank")
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    // 개인 신상정보 (필수)
    @Email(message = "valid.user.email.email")
    @NotBlank(message = "valid.user.email.blank")
    @Size(max=50, message = "valid.user.email.size")
    @Column
    private String email;
    @Pattern(regexp = "(^02|^\\d{3})-(\\d{3}|\\d{4})-\\d{4}", message = "valid.user.phone.phone")
    @NotBlank(message = "valid.user.phone.blank")
    @Size(min = 9, max = 13, message = "valid.user.phone.size")
    @Column
    private String phone;
    @Column
    @NotBlank(message = "valid.user.name.blank")
    @Size(max=50, message = "valid.user.name.size")
    private String name;

    // 개인 신상정보 (선택)
    @Size(max=50, message = "valid.user.department.size")
    private String department;

    // 사용자별 통계정보
    @Builder.Default
    private Double noShowRate = 0.0d;
    @Builder.Default
    private Integer reserveCnt = 0;
    @Builder.Default
    private Integer noShowCnt = 0;

    // 계정 생성 일시 및 마지막 로그인 일시 (익명 계정 및 휴면처리하는데 참고 - 추후 자동화)
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime lastLoginAt;
    
    // 권한 정보 (사용자 그룹)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_permission", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "user_permission_key")
    @Column(name = "permission")
    @JoinColumn(name = "user_id")
    @Enumerated(EnumType.STRING)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value={CascadeType.ALL})
    @Builder.Default
    private List<Permission> permissions = new ArrayList<>();

    // 계정 활성 여부 (탈퇴/정지계정은 false 처리)
    @Column(nullable = false)
    private Boolean active;

    @Column
    private String fcmRegistrationToken;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissions.stream()
                .map((permission) -> new SimpleGrantedAuthority(permission.toString()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return this.active;
    }
}
