package com.App.Consecionario.Controller;

import com.App.Consecionario.Entity.Usuario;
import com.App.Consecionario.Services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("usuarios", service.listar());
        model.addAttribute("usuario", new Usuario()); // Para formulario
        return "usuarios"; // Vista Thymeleaf
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Usuario u) {
        service.guardar(u);
        return "redirect:/usuarios";
    }

    @GetMapping("/delete/{dni}")
    public String delete(@PathVariable String dni) {
        service.eliminar(dni);
        return "redirect:/usuarios";
    }

}