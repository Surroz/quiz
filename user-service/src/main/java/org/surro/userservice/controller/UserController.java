package org.surro.userservice.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.surro.userservice.model.LoginRequest;
import org.surro.userservice.model.RegistrationRequest;
import org.surro.userservice.model.TokensPair;
import org.surro.userservice.service.JwtService;
import org.surro.userservice.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest registrationRequest) {

        if (userService.register(registrationRequest))
            return new ResponseEntity<>("Registration successful", HttpStatus.CREATED);
        else
            return new ResponseEntity<>("Registration unsuccessful, review entered data", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        TokensPair tokensPair = userService.login(loginRequest);
        ResponseCookie cookie = ResponseCookie.from("refresh_token", tokensPair.accessToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(tokensPair.tokenDuration())
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(tokensPair.requestToken());
   }

    @GetMapping("/refresh")
    public ResponseEntity<String> refreshToken(@CookieValue(name = "refresh_token") String refreshToken) {
        return ResponseEntity.ok(userService.generateAccessToken(refreshToken));
    }

    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> getKeys() {
        return jwtService.getJwkSet();
    }
}