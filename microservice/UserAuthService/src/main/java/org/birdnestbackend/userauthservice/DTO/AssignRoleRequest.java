package org.birdnestbackend.userauthservice.DTO;

import lombok.Data;

@Data
public class AssignRoleRequest {
    private String username;
    private String role;
}
