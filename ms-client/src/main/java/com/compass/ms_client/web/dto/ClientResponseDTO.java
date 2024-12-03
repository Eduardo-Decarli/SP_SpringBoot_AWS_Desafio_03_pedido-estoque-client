package com.compass.ms_client.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientResponseDTO extends RepresentationModel<ClientResponseDTO> implements Serializable {

    private Long id;

    @NotBlank(message = "The name can't be null")
    private String name;
    @Email
    @NotBlank(message = "The email can't be null")
    private String email;
}
