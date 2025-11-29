package com.App.Consecionario.Repository;

import com.App.Consecionario.Entity.Opcion;
import com.App.Consecionario.Entity.VentaOpcion;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class VentaOpcionRepository {

    private final JdbcTemplate jdbc;

    public VentaOpcion save(VentaOpcion vo) {
        if (vo.getId() == null) {

            jdbc.update("""
                INSERT INTO venta_opcion (precio_aplicado, venta_id, opcion_id)
                VALUES (?,?,?)
            """,
                    vo.getPrecioAplicado(),
                    vo.getVenta() != null ? vo.getVenta().getId() : null,
                    vo.getOpcion() != null ? vo.getOpcion().getId() : null
            );

            Long id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
            vo.setId(id);
        } else {
            jdbc.update("""
                UPDATE venta_opcion SET precio_aplicado=?, venta_id=?, opcion_id=?
                WHERE id=?
            """,
                    vo.getPrecioAplicado(),
                    vo.getVenta() != null ? vo.getVenta().getId() : null,
                    vo.getOpcion() != null ? vo.getOpcion().getId() : null,
                    vo.getId());
        }
        return vo;
    }

    public void saveAll(List<VentaOpcion> lista) {
        lista.forEach(this::save);
    }

    public List<VentaOpcion> findByVentaId(Long ventaId) {
        return jdbc.query("""
            SELECT vo.id, vo.precio_aplicado, op.id AS op_id, op.nombre, op.descripcion
            FROM venta_opcion vo
            INNER JOIN opcion op ON vo.opcion_id = op.id
            WHERE vo.venta_id = ?
        """, (rs,row) -> {

            VentaOpcion vo = new VentaOpcion();
            vo.setId(rs.getLong("id"));
            vo.setPrecioAplicado(rs.getDouble("precio_aplicado"));

            // Opción asociada
            Opcion op = new Opcion();
            op.setId(rs.getLong("op_id"));
            op.setNombre(rs.getString("nombre"));
            op.setDescripcion(rs.getString("descripcion"));
            vo.setOpcion(op);

            return vo;

        }, ventaId);
    }

    public void deleteById(Long id){
        jdbc.update("DELETE FROM venta_opcion WHERE id=?", id);
    }

    public void deleteByVentaId(Long id){
        jdbc.update("DELETE FROM venta_opcion WHERE venta_id=?", id);
    }

}