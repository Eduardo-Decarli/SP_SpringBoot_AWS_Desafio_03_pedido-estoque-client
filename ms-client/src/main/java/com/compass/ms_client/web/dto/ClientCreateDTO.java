package com.compass.ms_client.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class ClientCreateDTO {

    @NotBlank(message = "Name cannot be null")
    private String name;

    @Email(message = "Email need be valid")
    @NotBlank(message = "Email cannot be null")
    private String email;
}
