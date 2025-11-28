package com.App.Consecionario.Controller;

import com.App.Consecionario.Entity.Emun.Rol;
import com.App.Consecionario.Entity.Usuario;
import com.App.Consecionario.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository repo;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("usuarios", repo.findAll());
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", Rol.values());
        return "usuarios";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Usuario u) {
        repo.save(u);
        return "redirect:/usuarios";
    }

    @GetMapping("/delete/{dni}")
    public String delete(@PathVariable String dni) {
        repo.deleteById(dni);
        return "redirect:/usuarios";
    }

}