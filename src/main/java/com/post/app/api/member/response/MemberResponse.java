package com.post.app.api.member.response;

import com.post.app.domain.member.Address;
import com.post.app.domain.member.Member;
import com.post.app.domain.member.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class MemberResponse {

    private Long id;
    private String loginId;
    private String password;
    private String name;
    private String email;
    private Address address;
    private LocalDate birthdate;
    private String phone;
    private Role role;
    private LocalDateTime createAt;

    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getLoginId(),
                member.getPassword(),
                member.getName(),
                member.getEmail(),
                member.getAddress(),
                member.getBirthdate(),
                member.getPhone(),
                member.getRole(),
                member.getCreateAt()
        );
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        MemberResponse that = (MemberResponse) object;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getLoginId(), that.getLoginId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getAddress(), that.getAddress()) &&
                Objects.equals(getBirthdate(), that.getBirthdate()) &&
                Objects.equals(getPhone(), that.getPhone()) && getRole() == that.getRole() &&
                Objects.equals(getCreateAt(), that.getCreateAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLoginId(), getName(), getEmail(), getAddress(), getBirthdate(), getPhone(), getRole(), getCreateAt());
    }
}
