package com.App.Consecionario.Entity;

import com.App.Consecionario.Entity.Emun.Rol;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Usuario {

    @Id
    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 5, max = 12, message = "El DNI debe tener entre 5 y 12 caracteres")
    private String dni;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    private String nombre;
    @Size(max = 150, message = "La dirección no puede superar 150 caracteres")
    private String direccion;

    @Pattern(regexp = "^[0-9+ ]{7,15}$", message = "El teléfono solo puede contener números y '+'")
    private String telefono;

    @NotNull(message = "Debe seleccionar un rol")
    @Enumerated(EnumType.STRING)
    private Rol rol;

}