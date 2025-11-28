package com.App.Consecionario.Controller;

import com.App.Consecionario.Entity.Opcion;
import com.App.Consecionario.Repository.OpcionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/opciones")
public class OpcionController {

    private final OpcionRepository repo;

    @GetMapping
    public String list(Model model) {
        List<Opcion> opciones = repo.findAll();
        model.addAttribute("opciones", opciones);
        model.addAttribute("opcion", new Opcion());
        return "opciones";
    }

    @PostMapping("/save")
    public String save(Opcion o) {
        repo.save(o);
        return "redirect:/opciones";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/opciones";
    }

}