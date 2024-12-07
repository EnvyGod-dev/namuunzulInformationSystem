package org.birdnestbackend.userauthservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.birdnestbackend.userauthservice.DTO.*;
import org.birdnestbackend.userauthservice.entity.User;
import org.birdnestbackend.userauthservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public Mono<ResponseEntity<User>> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        user.setEmail(registerRequest.getEmail());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setBirthday(registerRequest.getBirthday());
        user.setRoles(registerRequest.getRoles());

        return userService.registerUser(user)
                .map(savedUser -> ResponseEntity.status(HttpStatus.CREATED).body(savedUser))
                .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().body(null)));
    }


    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest.getUsername(), loginRequest.getPassword())
                .map(token -> ResponseEntity.ok("Bearer " + token))
                .onErrorResume(ex -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage())));
    }

    @PostMapping("/assign-role")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<User>> assignRole(@RequestBody AssignRoleRequest assignRoleRequest) {
        return userService.assignRole(assignRoleRequest.getUsername(), assignRoleRequest.getRole())
                .map(ResponseEntity::ok)
                .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().body(null)));
    }

    @PostMapping("/forgot-password")
    public Mono<ResponseEntity<String>> forgotPassword(@RequestBody ForgorPasswordRequest forgotPasswordRequest) {
        return userService.forgotPassword(forgotPasswordRequest.getEmail())
                .map(ResponseEntity::ok)
                .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().body(ex.getMessage())));
    }

    @PostMapping("/reset-password")
    public Mono<ResponseEntity<String>> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        return userService.resetPassword(resetPasswordRequest.getResetToken(), resetPasswordRequest.getNewPassword())
                .map(ResponseEntity::ok)
                .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().body(ex.getMessage())));
    }
    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public Flux<User> getAllUsers() {
        return userService.getAllUsers();
    }

}
