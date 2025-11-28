package com.App.Consecionario.Repository;

import com.App.Consecionario.Entity.VentaOpcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaOpcionRepository extends JpaRepository<VentaOpcion, Long> {
}