package org.birdnestbackend.userauthservice.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RegisterRequest {
    @NotBlank(message = "Хэрэглэгчийн нэр хоосон байна")
    private String username;

    @NotBlank(message = "Нууц үг заавал байх ёстой")
    private String password;

    @NotBlank(message = "И-Мэйл хаяг заавал орох ёстой")
    @Email(message = "И-Мэйл оруулна уу")
    private String email;

    @NotBlank(message = "Утасны дугаар оруулна уу")
    @Pattern(regexp = "^[0-9]{8}$", message = "Утасны дугаар 8 оронтой байх ёстой")
    private String phoneNumber;

    @NotBlank(message = "Овог оруулна уу")
    private String firstName;

    @NotBlank(message = "Нэрээ оруулна уу")
    private String lastName;

    @NotNull(message = "Төрсөн өдөр заавал оруулна уу.")
    @Past(message = "Төрсөн өдөр өнгөрсөн цаг дээр байх ёстой")
    private LocalDate birthday;

    private List<String> roles;
}
