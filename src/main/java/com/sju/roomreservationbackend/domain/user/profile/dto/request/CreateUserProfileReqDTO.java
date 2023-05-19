package com.sju.roomreservationbackend.domain.user.profile.dto.request;

import com.sju.roomreservationbackend.domain.user.profile.entity.Permission;
import lombok.Data;

import jakarta.validation.constraints.*;

@Data
public class CreateUserProfileReqDTO {
    @NotBlank(message = "valid.user.username.blank")
    @Size(max=20, message = "valid.user.username.size")
    private String username;

    @NotBlank(message = "valid.user.password.blank")
    @Size(min=6, max=20, message = "valid.user.password.size")
    private String password;

    @Email(message = "valid.user.email.email")
    @NotBlank(message = "valid.user.email.blank")
    @Size(max=50, message = "valid.user.email.size")
    private String email;

    @Pattern(regexp = "(^02|^\\d{3})-(\\d{3}|\\d{4})-\\d{4}", message = "valid.user.phone.phone")
    @NotBlank(message = "valid.user.phone.blank")
    @Size(min = 9, max = 13, message = "valid.user.phone.size")
    private String phone;

    @NotBlank(message = "valid.user.name.blank")
    @Size(max=50, message = "valid.user.name.size")
    private String name;

    @Size(max=50, message = "valid.user.department.size")
    private String department;

    @PositiveOrZero(message = "valid.user.noshowcnt.positive")
    private Integer noShowCnt;

    @NotNull(message = "valid.user.permission.null")
    private Permission permission;

    public CreateUserProfileReqDTO parseFromCSVRow(String[] rawTexts, String encodedPw) {
        StringBuilder phoneNumber = new StringBuilder();
        phoneNumber.append(rawTexts[4]);
        phoneNumber.insert(3, '-');
        phoneNumber.insert(phoneNumber.length() - 4, '-');

        this.username = rawTexts[0];
        this.password = encodedPw;
        this.name = rawTexts[2];
        this.department = Integer.parseInt(rawTexts[3]) == 1 ? "컴퓨터공학과" : "기타학과";
        this.phone = phoneNumber.toString();
        this.email = rawTexts[5];
        this.permission = Permission.values()[Integer.parseInt(rawTexts[6])];
        return this;
    }
}
