package com.App.Consecionario.Controller;

import com.App.Consecionario.Entity.Opcion;
import com.App.Consecionario.Entity.Venta;
import com.App.Consecionario.Entity.VentaOpcion;
import com.App.Consecionario.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ventas")
public class VentaController {

    private final VentaRepository ventaRepo;
    private final UsuarioRepository usuarioRepo;
    private final VehiculoRepository vehiculoRepo;
    private final OpcionRepository opcionRepo;
    private final VentaOpcionRepository ventaOpcionRepo;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("ventas", ventaRepo.findAll());
        model.addAttribute("venta", new Venta());

        // datos para el formulario
        model.addAttribute("vehiculos", vehiculoRepo.findAll());
        // vendedores: filtrar por rol si quieres, aquí incluimos todos
        model.addAttribute("usuarios", usuarioRepo.findAll());
        model.addAttribute("opciones", opcionRepo.findAll());
        return "ventas";
    }

    /**
     * Guardar venta con parámetros simples:
     * - vendedorDni: String
     * - vehiculoMatricula: String
     * - clienteDni (opcional, para vehículo usado cedido)
     * - vehiculoUsadoMatricula (opcional)
     * - precioVenta: Double
     * - opcionIds (opcional): Long[] (ids de opciones seleccionadas)
     */
    @PostMapping("/save")
    public String save(
            @RequestParam String vendedorDni,
            @RequestParam String vehiculoMatricula,
            @RequestParam(required = false) String clienteDni,
            @RequestParam(required = false) String vehiculoUsadoMatricula,
            @RequestParam(required = false) Double precioVenta,
            @RequestParam(required = false, name = "opcionIds") Long[] opcionIds
    ) {

        Venta v = new Venta();
        v.setFecha(LocalDate.now());
        v.setPrecioVenta(precioVenta);
        // vehiculo nuevo
        vehiculoRepo.findById(vehiculoMatricula).ifPresent(v::setVehiculo);
        // vendedor
        usuarioRepo.findById(vendedorDni).ifPresent(v::setVendedor);

        // guardamos venta primero (para tener id)
        Venta saved = ventaRepo.save(v);

        // si se entregó vehículo usado (solo registro simple en este CRUD)
        if (vehiculoUsadoMatricula != null && !vehiculoUsadoMatricula.isBlank()) {
            // Opcional: marcarlo en la tabla Vehiculo como entregado (fecha, tasacion) según tu modelo.
            // Aquí solo lo dejamos como referencia en matriculaVehiculoNuevo (si lo deseas).
            // saved.setMatriculaVehiculoNuevo(vehiculoUsadoMatricula);
        }

        // Opciones aplicadas
        if (opcionIds != null && opcionIds.length > 0) {
            List<VentaOpcion> lista = new ArrayList<>();
            for (Long id : opcionIds) {
                Opcion op = opcionRepo.findById(id).orElse(null);
                if (op != null) {
                    VentaOpcion vo = new VentaOpcion();
                    vo.setVenta(saved);
                    vo.setOpcion(op);
                    // ejemplo: precioAplicado igual a 0 por defecto; puedes ajustar
                    vo.setPrecioAplicado(0.0);
                    lista.add(vo);
                }
            }
            ventaOpcionRepo.saveAll(lista);
            saved.setOpciones(lista);
            ventaRepo.save(saved);
        }

        return "redirect:/ventas";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        // borrar opciones relacionadas primero (cascade podría hacerlo si está configurado)
        ventaOpcionRepo.findAll().stream()
                .filter(vo -> vo.getVenta() != null && vo.getVenta().getId().equals(id))
                .forEach(vo -> ventaOpcionRepo.deleteById(vo.getId()));
        ventaRepo.deleteById(id);
        return "redirect:/ventas";
    }
}