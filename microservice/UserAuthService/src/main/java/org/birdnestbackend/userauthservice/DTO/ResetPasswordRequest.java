package org.birdnestbackend.userauthservice.DTO;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String resetToken;
    private String newPassword;
}
