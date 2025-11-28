package com.App.Consecionario.Controller;

import com.App.Consecionario.Entity.Usuario;
import com.App.Consecionario.Entity.Vehiculo;
import com.App.Consecionario.Repository.UsuarioRepository;
import com.App.Consecionario.Repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/vehiculos")
public class VehiculoController {

    private final VehiculoRepository repo;
    private final UsuarioRepository usuarioRepo;

    @GetMapping
    public String findAll(Model model) {
        List<Vehiculo> vehiculos = repo.findAll();
        List<Usuario> usuarios = usuarioRepo.findAll();
        model.addAttribute("vehiculos", vehiculos);
        model.addAttribute("vehiculo", new Vehiculo());
        model.addAttribute("usuarios", usuarios); // para asignar cliente si hace falta
        return "vehiculos";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Vehiculo vehiculo,
                       @RequestParam(required = false) String clienteDni) {

        if (clienteDni != null && !clienteDni.isBlank()) {
            Usuario cliente = usuarioRepo.findById(clienteDni).orElse(null);
            vehiculo.setCliente(cliente);
        } else {
            vehiculo.setCliente(null);
        }

        repo.save(vehiculo);
        return "redirect:/vehiculos";
    }

    @GetMapping("/delete/{matricula}")
    public String delete(@PathVariable String matricula) {
        repo.deleteById(matricula);
        return "redirect:/vehiculos";
    }

}