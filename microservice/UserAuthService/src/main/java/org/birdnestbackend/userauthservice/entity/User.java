package org.birdnestbackend.userauthservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id; // MongoDB document ID

    @NotBlank(message = "Хэрэглэгчийн нэр шаардлагатай")
    @Size(min = 3, max = 20, message = "Хэрэглэгчийн нэр 3-20 тэмдэгт байх ёстой")
    private String username;

    @NotBlank(message = "Нууц үг шаардлагатай")
    @Size(min = 6, message = "Нууц үг хамгийн багадаа 6 тэмдэгт байх ёстой")
    private String password;

    @NotBlank(message = "И-Мэйл шаардлагатай")
    @Email(message = "И-Мэйл зөв байх ёстой")
    private String email;

    private String resetToken;

    private List<String> roles;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private LocalDate birthday;


    public User(String id, String username, String password, String email, List<String> roles,
                String resetToken, String phoneNumber, String firstName, String lastName, LocalDate birthday) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
        this.resetToken = resetToken;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }
}
