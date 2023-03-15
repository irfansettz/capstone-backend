package com.ikon.authservice.service.impl;

import com.ikon.authservice.dto.UserDTO;
import com.ikon.authservice.dto.UserResponseDTO;
import com.ikon.authservice.entity.User;
import com.ikon.authservice.exception.InvalidPasswordException;
import com.ikon.authservice.exception.UserNotFoundException;
import com.ikon.authservice.repository.UserRepository;
import com.ikon.authservice.util.PasswordValidator;
import com.ikon.authservice.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder encoder;
    private final RestTemplate restTemplate;
    @Override
    public String generatedToken(Authentication authentication) {
        Instant now = Instant.now();
        String scope = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(""));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Override
    public UserDTO decodeToken(String token) {
        String newToken = token.split(" ")[1];
        Jwt jwtToken = jwtDecoder.decode(newToken);
        String data = jwtToken.getSubject();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<UserResponseDTO> response;
        response = restTemplate.exchange("http://localhost:8082/v1/api/users?username=" + data, HttpMethod.GET, entity, UserResponseDTO.class);

        if (response.getBody().getData() != null) {
            return response.getBody().getData().get(0);
        }
        throw new UserNotFoundException("user not found");
    }

    @Override
    @Transactional()
    public void addUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        if (!PasswordValidator.isValid(user.getPassword())) {
            throw new InvalidPasswordException("error");

        }
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}

