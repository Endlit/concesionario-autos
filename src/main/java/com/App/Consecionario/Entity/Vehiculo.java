package com.App.Consecionario.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Vehiculo {

    @Id
    @NotBlank(message = "La matrícula es obligatoria")
    @Size(min = 4, max = 10, message = "La matrícula debe tener entre 4 y 10 caracteres")
    private String matricula;

    @NotBlank(message = "La marca es obligatoria")
    @Size(max = 50, message = "La marca no puede superar 50 caracteres")
    private String marca;
    @NotBlank(message = "El modelo es obligatorio")
    @Size(max = 50, message = "El modelo no puede superar 50 caracteres")
    private String modelo;
    @Size(max = 20, message = "La cilindrada no puede superar 20 caracteres")
    private String cilindrada;
    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    private Integer precio;

    @Positive(message = "El precio de tasación debe ser mayor a 0")
    private Double precioTasacion;
    @PastOrPresent(message = "La fecha debe ser hoy o una fecha pasada")
    private LocalDate fechaCesion;

    @ManyToOne
    @JoinColumn(name = "dni_cliente")
    private Usuario cliente;

}