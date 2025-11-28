package com.App.Consecionario.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Entity
@Data
public class VentaOpcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PositiveOrZero(message = "El precio aplicado no puede ser negativo")
    private Double precioAplicado;

    @ManyToOne
    @JoinColumn(name = "venta_id")
    @NotNull(message = "Debe asociarse a una venta")
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "opcion_id")
    @NotNull(message = "Debe seleccionarse una opción")
    private Opcion opcion;
}