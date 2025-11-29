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

        model.addAttribute("vehiculos", vehiculoRepo.findAll());
        model.addAttribute("usuarios", usuarioRepo.findAll()); // puedes filtrar por ROL si deseas
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
            @RequestParam(required = false) String vehiculoUsadoMatricula,
            @RequestParam(required = false) Double precioVenta,
            @RequestParam(required = false) Double precioOpcionAplicado,    // ← este campo viene del form
            @RequestParam(required = false, name = "opcionIds") Long[] opcionIds
    ) {

        Venta venta = new Venta();
        venta.setFecha(LocalDate.now());
        venta.setPrecioVenta(precioVenta);

        vehiculoRepo.findById(vehiculoMatricula).ifPresent(venta::setVehiculo);
        usuarioRepo.findById(vendedorDni).ifPresent(venta::setVendedor);

        if(vehiculoUsadoMatricula != null && !vehiculoUsadoMatricula.isBlank()){
            venta.setMatriculaVehiculoNuevo(vehiculoUsadoMatricula);
        }

        Venta savedVenta = ventaRepo.save(venta);


        // *** OPCIONES con precioAplicado propio ***
        if(opcionIds != null && opcionIds.length > 0){
            List<VentaOpcion> lista = new ArrayList<>();

            for(Long id : opcionIds){
                opcionRepo.findById(id).ifPresent(op -> {
                    VentaOpcion vo = new VentaOpcion();
                    vo.setVenta(savedVenta);
                    vo.setOpcion(op);

                    vo.setPrecioAplicado(precioOpcionAplicado);
                    lista.add(vo);
                });
            }

            ventaOpcionRepo.saveAll(lista);
            savedVenta.setOpciones(lista);
            ventaRepo.save(savedVenta);
        }

        return "redirect:/ventas";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        ventaOpcionRepo.deleteByVentaId(id);
        ventaRepo.deleteById(id);
        return "redirect:/ventas";
    }

}