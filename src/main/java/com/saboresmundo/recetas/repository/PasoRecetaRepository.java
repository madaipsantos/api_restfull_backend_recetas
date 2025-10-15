package com.saboresmundo.recetas.repository;

import com.saboresmundo.recetas.model.PasoReceta;
import com.saboresmundo.recetas.model.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PasoRecetaRepository extends JpaRepository<PasoReceta, Long> {
    List<PasoReceta> findByRecetaOrderByOrdenAsc(Receta receta);

    void deleteByReceta(Receta receta);
}