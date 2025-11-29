package com.App.Consecionario.Repository;

import com.App.Consecionario.Entity.Emun.Rol;
import com.App.Consecionario.Entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UsuarioRepository {

    private final JdbcTemplate jdbc;

    public List<Usuario> findAll() {
        return jdbc.query("SELECT * FROM usuario", (rs, row) -> {
            Usuario u = new Usuario();
            u.setDni(rs.getString("dni"));
            u.setNombre(rs.getString("nombre"));
            u.setDireccion(rs.getString("direccion"));
            u.setTelefono(rs.getString("telefono"));
            u.setRol(Rol.valueOf(rs.getString("rol")));
            return u;
        });
    }

    public Optional<Usuario> findById(String dni) {
        String sql = "SELECT * FROM usuario WHERE dni=?";

        List<Usuario> result = jdbc.query(sql, (rs, row) -> {
            Usuario u = new Usuario();
            u.setDni(rs.getString("dni"));
            u.setNombre(rs.getString("nombre"));
            u.setDireccion(rs.getString("direccion"));
            u.setTelefono(rs.getString("telefono"));
            u.setRol(Rol.valueOf(rs.getString("rol")));
            return u;
        }, dni);

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }


    public void save(Usuario u) {
        if (findAll().stream().noneMatch(x -> x.getDni().equals(u.getDni()))) {
            jdbc.update("""
                INSERT INTO usuario(dni,nombre,direccion,telefono,rol)
                VALUES (?,?,?,?,?)
            """, u.getDni(), u.getNombre(), u.getDireccion(), u.getTelefono(), u.getRol().name());
        } else {
            jdbc.update("""
                UPDATE usuario SET nombre=?,direccion=?,telefono=?,rol=? WHERE dni=?
            """, u.getNombre(), u.getDireccion(), u.getTelefono(), u.getRol().name(), u.getDni());
        }
    }

    public void delete(String dni) {

        // Primero borra vehículos relacionados
        jdbc.update("DELETE FROM vehiculo WHERE dni_cliente=?", dni);

        // Luego el usuario
        jdbc.update("DELETE FROM usuario WHERE dni=?", dni);
    }


}