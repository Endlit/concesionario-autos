package com.App.Consecionario.Repository;

import com.App.Consecionario.Entity.Emun.Rol;
import com.App.Consecionario.Entity.Usuario;
import com.App.Consecionario.Entity.Vehiculo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VehiculoRepository {

    private final JdbcTemplate jdbc;

    public List<Vehiculo> findAll() {
        return jdbc.query("""
                SELECT v.*, u.dni, u.nombre, u.direccion, u.telefono, u.rol
                FROM vehiculo v
                LEFT JOIN usuario u ON v.dni_cliente = u.dni
            """, (rs, row) -> {
            Vehiculo v = new Vehiculo();
            v.setMatricula(rs.getString("matricula"));
            v.setMarca(rs.getString("marca"));
            v.setModelo(rs.getString("modelo"));
            v.setCilindrada(rs.getString("cilindrada"));
            v.setPrecio(rs.getInt("precio"));
            v.setPrecioTasacion(rs.getDouble("precio_tasacion"));
            v.setFechaCesion(rs.getDate("fecha_cesion") != null ?
                    rs.getDate("fecha_cesion").toLocalDate() : null);

            // Cliente asociado
            if (rs.getString("dni") != null) {
                Usuario cliente = new Usuario();
                cliente.setDni(rs.getString("dni"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setRol(Rol.valueOf(rs.getString("rol")));
                v.setCliente(cliente);
            }

            return v;
        });
    }

    public Optional<Vehiculo> findById(String matricula) {
        String sql = """
            SELECT v.*, u.dni, u.nombre, u.direccion, u.telefono, u.rol
            FROM vehiculo v
            LEFT JOIN usuario u ON v.dni_cliente = u.dni
            WHERE matricula=?
            """;

        List<Vehiculo> result = jdbc.query(sql, (rs, row) -> {
            Vehiculo v = new Vehiculo();
            v.setMatricula(rs.getString("matricula"));
            v.setMarca(rs.getString("marca"));
            v.setModelo(rs.getString("modelo"));
            v.setCilindrada(rs.getString("cilindrada"));
            v.setPrecio(rs.getInt("precio"));
            v.setPrecioTasacion(rs.getDouble("precio_tasacion"));
            v.setFechaCesion(rs.getDate("fecha_cesion") != null ?
                    rs.getDate("fecha_cesion").toLocalDate() : null);

            // Cliente asociado
            if (rs.getString("dni") != null) {
                Usuario cliente = new Usuario();
                cliente.setDni(rs.getString("dni"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setRol(Rol.valueOf(rs.getString("rol")));
                v.setCliente(cliente);
            }

            return v;
        }, matricula);

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public void save(Vehiculo v) {
        if (findAll().stream().noneMatch(x -> x.getMatricula().equals(v.getMatricula()))) {
            jdbc.update("""
                INSERT INTO vehiculo(matricula,marca,modelo,cilindrada,precio,precio_tasacion,fecha_cesion,dni_cliente)
                VALUES (?,?,?,?,?,?,?,?)
            """, v.getMatricula(), v.getMarca(), v.getModelo(), v.getCilindrada(),
                    v.getPrecio(), v.getPrecioTasacion(), v.getFechaCesion(),
                    v.getCliente() != null ? v.getCliente().getDni() : null);
        } else {
            jdbc.update("""
                UPDATE vehiculo SET marca=?,modelo=?,cilindrada=?,precio=?,precio_tasacion=?,fecha_cesion=?,dni_cliente=?
                WHERE matricula=?
            """, v.getMarca(), v.getModelo(), v.getCilindrada(), v.getPrecio(), v.getPrecioTasacion(),
                    v.getFechaCesion(), v.getCliente() != null ? v.getCliente().getDni() : null,
                    v.getMatricula());
        }
    }

    public void delete(String matricula) {
        jdbc.update("DELETE FROM vehiculo WHERE matricula=?", matricula);
    }

}