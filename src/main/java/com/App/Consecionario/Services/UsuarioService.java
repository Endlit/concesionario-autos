package com.App.Consecionario.Services;

import com.App.Consecionario.Entity.Usuario;
import com.App.Consecionario.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repo;

    public List<Usuario> listar() {
        return repo.findAll();
    }

    public Usuario buscar(String dni) {
        return repo.findById(dni).orElse(null);
    }

    public void guardar(Usuario u) {
        repo.save(u);
    }

    public void eliminar(String dni) {
        repo.delete(dni);
    }

}