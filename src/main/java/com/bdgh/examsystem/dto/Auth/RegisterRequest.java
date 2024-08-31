package com.bdgh.examsystem.dto.Auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.bdgh.examsystem.entity.Role;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {
    @NotBlank(message = "Họ không được để trống")
    String ho;

    @NotBlank(message = "Tên không được để trống")
    String ten;

    @NotBlank(message = "Email không được để trống")
    String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "Mật khẩu phải ít nhất 8 kí tự")
    @Size(max = 20, message = "Mật khẩu tối đa 20 kí tự")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
            message = "Mật khẩu phải có ít nhất 1 chữ thường, 1 chữ hoa, 1 số, 1 kí tự đặc biệt")
    String password;

    @NotNull(message = "Vai trò không được để trống")
    Role role;
}
