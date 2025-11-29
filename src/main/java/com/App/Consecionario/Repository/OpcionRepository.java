package com.App.Consecionario.Repository;

import com.App.Consecionario.Entity.Opcion;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OpcionRepository {

    private final JdbcTemplate jdbc;

    public Optional<Opcion> findById(Long id) {
        String sql = "SELECT * FROM opcion WHERE id = ?";
        List<Opcion> result = jdbc.query(sql, (rs, row) -> {
            Opcion o = new Opcion();
            o.setId(rs.getLong("id"));
            o.setNombre(rs.getString("nombre"));
            o.setDescripcion(rs.getString("descripcion"));
            return o;
        }, id);

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public List<Opcion> findAll() {
        String sql = "SELECT * FROM opcion";
        return jdbc.query(sql, (rs, row) -> {
            Opcion o = new Opcion();
            o.setId(rs.getLong("id"));
            o.setNombre(rs.getString("nombre"));
            o.setDescripcion(rs.getString("descripcion"));
            return o;
        });
    }

    public void save(Opcion o) {
        if (o.getId() == null) {
            jdbc.update("INSERT INTO opcion(nombre, descripcion) VALUES (?,?)",
                    o.getNombre(), o.getDescripcion());
        } else {
            jdbc.update("UPDATE opcion SET nombre=?, descripcion=? WHERE id=?",
                    o.getNombre(), o.getDescripcion(), o.getId());
        }
    }

    public void deleteById(Long id) {
        jdbc.update("DELETE FROM opcion WHERE id=?", id);
    }

}