package com.App.Consecionario.Services;

import com.App.Consecionario.Entity.Vehiculo;
import com.App.Consecionario.Repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculoService {

    private final VehiculoRepository repo;

    public List<Vehiculo> listar() {
        return repo.findAll();
    }

    public Vehiculo buscar(String matricula) {
        return repo.findById(matricula).orElse(null);
    }

    public void guardar(Vehiculo v) {
        repo.save(v);
    }

    public void eliminar(String matricula) {
        repo.delete(matricula);
    }
}
