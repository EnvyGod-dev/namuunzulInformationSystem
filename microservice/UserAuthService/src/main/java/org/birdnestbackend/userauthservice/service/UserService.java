package org.birdnestbackend.userauthservice.service;

import lombok.RequiredArgsConstructor;
import org.birdnestbackend.userauthservice.config.JwtTokenProvider;
import org.birdnestbackend.userauthservice.entity.User;
import org.birdnestbackend.userauthservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final List<String> VALID_ROLES = Arrays.asList(
            "ROLE_ADMIN",
            "ROLE_MANAGER",
            "ROLE_OFFICER",
            "ROLE_CUSTOMER"
    );

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    public Mono<User> registerUser(User user) {
        return userRepository.findByUsername(user.getUsername())
                .flatMap(existingUser -> Mono.<User>error(new RuntimeException("Бүртгэлтэй нэвтрэх нэр байна")))
                .switchIfEmpty(Mono.defer(() -> {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
//                    user.setRoles(Collections.singletonList("ROLE_CUSTOMER"));
                    return userRepository.save(user);
                }));
    }



    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }


    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new RuntimeException("Хэрэглэгч олдсонгүй")));
    }


    public Mono<User> assignRole(String username, String role) {
        if (!VALID_ROLES.contains(role)) {
            return Mono.error(new RuntimeException("Зөвшөөрөгдсөн role-д байхгүй байна"));
        }

        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new RuntimeException("Хэрэглэгч олдсонгүй")))
                .flatMap(user -> {
                    if(!user.getRoles().contains(role)) {
                        user.getRoles().add(role);
                    }
                    return userRepository.save(user);
                });
    }

    public Mono<String> login(String username, String password) {
        return userRepository.findByUsername(username)
                .flatMap(user -> {
                    if(passwordEncoder.matches(password, user.getPassword())) {
                        String token = jwtTokenProvider.generateToken(user);
                        return Mono.just(token);
                    } else {
                        return Mono.error(new RuntimeException("Нэвтрэх эсхүл нууц үг буруу байна"));
                    }
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Нэвтрэх эсхүл нууц үг буруу байна")));
    }

    public Mono<String> forgotPassword(String email) {
        return userRepository.findByEmail(email)
                .flatMap(user -> {
                    String resetToken = UUID.randomUUID().toString();
                    user.setResetToken(resetToken);
                    return userRepository.save(user)
                            .then(Mono.just("Нууц үг сэргээх link мэйл руу явуулсан" + email));
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Хэрэглэгчийн И-Мэйл олдсонгүй")));
    }

    public  Mono<String> resetPassword(String resetToken,String newPassword) {
        return userRepository.findByResetToken(resetToken)
                .flatMap(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.setResetToken(null);
                    return userRepository.save(user)
                            .then(Mono.just("Нууц үг амжилттай сэргээгдлээ"));
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Reset token-ний хугацаа дууссан байна.")));
    }
}