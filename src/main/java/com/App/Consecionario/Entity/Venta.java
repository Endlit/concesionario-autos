package com.App.Consecionario.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    private Double precioVenta;

    private String matriculaVehiculoNuevo;

    @ManyToOne
    @JoinColumn(name = "vehiculo_matricula")
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "dni_vendedor")
    private Usuario vendedor;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<VentaOpcion> opciones;

}