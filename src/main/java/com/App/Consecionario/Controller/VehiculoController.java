package com.App.Consecionario.Controller;

import com.App.Consecionario.Entity.Vehiculo;
import com.App.Consecionario.Repository.UsuarioRepository;
import com.App.Consecionario.Repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/vehiculos")
public class VehiculoController {

    private final VehiculoRepository repo;
    private final UsuarioRepository usuarioRepo;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("vehiculos", repo.findAll());
        model.addAttribute("vehiculo", new Vehiculo());
        model.addAttribute("usuarios", usuarioRepo.findAll());
        return "vehiculos";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Vehiculo vehiculo,
                       @RequestParam(required = false) String clienteDni) {

        if (clienteDni != null && !clienteDni.isBlank()) {
            usuarioRepo.findById(clienteDni).ifPresent(vehiculo::setCliente);
        }

        repo.save(vehiculo);
        return "redirect:/vehiculos";
    }

    @GetMapping("/delete/{matricula}")
    public String delete(@PathVariable String matricula) {
        repo.delete(matricula);
        return "redirect:/vehiculos";
    }
}