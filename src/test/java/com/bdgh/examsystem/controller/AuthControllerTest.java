package com.bdgh.examsystem.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.bdgh.examsystem.config.JwtService;
import com.bdgh.examsystem.exception.NotFoundException;
import com.bdgh.examsystem.service.AuthenticationService;
import com.bdgh.examsystem.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserService userService;

    @Test
    public void testForgotPasswordSuccess() throws Exception {
        String email = "test@example.com";
        doNothing().when(userService).forgotPassword(email);

        mockMvc.perform(post("/api/v1/auth/forgotPassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + email + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.message").value("Gửi email đặt lại mật khẩu thành công"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    public void testForgotPasswordFailure() throws Exception {
        String email = "test@example.com";
        doThrow(new NotFoundException("Email không tồn tại")).when(userService).forgotPassword(email);

        mockMvc.perform(post("/api/v1/auth/forgotPassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + email + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Email không tồn tại"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    public void testResetPasswordSuccess() throws Exception {
        // Tạo đối tượng Map và thêm từng phần tử vào
        Map<String, String> resetInfo = new HashMap<>();
        resetInfo.put("token", "some-token");
        resetInfo.put("newPassword", "newPassword123");

        // Mock hành vi của userService.resetPassword
        doNothing().when(userService).resetPassword(resetInfo);

        // Tạo chuỗi JSON cho yêu cầu
        String resetInfoJson = "{\"token\":\"some-token\",\"newPassword\":\"newPassword123\"}";

        // Gửi yêu cầu POST và kiểm tra phản hồi
        mockMvc.perform(post("/api/v1/auth/resetPassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(resetInfoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.message").value("Đặt lại mật khẩu thành công"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    public void testResetPasswordFailure() throws Exception {
        // Tạo đối tượng Map và thêm từng phần tử vào
        Map<String, String> resetInfo = new HashMap<>();
        resetInfo.put("token", "some-token");
        resetInfo.put("newPassword", "newPassword123");

        // Mock hành vi của userService.resetPassword
        doThrow(new NotFoundException("Token không tồn tại hoặc đã hết hạn"))
                .when(userService)
                .resetPassword(resetInfo);

        // Tạo chuỗi JSON cho yêu cầu
        String resetInfoJson = "{\"token\":\"some-token\",\"newPassword\":\"newPassword123\"}";

        // Gửi yêu cầu POST và kiểm tra phản hồi
        mockMvc.perform(post("/api/v1/auth/resetPassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(resetInfoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Token không tồn tại hoặc đã hết hạn"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    //    @Test
    //    public void testRegisterSuccess() throws Exception {
    //        RegisterRequest request = RegisterRequest.builder()
    //                .email("email")
    //                .ho("ho")
    //                .ten("ten")
    //                .password("password")
    //                .role(Role.STUDENT)
    //                .build();
    //        Token token = new Token("some-token", Role.STUDENT.name(), "");
    //        when(authenticationService.register(request)).thenReturn(token);
    //
    //        mockMvc.perform(post("/api/v1/auth/register")
    //                        .contentType(MediaType.APPLICATION_JSON)
    //                        .content("{\"email\":\"email\","
    //                                + "\"ho\":\"ho\","
    //                                + "\"ten\":\"ten\","
    //                                + "\"role\":\"STUDENT\","
    //                                + "\"password\":\"password\"}"))
    //                .andExpect(status().isOk())
    //                .andExpect(jsonPath("$.status").value("ok"))
    //                .andExpect(jsonPath("$.message").value("Đăng ký thành công!"))
    //                .andExpect(jsonPath("$.data.token").value("some-token"))
    //                .andExpect(jsonPath("$.data.role").value(Role.STUDENT.name()));
    //    }
    //
    //    @Test
    //    public void testRegisterFailure() throws Exception {
    //        RegisterRequest request = RegisterRequest.builder()
    //                .email("email")
    //                .ho("ho")
    //                .ten("ten")
    //                .password("password1A@")
    //                .role(Role.STUDENT)
    //                .build();
    //        Token token = new Token();
    //        // Mong đợi thrown ngoại lệ
    //        AuthException exception = assertThrows(AuthException.class, () -> {
    //            authenticationService.register(request);
    //        });
    //
    //        mockMvc.perform(post("/api/v1/auth/register")
    //                        .contentType(MediaType.APPLICATION_JSON)
    //                        .content("{\"email\":\"email\","
    //                                + "\"ho\":\"ho\","
    //                                + "\"ten\":\"ten\","
    //                                + "\"role\":\"STUDENT\","
    //                                + "\"password\":\"password1A@\"}"))
    //                .andExpect(status().isOk())
    //                .andExpect(jsonPath("$.status").value("error"))
    //                .andExpect(jsonPath("$.message").value("Tài khoản đã tồn tại!"))
    //                .andExpect(jsonPath("$.data").doesNotExist());
    //    }
    //
    //    @Test
    //    public void testAuthenticateSuccess() throws Exception {
    //        AuthenticationRequest request = new AuthenticationRequest("email", "password");
    //        Token token = new Token("some-token", Role.STUDENT.name(), "");
    //        when(authenticationService.authenticate(request)).thenReturn(token);
    //
    //        mockMvc.perform(post("/api/v1/auth/authenticate")
    //                        .contentType(MediaType.APPLICATION_JSON)
    //                        .content("{\"email\":\"email\",\"password\":\"password\"}"))
    //                .andExpect(status().isOk())
    //                .andExpect(jsonPath("$.status").value("ok"))
    //                .andExpect(jsonPath("$.message").value("Đăng nhập thành công!"))
    //                .andExpect(jsonPath("$.data.token").value("some-token"))
    //                .andExpect(jsonPath("$.data.role").value(Role.STUDENT.name()));
    //    }
    //
    //    @Test
    //    public void testAuthenticateFailure() throws Exception {
    //        AuthenticationRequest request = new AuthenticationRequest("email", "password");
    //        Token token = new Token();
    //        when(authenticationService.authenticate(request)).thenReturn(token);
    //
    //        mockMvc.perform(post("/api/v1/auth/authenticate")
    //                        .contentType(MediaType.APPLICATION_JSON)
    //                        .content("{\"email\":\"username\",\"password\":\"password\"}"))
    //                .andExpect(status().isOk())
    //                .andExpect(jsonPath("$.status").value("error"))
    //                .andExpect(jsonPath("$.message").value("Tài khoản, mật khẩu không đúng!"))
    //                .andExpect(jsonPath("$.data").doesNotExist());
    //    }
}
