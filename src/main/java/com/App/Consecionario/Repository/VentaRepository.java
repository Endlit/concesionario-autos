package com.App.Consecionario.Repository;

import com.App.Consecionario.Entity.Emun.Rol;
import com.App.Consecionario.Entity.Usuario;
import com.App.Consecionario.Entity.Vehiculo;
import com.App.Consecionario.Entity.Venta;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class VentaRepository {

    private final JdbcTemplate jdbc;

    public List<Venta> findAll() {
        return jdbc.query("""
                SELECT v.id, v.fecha, v.precio_venta, v.matricula_vehiculo_nuevo,
                       ve.matricula, ve.marca, ve.modelo,
                       u.dni AS vendedor_dni, u.nombre AS vendedor_nombre, u.rol
                FROM venta v
                LEFT JOIN vehiculo ve ON v.vehiculo_matricula = ve.matricula
                LEFT JOIN usuario u ON v.dni_vendedor = u.dni
            """, (rs, row) -> {
            Venta venta = new Venta();
            venta.setId(rs.getLong("id"));
            venta.setFecha(rs.getDate("fecha").toLocalDate());
            venta.setPrecioVenta(rs.getDouble("precio_venta"));
            venta.setMatriculaVehiculoNuevo(rs.getString("matricula_vehiculo_nuevo"));

            // Vehículo asociado
            if (rs.getString("matricula") != null) {
                Vehiculo ve = new Vehiculo();
                ve.setMatricula(rs.getString("matricula"));
                ve.setMarca(rs.getString("marca"));
                ve.setModelo(rs.getString("modelo"));
                venta.setVehiculo(ve);
            }

            // Vendedor
            if (rs.getString("vendedor_dni") != null) {
                Usuario u = new Usuario();
                u.setDni(rs.getString("vendedor_dni"));
                u.setNombre(rs.getString("vendedor_nombre"));
                u.setRol(Rol.valueOf(rs.getString("rol")));
                venta.setVendedor(u);
            }

            return venta;
        });
    }


    public Venta save(Venta v) {
        if(v.getId() == null) {
            jdbc.update("""
                INSERT INTO venta(fecha,precio_venta,vehiculo_matricula,dni_vendedor,matricula_vehiculo_nuevo)
                VALUES (?,?,?,?,?)
            """,
                    v.getFecha(),
                    v.getPrecioVenta(),
                    v.getVehiculo()!=null? v.getVehiculo().getMatricula():null,
                    v.getVendedor()!=null? v.getVendedor().getDni():null,
                    v.getMatriculaVehiculoNuevo());

            Long id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
            v.setId(id);
        } else {
            jdbc.update("""
                UPDATE venta SET fecha=?,precio_venta=?,vehiculo_matricula=?,dni_vendedor=?,matricula_vehiculo_nuevo=?
                WHERE id=?
            """,
                    v.getFecha(),
                    v.getPrecioVenta(),
                    v.getVehiculo()!=null? v.getVehiculo().getMatricula():null,
                    v.getVendedor()!=null? v.getVendedor().getDni():null,
                    v.getMatriculaVehiculoNuevo(),
                    v.getId());
        }
        return v;
    }

    public void deleteById(Long id){
        jdbc.update("DELETE FROM venta WHERE id=?", id);
    }

}