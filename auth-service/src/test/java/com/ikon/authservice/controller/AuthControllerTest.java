//package com.ikon.authservice.controller;
//
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import com.ikon.authservice.dto.ResponseDTO;
//import com.ikon.authservice.dto.UserDTO;
//import com.ikon.authservice.util.UserRole;
//import com.ikon.authservice.util.UserDepartment;
//import com.ikon.authservice.entity.User;
//import com.ikon.authservice.service.TokenService;
//import com.ikon.authservice.service.impl.JpaUserDetailService;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.verify;
//
//@ExtendWith(MockitoExtension.class)
//public class AuthControllerTest {
//
//    @Mock
//    private TokenService tokenService;
//
//    @Mock
//    private AuthenticationManager authenticationManager;
//
//    @Mock
//    private JpaUserDetailService jpaUserDetailService;
//
//    @InjectMocks
//    private AuthController authController;
//
//    private User createDefaultUser() {
//        return new User(
//                1L,
//                "admin",
//                "admin@ikon.com",
//                "admin@12345",
//                "admin",
//                UserRole.ADMIN,
//                UserDepartment.HRD,
//                LocalDateTime.now(),
//                LocalDateTime.now()
//        );
//    }
//    private UserDTO createDefaultUserDTO() {
//        return new UserDTO(
//                1,
//                "admin",
//                "admin@ikon.com",
//                "admin@12345",
//                "admin",
//                UserRole.ADMIN,
//                UserDepartment.HRD
//        );
//    }
//
//
//    @Test
//    public void testAddUser() {
//        // Arrange
//        UserDTO userDTO = createDefaultUserDTO();
//        ResponseDTO expectedResponse = new ResponseDTO(HttpStatus.CREATED, "success add user", userDTO);
//
//        // Act
//        ResponseEntity<ResponseDTO> response = authController.addUser(userDTO);
//
//        // Assert
//        assertEquals(expectedResponse, response.getBody());
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//    }
//
////    @Test
////    public void testToken() {
////        // Arrange
////        User user = createDefaultUser();
////        String token = "token";
////        ResponseDTO<String> expectedResponse = new ResponseDTO<>(HttpStatus.OK, "token created", token);
////
////        when(authenticationManager.authenticate(any())).thenReturn(null);
////        when(tokenService.generatedToken(null)).thenReturn(token);
////
////        // Act
////        ResponseEntity<ResponseDTO<String>> response = authController.token(user);
////
////        // Assert
////        assertEquals(expectedResponse, response.getBody());
////        assertEquals(HttpStatus.OK, response.getStatusCode());
////    }
////
//    @Test
//    public void testUserInfo() {
//        // Arrange
//        UserDTO userDTO = createDefaultUserDTO();
//        String tokenBearer = "tokenBearer";
//        ResponseDTO<Object> expectedResponse = new ResponseDTO<>(HttpStatus.OK, "token found", userDTO);
//
//        when(tokenService.decodeToken(tokenBearer)).thenReturn(userDTO);
//
//        // Act
//        ResponseEntity<ResponseDTO<Object>> response = authController.userInfo(tokenBearer);
//
//        // Assert
//        assertEquals(expectedResponse, response.getBody());
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    public void testUserInfo2() {
//        // Arrange
//        UserDTO userDTO = createDefaultUserDTO();
//        String tokenBearer = "tokenBearer";
//        ResponseEntity<UserDTO> expectedResponse = new ResponseEntity<>(userDTO, HttpStatus.OK);
//
//        when(tokenService.decodeToken(tokenBearer)).thenReturn(userDTO);
//
//        // Act
//        ResponseEntity<UserDTO> response = authController.userInfo2(tokenBearer);
//
//        // Assert
//        assertEquals(expectedResponse, response);
//    }
////
////    @Test
////    public void testAllUsers() throws Exception {
////        // Arrange
////        List<UserDTO> users = Arrays.asList(
////                new UserDTO("Alice", "alice@example.com"),
////                new UserDTO("Bob", "bob@example.com")
////        );
////        when(jpaUserDetailService.getAll()).thenReturn(users);
////
////        // Act
////        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/auth/all-users")
////                        .accept(MediaType.APPLICATION_JSON))
////                .andExpect(status().isOk())
////                .andReturn();
////
////        // Assert
////        String responseBody = result.getResponse().getContentAsString();
////        List<UserDTO> responseUsers = Arrays.asList(new ObjectMapper().readValue(responseBody, UserDTO[].class));
////        assertEquals(users, responseUsers);
////    }
//}