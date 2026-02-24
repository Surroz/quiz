package org.surro.userservice.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.surro.userservice.model.LoginRequest;
import org.surro.userservice.model.RegistrationRequest;
import org.surro.userservice.model.Role;
import org.surro.userservice.model.entity.User;
import org.surro.userservice.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public boolean register(RegistrationRequest regRequest) {
        boolean result = false;
        if(StringUtils.hasText(regRequest.login()) && StringUtils.hasText(regRequest.password())
            && userRepository.findByLogin(regRequest.login()).isEmpty()) {
            User user = new User();
            user.setLogin(regRequest.login());
            user.setPassword(passwordEncoder.encode(regRequest.password()));
            user.setRole
                    (Role.USER);
            userRepository.save(user);
            result = true;
        }
        return result;
    }

    public String login(LoginRequest loginRequest) {
        User user = userRepository.findByLogin(loginRequest.login())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return jwtService.createToken(user.getLogin());

    }

}
